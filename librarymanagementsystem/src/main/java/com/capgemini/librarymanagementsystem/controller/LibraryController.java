package com.capgemini.librarymanagementsystem.controller;

import java.util.Scanner;

import com.capgemini.librarymanagementsystem.dao.StudentDAO;
import com.capgemini.librarymanagementsystem.database.DataBase;
import com.capgemini.librarymanagementsystem.dto.AdminPrimaryInfo;
import com.capgemini.librarymanagementsystem.dto.BookPrimaryInfo;
import com.capgemini.librarymanagementsystem.dto.StudentPrimaryInfo;
import com.capgemini.librarymanagementsystem.exception.AdminException;
import com.capgemini.librarymanagementsystem.exception.BookException;
import com.capgemini.librarymanagementsystem.exception.StudentException;
import com.capgemini.librarymanagementsystem.factory.AdminFactory;
import com.capgemini.librarymanagementsystem.factory.StudentFactory;
import com.capgemini.librarymanagementsystem.service.AdminService;

public class LibraryController {
	public static void main(String[] args) {
		performingOperations();
	}

	public static void performingOperations() {
		try (Scanner scan = new Scanner(System.in)) {
			System.out.println("Press 1 for Admin Operations");
			System.out.println("Press 2 for Student Operations");
			System.out.println("Press 3 Key for Exit");
			System.out.println("Enter Key");
			int key = scan.nextInt();
			switch (key) {
			case 1:
				adminOperations();
				break;
			case 2:
				studentOperations();
				break;
			case 3:
				exit();
				break;
			default:
				System.out.println("Enter Valid Data");
				break;
			}
		}

	}

