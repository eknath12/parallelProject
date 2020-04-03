package com.capgemini.librarymanagementjdbc.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.capgemini.librarymanagementjdbc.dto.BookIssueInfo;
import com.capgemini.librarymanagementjdbc.dto.BookPrimaryInfo;
import com.capgemini.librarymanagementjdbc.dto.UserPrimaryInfo;
import com.capgemini.librarymanagementjdbc.exception.UserException;

public class UserDAOImplementation implements UserDAO {

	// 1
	/*
	 * In RegisterUser() is helps to register both student and as well as admin
	 * based on argument(typeOfUser) bascically it will check the user already
	 * exists or not by using user emailID if user is Admin then it will insert a
	 * one row in the userPrimaryInfo table and it will not insert a row in fines if
	 * the User is Student it will insert a one row in the UserPrimaryInfo table and
	 * userFine table(id,emailid,fines(0.0))) please see the corresponding database
	 * queries in the database.propeprties
	 *
	 **/
	@Override
	public boolean registerUser(UserPrimaryInfo user, String typeOfUser) {
		boolean isRegisterd = false;
		try (FileInputStream fis = new FileInputStream("database.properties");) {
			Properties properties = new Properties();
			properties.load(fis);
			Class.forName(properties.getProperty("mysqlDriverClass")).newInstance();
			try (Connection conn = DriverManager.getConnection(properties.getProperty("path"),
					properties.getProperty("userName"), properties.getProperty("password"));
					// 1-1
					PreparedStatement searchUserPstmt = conn
							.prepareStatement(properties.getProperty("searchingUserDetails"));
					// 1-2
					PreparedStatement registerUserPstmt = conn
							.prepareStatement(properties.getProperty("registerUserQuery"));
					PreparedStatement userFinePstmt = conn.prepareStatement("userFineQuery");) {
				searchUserPstmt.setString(1, user.getEmailId());
				searchUserPstmt.setString(2, typeOfUser);
				ResultSet searchUserRSet = searchUserPstmt.executeQuery();
				if (searchUserRSet.next()) {
					throw new UserException("User Already Exists \nGo to Login page");
				} else {

					registerUserPstmt.setInt(1, user.getId());
					registerUserPstmt.setString(2, user.getName());
					registerUserPstmt.setString(3, user.getEmailId());
					registerUserPstmt.setString(4, user.getPassword());
					registerUserPstmt.setLong(5, user.getMobileNo());
					registerUserPstmt.setString(6, typeOfUser);
					if (typeOfUser.equals("STUDENT")) {

						userFinePstmt.setInt(1, user.getId());
						userFinePstmt.setString(2, user.getEmailId());
						userFinePstmt.setFloat(3, 0.0f);
						int noOfRowsUpdated = registerUserPstmt.executeUpdate();
						int finesUpdated = userFinePstmt.executeUpdate();
						if (noOfRowsUpdated == 1 && finesUpdated == 1) {
							isRegisterd = true;

						} else {
							throw new UserException("Not Registered");
						}

					} else {
						int noOfRowsUpdated = registerUserPstmt.executeUpdate();
						if (noOfRowsUpdated == 1) {
							isRegisterd = true;

						} else {
							throw new UserException("Not Registered");
						}
					}

				}
			}
		} catch (UserException e) {
			System.err.println(e.getMessage());
		} catch (Exception e) {
			System.err.println("SOME THING WENT WRONG");
		}
		return isRegisterd;
	}// End Of Register User Method

	// 2
	/*
	 * LoginUser is also helps us to login both admin as well as Student based on
	 * aruguments passed into the typeOfUser and it will return userPrimaryInfo
	 * first it will cjheck user(emailid,typeOfUser) will exists or not.if user
	 * exists then it will check for password if not exists it give a error message
	 * to the user
	 * 
	 * 
	 */
	@Override
	public UserPrimaryInfo loginUser(String userMailID, String userPassword, String typeOfUser) {
		UserPrimaryInfo userInfo = null;
		try (FileInputStream fis = new FileInputStream("database.properties");) {
			Properties properties = new Properties();
			properties.load(fis);
			Class.forName(properties.getProperty("mysqlDriverClass")).newInstance();
			try (Connection conn = DriverManager.getConnection(properties.getProperty("path"),
					properties.getProperty("userName"), properties.getProperty("password"));
					// 1
					PreparedStatement searchUserPstmt = conn
							.prepareStatement(properties.getProperty("searchingUserDetails"));
					) {
				searchUserPstmt.setString(1, userMailID);
				searchUserPstmt.setString(2, typeOfUser);

				try (ResultSet searchUserRSet = searchUserPstmt.executeQuery();) {
					if (searchUserRSet.next()) {
						if (searchUserRSet.getString("emailid").equals(userMailID)
								&& searchUserRSet.getString("password").equals(userPassword)
								&& searchUserRSet.getString("typeofuser").equals(typeOfUser)) {

							userInfo = new UserPrimaryInfo();
							userInfo.setId(searchUserRSet.getInt("id"));
							userInfo.setName(searchUserRSet.getString("name"));
							userInfo.setEmailId(searchUserRSet.getString("emailId"));
							userInfo.setMobileNo(searchUserRSet.getLong("mobileno"));
							userInfo.setTypeOfUser(searchUserRSet.getString("typeofuser"));

						} else {
							throw new UserException("Invalid Credentials");
						}

					} else {
						throw new UserException("User Data Not Avalable ");
					}
				}
			}

		} catch (UserException e) {
			System.err.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("SOME THING WENT WORNG");
		}
		return userInfo;
	}

	// 3
	/*
	 * as same as loginUser and RegisterUser() for both admin and student
	 * 
	 * it will check for user mailID password and the update the new PAssword
	 */
	@Override
	public boolean updateDetails(String mailId, String password, String newPassword, String typeOfUser) {
		boolean isUpdated = false;

		try (FileInputStream fis = new FileInputStream("database.properties");) {
			Properties properties = new Properties();
			properties.load(fis);
			Class.forName(properties.getProperty("mysqlDriverClass")).newInstance();
			try (Connection conn = DriverManager.getConnection(properties.getProperty("path"),
					properties.getProperty("userName"), properties.getProperty("password"));
					// 5
					PreparedStatement pstmt = conn.prepareStatement(properties.getProperty("updateUserQuery"));) {
				pstmt.setString(1, newPassword);
				pstmt.setString(2, mailId);
				pstmt.setString(3, password);
				pstmt.setString(4, typeOfUser);
				int updatedRows = pstmt.executeUpdate();
				if (updatedRows == 1) {
					isUpdated = true;
				} else {
					throw new UserException("Invalid Credentails");
				}
			}
		} catch (UserException e) {
			System.err.println("Enter valid credentials");
		} catch (Exception e) {
			System.err.println("SOME THING WENT WORNG");
		}
		return isUpdated;

	}

	// 4

//	  only for admin this method will takes a bookPrimaryInfo and returns a boolean
//	  if the book is added successfuly it returns true other wise it returns false
//	  
//	  1. In MySQl table will taken care for bookId. for every row bookID
//	  will be automatically incremented by 1 to the next book.
//	  2.By Default bookUniqueId will be taken care by Java Program BookUniqueId will be
//	  like "EEE1001" and next book of the same Category will added one to the
//	  BookUniqueId next id = "EEE1002" 
//	 for every category it will start from "category(ThreeCharacters)1001" and next 1002
//	   for example i am taken a Electrical machinery category EEE by
//	  
//	 
//	  if the user wants to add 10 books of
//	  BOOKNAME ELECTRICALMACHINERY BOOKCATEGORY EEE.
//	   then the query will exceute 10 times and id will be incremented every time.
//	  and bookUniqueid will be same for all 10 books 
//	   first bookid 1 and last bookId - 10 and bookUniqyeID = "EEE1001"
//	   i am giving a bookUniqueId(based upon Category of the
//	  book)
//	  if i want to add another book 
//	  BookName ElectroMagenticfiels & bookCAtehory EEE
//	  i want to add 2 books
//	  now bookId's = 11,12 and bookUniqueId = "EEE1002"
//	
	@Override
	public boolean addBook(BookPrimaryInfo bookInfo) {
		int added = 0;
		boolean isAdded = false;
		String bookUnique = null;
		try (FileInputStream fis = new FileInputStream("database.properties");) {
			Properties properties = new Properties();
			properties.load(fis);
			Class.forName(properties.getProperty("mysqlDriverClass")).newInstance();
			try (Connection conn = DriverManager.getConnection(properties.getProperty("path"),
					properties.getProperty("userName"), properties.getProperty("password"));
					// 6
					PreparedStatement addBookPstmt = conn.prepareStatement(properties.getProperty("addBookQuery"));
					// 7
					PreparedStatement lastrowPstmt = conn.prepareStatement(properties.getProperty("lastRowQuery"));) {
				lastrowPstmt.setString(1, bookInfo.getBookCategory());
				try (ResultSet lastRowResultSet = lastrowPstmt.executeQuery();) {
					Savepoint savepoint = conn.setSavepoint();
					if (lastRowResultSet.next()) {
						String lastRow = lastRowResultSet.getString(1);
						int categoryUniqueId = Integer.parseInt(lastRow.substring(3));
						bookUnique = bookInfo.getBookCategory() + (categoryUniqueId + 1);
					} else {
						bookUnique = bookInfo.getBookCategory() + 1001;
					}
					for (int i = 1; i <= bookInfo.getNoOfBooks(); i++) {
						addBookPstmt.setString(1, bookUnique);
						addBookPstmt.setString(2, bookInfo.getBookName());
						addBookPstmt.setString(3, bookInfo.getBookAuthor());
						addBookPstmt.setString(4, bookInfo.getBookPublisherName());
						addBookPstmt.setString(5, bookInfo.getBookCategory());
						added = addBookPstmt.executeUpdate();
					}
					if (added > 0) {
						isAdded = true;
					} else {
						conn.rollback(savepoint);
						throw new UserException("books Are Not Added into Library");
					}
				}
			}
		} catch (UserException e) {
			System.err.println("books Are Not Added into Library");
		} catch (Exception e) {
			System.err.println("Some Thing Went Worng");
		}
		return isAdded;
	}