	public static void adminOperations() {
		try (Scanner scan = new Scanner(System.in)) {

			AdminPrimaryInfo adminBean = new AdminPrimaryInfo();

			AdminService adminService = AdminFactory.getAdminService();
			int key;
			do {
				boolean isLoggedIn = false;

				do {

					System.out.println("\nPress 1 to Register Admin Details");
					System.out.println("Press 2 to Login to Admin Details");
					System.out.println("Press 3 to update admin password");
					System.out.println("Press 4 to add a book to Library");
					System.out.println("Press 5 to remove a book to Library");
					System.out.println("Press 6 to Issue a book to Student");
					System.out.println("Press 7 to return a book to Library");
					System.out.println("Press 8 to search a book from Library");
					System.out.println("Press 9 to Logout from Admin");
					System.out.println("-----------------------------------");

					System.out.println("Enter key");

					key = scan.nextInt();

					switch (key) {

					// Register Admin
					case 1:

						System.out.println("Enter Admin Id");
						adminBean.setId(Integer.parseInt(scan.next()));
						System.out.println("Enter UserName");
						adminBean.setUserName(scan.next());
						System.out.println("Enter First Name");
						adminBean.setFirstName(scan.next());
						System.out.println("Enter Last Name");
						adminBean.setLastName(scan.next());
						System.out.println("Enter the Email Id");
						adminBean.setMailId(scan.next());
						System.out.println("Enter the Mobile number");
						adminBean.setMobileNo(Long.parseLong(scan.next()));
						System.out.println("Enter Password");
						adminBean.setPassword(scan.next());

						try {
							boolean isCreated = adminService.registerAdmin(adminBean);
							if (isCreated)
								System.out.println("Sucessfully Registered");
						} catch (AdminException e) {
							System.err.println(e.getMessage());
						}
						break;

					// login Admin
					case 2:

						System.out.println("\n\nEnter mailID");
						String mailId = scan.next();
						System.out.println("Enter Password");
						String password = scan.next();
						try {
							AdminPrimaryInfo adminLoginInfo = adminService.loginAdmin(mailId, password);
							if (adminLoginInfo != null) {
								System.out.println("Successfully LoggedIn into account");
								isLoggedIn = true;
							}
						} catch (AdminException e) {
							System.err.println(e.getMessage());

						}
						break;
					// -----------------Update admin Password Info---------------------------
					case 3:
						if (isLoggedIn) {
							System.out.println("\n\nEnter mailID");
							String updateMailId = scan.next();
							System.out.println("Enter Old Password");
							String oldPassword = scan.next();
							System.out.println("Enter New Password");
							String newPassword = scan.next();
							try {
								adminService.updateDetails(updateMailId, oldPassword, newPassword);
								System.out.println("Admin Password Changed");
							} catch (AdminException e) {
								e.printStackTrace();
							}

						} else {
							System.err.println("Please go to Login Page");
						}
						break;
					// --------------- ADD a book to Library---------------
					case 4:
						if (isLoggedIn) {
							BookPrimaryInfo book = new BookPrimaryInfo();
							System.out.println("\n\nEnter BookId");
							book.setBookId(Integer.parseInt(scan.next()));
							System.out.println("Enter Book Name");
							book.setBookName(scan.next());
							System.out.println("Enter Book Author");
							book.setBookAuthor(scan.next());
							System.out.println("Enter Book Publisher Name");
							book.setBookPublisherName(scan.next());
							System.out.println("Enter Book Category");
							book.setBookcategory(scan.next());
							System.out.println("Enter Number Of Books");
							book.setNoOfBooks(scan.nextInt());
							System.out.println("book added to the library sucessfully");
							try {
								boolean isBookAdded = adminService.addBook(book);
								if (isBookAdded) {
									System.out.println("Book Added Successfully");
								}
							} catch (BookException e) {
								System.err.println(e.getMessage());
							}
						} else {
							System.err.println("Please Go to Login Page");
						}

						break;

					// --------------------remove book---------------

					case 5:
						if (isLoggedIn) {
							System.out.println("\n\nEnter BookID for removing a book from library");
							int bookId = scan.nextInt();
							try {
								boolean isRemoved = adminService.removeBook(bookId);
								if (isRemoved) {
									System.out.println("Succesfully Removed from Library");
								}
							} catch (BookException e) {
								throw new BookException(e.getMessage());
							}
						} else {
							System.err.println("Please Go Login Page");
						}
						break;
					// -----------------issuing book-----------------
					case 6:
						if (isLoggedIn) {
							System.out.println("Issuing a Book From Library");
							System.out.println("\n\nEnter BookId ");
							int issueBookId = scan.nextInt();
							System.out.println("Enter Student MailId");
							String studentMailId = scan.next();
							try {
								BookPrimaryInfo issuedBook = adminService.issueBook(issueBookId, studentMailId);
								StudentPrimaryInfo stuinfo = DataBase.STUDENT_DB.get(studentMailId);
								System.out.println("Issued Book Details ");
								System.out.println("Book Id" + issuedBook.getBookId());
								System.out.println("Book Name" + issuedBook.getBookName());
								System.out.println("Student Id " + stuinfo.getId());
								System.out.println("Student First Name" + stuinfo.getFirstName());

							} catch (Exception e) {

								throw new BookException("Enterd BookID number Is Not Avaliable in library");
							}
						} else {
							System.err.println("Please Go Login Page");
						}
						break;

					case 7:
						if (isLoggedIn) {
							System.out.println("\n\nEnter bookId to return to library");

							int bookId = (Integer.parseInt(scan.nextLine()));

							System.out.println("Enter Student MailID");
							String studentMailId = scan.next();

							try {
								boolean isReturned = adminService.returnBook(bookId, studentMailId);
								if (isReturned) {
									System.out.println("Book Was Successfully Returned And To Library");
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							System.err.println("Please Go Login Page");
						}
						break;
					case 8:
						if (isLoggedIn) {
							System.out.println("\n\nSearhing a Book from Library");
							System.out.println("Enter bookId");
							int searchBookId = scan.nextInt();

							BookPrimaryInfo searchBook = adminService.searchBook(searchBookId);
							if (searchBook != null) {
								System.out.println("Book ID : " + searchBook.getBookId());
								System.out.println("Book Name : " + searchBook.getBookName());
								System.out.println("Book Author : " + searchBook.getBookAuthor());
								System.out.println("Book Category : " + searchBook.getBookcategory());
								System.out.println("Book Publisher Name : " + searchBook.getBookPublisherName());
								System.out.println("Number Of Books Available : " + searchBook.getNoOfBooks());
							} else {
								throw new BookException("Book Not Found");
							}
						} else {
							System.out.println("Please Go Login Page");
						}
						break;
					case 9:
						if (isLoggedIn) {
							System.out.println("Admin is LoggedOut successfully");
							isLoggedIn = false;
							performingOperations();
						} else {
							System.err.println("Admin not Log in");
						}
						break;
					default:
						System.out.println("Invalid key Entered");
					}
				} while (isLoggedIn);
			} while (true);
		}

	}

// Student Data
//
	public static void studentOperations() {

		try (Scanner scan = new Scanner(System.in);) {
			boolean isStudentLogin = false;
			StudentPrimaryInfo studentBean = new StudentPrimaryInfo();
			StudentDAO studentService = StudentFactory.getStudentDAO();
			do {
				do {

					System.out.println("Press 1 to Register Student");
					System.out.println("Press 2 to Login Student");
					System.out.println("Press 3 to Request Book");
					System.out.println("Press 4 to Search Book");
					System.out.println("Press 5 to Borrow Book");
					System.out.println("Enter Choice");

					int choice = scan.nextInt();
					switch (choice) {
					case 1:
						System.out.println("Registration Process");
						System.out.println("Enter Student Id");
						studentBean.setId(scan.nextInt());
						System.out.println("Enter Student First Name");
						studentBean.setFirstName(scan.next());
						System.out.println("Enter Student Last Name");
						studentBean.setLastName(scan.next());
						System.out.println("Enter MailID");
						studentBean.setMailId(scan.next());
						System.out.println("Enter Password");
						studentBean.setPassword(scan.next());
						System.out.println("Enter MobileNumber");
						studentBean.setMobileNo(scan.nextLong());
						System.out.println("Enter Department");
						studentBean.setDepartment(scan.next());
						System.out.println("Enter Level");
						studentBean.setLevel(scan.nextInt());
						System.out.println("Enter Metric NO");
						studentBean.setMetricNo(scan.nextInt());
						boolean isCreated = studentService.registerStudent(studentBean);
						if (isCreated) {
							System.out.println("Sucessfully Registered");
						} else {
							System.out.println("Not Registered");
						}
						break;

					case 2:

						System.out.println("\n\nEnter mailID");
						String mailId = scan.next();
						System.out.println("Enter Password");
						String password = scan.next();
						try {
							StudentPrimaryInfo studentLoginInfo = studentService.loginStudent(mailId, password);
							if (studentLoginInfo != null) {
								System.out.println("Successfully LoggedIn into account");
								isStudentLogin = true;
							}
						} catch (AdminException e) {
							System.err.println(e.getMessage());

						}
						break;

//					case 3:
//						System.out.println("Request A Book");
//						System.out.println("Enter BookId ");
//						int requestBookId = scan.nextInt();
//						try {
//							BookPrimaryInfo bookInfo = studentService.requestBook(requestBookId);
//						} catch (StudentException s) {
//							throw new StudentException("May be Book Not Avaliable try another Book id");
//						}
//						break;

					case 4:
						if (isStudentLogin) {
							System.out.println("Search A Book");
							System.out.println("Enter bookId");
							int searchBookId = scan.nextInt();
							try {
								BookPrimaryInfo searchBook = studentService.searchBook(searchBookId);
								if (searchBook != null) {
									System.out.println("Book ID : " + searchBook.getBookId());
									System.out.println("Book Name : " + searchBook.getBookName());
									System.out.println("Book Author : " + searchBook.getBookAuthor());
									System.out.println("Book Category : " + searchBook.getBookcategory());
									System.out.println("Book Publisher Name : " + searchBook.getBookPublisherName());
								} else {
									System.err.println("Book Not Found");
								}
							} catch (StudentException e) {
								System.err.println(e.getMessage());
							}
						} else {
							System.err.println("Please Go To Login Page");
						}
						break;

					case 5:

						System.out.println("Enter mailId of Student");
						String studentMailId = scan.next();
						try {

							StudentPrimaryInfo info = studentService.borrowedBooks(studentMailId);
							if (info != null) {
								System.out.println("Student MailId who borrowed" + studentMailId);
								System.out.println(info.getId());
								System.out.println(info.getFirstName());
								System.out.println(info.getNoOfBooksBorrowed());
							} else {
								System.err.println("Sorry Details InValid");
							}
						} catch (StudentException e) {
							System.err.println(e.getMessage());
						}
						break;

					case 6:
						isStudentLogin = false;
						if (isStudentLogin) {

							System.out.println("Student Account LoggedOut Successfully");
							performingOperations();
						} else {
							System.out.println("Student Account Not Logged Out");
						}

					default:
						System.err.println("Enter Valid Details");
						break;

					}
				} while (true);
			} while (isStudentLogin);

		}
	}

	private static void exit() {
		System.out.println("thank you");

	}
}