	// 5
	// this method accepts a three argumrnts bookID and mailId and typeofUser
	// Searching the user details weather user exists or not(Student) only student
	// can take a books anf admin only issue the books
	//
	//

	@Override
	public boolean issueBook(int requestBookId, String studentMailId, String typeOfUser) {
		int noOfBooksBorrowed = 0;
		boolean isIssued = false;
		try (FileInputStream fis = new FileInputStream("database.properties");) {
			Properties properties = new Properties();
			properties.load(fis);
			Class.forName(properties.getProperty("mysqlDriverClass")).newInstance();
			try (Connection conn = DriverManager.getConnection(properties.getProperty("path"),
					properties.getProperty("userName"), properties.getProperty("password"));
					// 1
					PreparedStatement searchingUserPsmt = conn
							.prepareStatement(properties.getProperty("searchingUserDetails"));
					// 8
					PreparedStatement bookInfoPstmt = conn
							.prepareStatement(properties.getProperty("searchingBookDetailsQuery"));
					// 9
					PreparedStatement booksBorrowedPstmt = conn
							.prepareStatement(properties.getProperty("checkBooksBorrowedQuery"));
					// 10
					PreparedStatement bookIssuePstmt = conn.prepareStatement(properties.getProperty("issueBookQuery"));
					// 11
					PreparedStatement borrowedUpdatePstmt = conn
							.prepareStatement(properties.getProperty("booksBorrowed"));
					// 12
					PreparedStatement updateBookStatusPstmt = conn
							.prepareStatement(properties.getProperty("updateBooksStatusQuery"));) {

				searchingUserPsmt.setString(1, studentMailId);
				searchingUserPsmt.setString(2, typeOfUser);
				try (ResultSet studentResultSet = searchingUserPsmt.executeQuery();) {
					if (studentResultSet.next()) {

						bookInfoPstmt.setInt(1, requestBookId);
						ResultSet bookInfoResultSet = bookInfoPstmt.executeQuery();
						if (bookInfoResultSet.next()) {

							booksBorrowedPstmt.setString(1, studentMailId);
							try (ResultSet bookBorrowedResultSet = booksBorrowedPstmt.executeQuery();) {
								if (bookBorrowedResultSet.next()) {
									System.out.println(bookBorrowedResultSet.getInt("pendingBooks"));
									noOfBooksBorrowed = bookBorrowedResultSet.getInt("pendingBooks");
								}
								if (noOfBooksBorrowed < 3) {

									Savepoint savepoint = conn.setSavepoint();

									bookIssuePstmt.setInt(1, requestBookId);
									bookIssuePstmt.setInt(2, studentResultSet.getInt("id"));

									borrowedUpdatePstmt.setInt(1, requestBookId);
									borrowedUpdatePstmt.setString(2, studentMailId);

									updateBookStatusPstmt.setInt(1, requestBookId);

									int bookIssueUpdate = bookIssuePstmt.executeUpdate();
									int borrowedUpdate = borrowedUpdatePstmt.executeUpdate();
									int updateStatus = updateBookStatusPstmt.executeUpdate();

									if (bookIssueUpdate != 0 && borrowedUpdate != 0 && updateStatus != 0) {
										isIssued = true;
									} else {
										conn.rollback(savepoint);
										throw new UserException("Not Updates");
									}
								} else {
									throw new UserException("Student Reached Max. Limit number Of Books");
								}
							}

						} else {
							throw new UserException("Book Data Not Availble");
						}

					} else {
						throw new UserException("Enter Valid Student Mail ID");
					}
				}
			}
		} catch (UserException e) {
			System.err.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isIssued;
	}

	// 6
	@Override
	public boolean removeBook(int bookId) {
		boolean isRemoved = false;
		try (FileInputStream fis = new FileInputStream("database.properties");) {
			Properties properties = new Properties();
			properties.load(fis);
			Class.forName(properties.getProperty("mysqlDriverClass")).newInstance();
			try (Connection conn = DriverManager.getConnection(properties.getProperty("path"),
					properties.getProperty("userName"), properties.getProperty("password"));
					PreparedStatement searchPstmt = conn
							.prepareStatement(properties.getProperty("searchBookInfoQuery"));
					// 13
					PreparedStatement removeBookPsmt = conn
							.prepareStatement(properties.getProperty("removeBookQuery"));) {
				searchPstmt.setInt(1, bookId);
				try (ResultSet searchResultSet = searchPstmt.executeQuery();) {
					if (searchResultSet.next()) {
						removeBookPsmt.setInt(1, bookId);
						int noOfRowsRemoved = removeBookPsmt.executeUpdate();
						if (noOfRowsRemoved != 0) {
							isRemoved = true;
						} else {
							throw new UserException("Book Not Removed From Library");
						}
					} else {
						throw new UserException("Book Not Availble In The Library");
					}
				}
			}
		} catch (UserException e) {
			System.err.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException("May Book Not Available");
		}

		return isRemoved;
	}

	private static long differenceDate(Date issuedDate, Date returnDate) {
		long days = (returnDate.getTime() - issuedDate.getTime()) / 86400000;

		return days;
	}

	// 7
	@Override
	public boolean returnBook(int returnBookId, String studentMailId) {
		float finePrice = 0;
		int noOfRowsDeleted;
		int updatedBookInfo;
		boolean isDeleted = false;
		try (FileInputStream fis = new FileInputStream("database.properties")) {
			Properties properties = new Properties();
			properties.load(fis);
			Class.forName(properties.getProperty("mysqlDriverClass")).newInstance();
			try (Connection conn = DriverManager.getConnection(properties.getProperty("path"),
					properties.getProperty("userName"), properties.getProperty("password"));
					// 1
					PreparedStatement searchUserPstmt = conn
							.prepareStatement(properties.getProperty("searchingUserDetails"));
					// 14
					PreparedStatement searchUserIssuePsmt = conn
							.prepareStatement(properties.getProperty("issueBookdetails"));
					// 15
					PreparedStatement searchReturnBookPstmt = conn
							.prepareStatement(properties.getProperty("searchReturnBooks"));
					// 16
					PreparedStatement importFinesPstmt = conn.prepareStatement(properties.getProperty("importFines"));
					// 18
					PreparedStatement updatingIssueBookPstmt = conn
							.prepareStatement(properties.getProperty("updateIssueBooks"));
					// 19
					PreparedStatement deleteRowPstmt = conn
							.prepareStatement(properties.getProperty("deleteBorrowedBooks"));
					// 20
					PreparedStatement updateFinesPstmt = conn.prepareStatement(properties.getProperty("insertFines"));
					// 21
					PreparedStatement updatingReturnBooks = conn
							.prepareStatement(properties.getProperty("updateBookInfo"));

			) {
				searchUserPstmt.setString(1, studentMailId);
				searchUserPstmt.setString(2, "admin");
				try (ResultSet searchResultSet = searchUserPstmt.executeQuery();) {
					if (searchResultSet.next()) {

						searchUserIssuePsmt.setString(1, studentMailId);
						try (ResultSet studentResultSet = searchUserIssuePsmt.executeQuery();) {
							if (studentResultSet.next() == true) {

								importFinesPstmt.setInt(1, studentResultSet.getInt("Id"));
								try (ResultSet importFinesRSet = importFinesPstmt.executeQuery();) {
									if (importFinesRSet.next()) {
										finePrice = importFinesRSet.getFloat("fines");
									}
									searchReturnBookPstmt.setString(1, studentMailId);
									searchReturnBookPstmt.setInt(2, returnBookId);
									try (ResultSet searchBookResultSet = searchReturnBookPstmt.executeQuery();) {
										if (searchBookResultSet.next()) {
											if (searchBookResultSet.getString("mailID").equals(studentMailId)
													&& searchBookResultSet.getInt("bookId") == returnBookId) {
												Date issuedDate = searchBookResultSet.getDate("issuedDate");
												Date returnDate = new Date();
												long noOfDays = differenceDate(issuedDate, returnDate);
												System.out.println(noOfDays);
												int noOfDaysint = (int) noOfDays;
												System.out.println(noOfDaysint);
												if (noOfDays <= 7) {
													finePrice = 0;
												} else {
													finePrice = noOfDays * 1.5f;
												}

												updatingIssueBookPstmt.setFloat(1, finePrice);
												updatingIssueBookPstmt.setString(2, studentMailId);
												updatingIssueBookPstmt.setInt(3, returnBookId);
												updatingIssueBookPstmt.setString(4,
														studentResultSet.getString("bookUniqueId"));

												updatingIssueBookPstmt.setDate(5,
														searchBookResultSet.getDate("issuedDate"));
												Savepoint savepoint = conn.setSavepoint();
												int updated = updatingIssueBookPstmt.executeUpdate();

												deleteRowPstmt.setInt(1, returnBookId);
												updateFinesPstmt.setFloat(1, finePrice);
												updateFinesPstmt.setInt(2, studentResultSet.getInt("id"));
												updatingReturnBooks.setInt(1, returnBookId);
												noOfRowsDeleted = deleteRowPstmt.executeUpdate();
												updatedBookInfo = updatingReturnBooks.executeUpdate();
												if (noOfDays >= 7) {
													int finesUpdated = updateFinesPstmt.executeUpdate();
													if (noOfRowsDeleted == 1 && finesUpdated == 1 && updated == 1
															&& updatedBookInfo == 1) {
														isDeleted = true;
													} else {
														conn.rollback(savepoint);
														isDeleted = false;
													}
												} else {
													updatedBookInfo = updatingReturnBooks.executeUpdate();
													if (noOfRowsDeleted == 1 && updated == 1 && updatedBookInfo == 1) {
														isDeleted = true;
													} else {
														conn.rollback(savepoint);
														isDeleted = false;
													}
												}

												// a = issuedDate-returnBookId;
											} else {
												throw new UserException("Please Enter Valid BookId and Student MailID");
											}
										} else {
											throw new UserException("Book Doesn't taken by student");
										}
									}
								}

							} else {
								throw new UserException("Student data not available");
							}
						}

					} else {
						throw new UserException("Enter Valid Credentials");
					}
				}
			} // Main TRY Block
		} catch (UserException e) {
			System.err.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isDeleted;
	}

	// 8
	@Override
	public BookPrimaryInfo searchBook(int searchBookId) {
		BookPrimaryInfo bookInfo = null;
		try (FileInputStream fis = new FileInputStream("database.properties")) {
			Properties properties = new Properties();
			properties.load(fis);
			Class.forName(properties.getProperty("mysqlDriverClass")).newInstance();
			try (Connection conn = DriverManager.getConnection(properties.getProperty("path"),
					properties.getProperty("userName"), properties.getProperty("password"));
					// 21
					PreparedStatement searchingUserPsmt = conn
							.prepareStatement(properties.getProperty("searchBookId"))) {

				searchingUserPsmt.setInt(1, searchBookId);
				try (ResultSet searchResultSet = searchingUserPsmt.executeQuery();) {
					if (searchResultSet.next()) {
						bookInfo = new BookPrimaryInfo();
						bookInfo.setBookId(searchResultSet.getInt("bookId"));
						bookInfo.setBookName(searchResultSet.getString("bookName"));
						bookInfo.setBookPublisherName(searchResultSet.getString("bookPublisherName"));
						bookInfo.setBookAuthor(searchResultSet.getString("bookAuthor"));
						bookInfo.setBookCategory(searchResultSet.getString("bookCategory"));
						bookInfo.setBookUniqueId(searchResultSet.getString("bookUniqueId"));
						bookInfo.setBookStatus(searchResultSet.getString("bookStatus"));
					} else {
						throw new UserException("Book Not Avaliable");
					}
				}
			}
		} catch (UserException e) {
			System.err.println("Exception At Book Search");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bookInfo;
	}

	// 9
	@Override
	public BookPrimaryInfo requestBook(int requestBookId) {
		BookPrimaryInfo bookInfo = new BookPrimaryInfo();
		try (FileInputStream fis = new FileInputStream("database.properties")) {
			Properties properties = new Properties();
			properties.load(fis);
			Class.forName(properties.getProperty("mysqlDriverClass")).newInstance();
			try (Connection conn = DriverManager.getConnection(properties.getProperty("path"),
					properties.getProperty("userName"), properties.getProperty("password"));
					// 22
					PreparedStatement requestBookPstmt = conn
							.prepareStatement(properties.getProperty("requestBookId"))) {
				requestBookPstmt.setInt(1, requestBookId);
				try (ResultSet requestResultSet = requestBookPstmt.executeQuery();) {
					if (requestResultSet.next()) {
						bookInfo.setBookId(requestBookId);
						bookInfo.setBookUniqueId(requestResultSet.getString("bookUniqueId"));
						bookInfo.setBookName(requestResultSet.getString("bookName"));
						bookInfo.setBookAuthor(requestResultSet.getString("bookAuthor"));
						bookInfo.setBookPublisherName(requestResultSet.getString("bookPublisherName"));
						bookInfo.setBookCategory(requestResultSet.getString("bookCategory"));
						bookInfo.setBookStatus(requestResultSet.getString("bookStatus"));
					} else {
						throw new UserException("Book Not Avalable");
					}
				}
			}
		} catch (UserException e) {
			System.err.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	// 10
	@Override
	public List<BookIssueInfo> borrowedBooks(String studentMailId) {
		BookIssueInfo bookIssueInfo = new BookIssueInfo();
		List<BookIssueInfo> list = new ArrayList<BookIssueInfo>();
		try (FileInputStream fis = new FileInputStream("database.properties")) {
			Properties properties = new Properties();
			properties.load(fis);
			Class.forName(properties.getProperty("mysqlDriverClass")).newInstance();
			try (Connection conn = DriverManager.getConnection(properties.getProperty("path"),
					properties.getProperty("userName"), properties.getProperty("password"));
					PreparedStatement borrowedBooksPstmt = conn
							.prepareStatement(properties.getProperty("borrowedBooks"));
					PreparedStatement bookIssueInfoPstmt = conn
							.prepareStatement(properties.getProperty("bookIssueInfo"))) {

				borrowedBooksPstmt.setString(1, studentMailId);
				try (ResultSet borrowedBooksResultSet = borrowedBooksPstmt.executeQuery();) {
					if (borrowedBooksResultSet.next()) {
						bookIssueInfoPstmt.setString(1, borrowedBooksResultSet.getString("mailId"));
						try (ResultSet issuedinfoResultSet = bookIssueInfoPstmt.executeQuery();) {

							while (issuedinfoResultSet.next()) {
								bookIssueInfo.setIssuedBookId(issuedinfoResultSet.getInt("id"));
								bookIssueInfo.setIssuedBookUniqueId(issuedinfoResultSet.getString("bookUniqueId"));
								bookIssueInfo.setIssuedBookName(issuedinfoResultSet.getString("bookName"));
								bookIssueInfo.setIssuedBookAuthor(issuedinfoResultSet.getString("bookAuthorName"));
								bookIssueInfo
										.setIssuedBookPublisherName(issuedinfoResultSet.getString("bookPublisherName"));
								bookIssueInfo.setIssuedBookCategory(issuedinfoResultSet.getString("bookCategory"));
								bookIssueInfo.setIssuedDate(borrowedBooksResultSet.getDate("issuedDate"));
								bookIssueInfo.setActualReturnDate(issuedinfoResultSet.getDate("actualReturnDate"));
								bookIssueInfo.setFine(issuedinfoResultSet.getFloat("fine"));

								list.add(bookIssueInfo);
							}
						}
					} else {
						throw new UserException("Student did'nt take any books");
					}
				}
			}
		} catch (UserException e) {
			System.err.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}
