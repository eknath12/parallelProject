package com.capgemini.librarymanagementsystem.dao;

import com.capgemini.librarymanagementsystem.database.DataBase;
import com.capgemini.librarymanagementsystem.dto.AdminPrimaryInfo;
import com.capgemini.librarymanagementsystem.dto.BookPrimaryInfo;
import com.capgemini.librarymanagementsystem.dto.StudentPrimaryInfo;
import com.capgemini.librarymanagementsystem.exception.AdminException;
import com.capgemini.librarymanagementsystem.exception.BookException;

public class AdminDAOImplementation implements AdminDAO {

	boolean isLoggedIn = false;

	public boolean registerAdmin(AdminPrimaryInfo adminInfo) {
		int size = DataBase.ADMIN_DB.size();
		if (size == 0) {
			DataBase.ADMIN_DB.put(adminInfo.getMailId(), adminInfo);
			return true;
		} else {
			if (adminInfo.getMailId() != null) {
				boolean isAdminExists = DataBase.ADMIN_DB.containsKey(adminInfo.getMailId());
				if (isAdminExists == false) {
					DataBase.ADMIN_DB.put(adminInfo.getMailId(), adminInfo);
					return true;
				} else {
					throw new AdminException("Admin mail Id Already Exits");
				}
			} else {
				throw new AdminException("Admin mail Id can't be Empty");
			}
		}

	}

	public AdminPrimaryInfo loginAdmin(String mailId, String userPassword) {

		boolean isExists = DataBase.ADMIN_DB.containsKey(mailId);
		if (isExists) {
			AdminPrimaryInfo dbAdminInfo = DataBase.ADMIN_DB.get(mailId);

			if (dbAdminInfo.getMailId().equals(mailId) && dbAdminInfo.getPassword().equals(userPassword)) {

				return dbAdminInfo;
			} else {

				throw new AdminException("Invalid Credentials");
			}
		} else {
			throw new AdminException("Mail Id Not Exists");
		}

	}

	public boolean updateDetails(String email, String oldPassword, String newPassword) {

		try {
			AdminPrimaryInfo adminInfo = DataBase.ADMIN_DB.get(email);
			if (adminInfo.getMailId().equals(email) && adminInfo.getPassword().equals(oldPassword)) {
				adminInfo.setPassword(newPassword);
				DataBase.ADMIN_DB.put(email, adminInfo);
				return true;
			} else {
				throw new AdminException("Invalid Credentils");
			}

		} catch (Exception e) {
			throw new AdminException("Id Not Vaild");
		}
	}

	public boolean addBook(BookPrimaryInfo bookInfo) {

		DataBase.BOOK_DB.put(bookInfo.getBookId(), bookInfo);
		boolean isAdded = DataBase.BOOK_DB.containsKey(bookInfo.getBookId());

		if (isAdded) {
			return isAdded; 
		} else {
			throw new BookException("Book Not Added to Library");
		}
	}

	public boolean removeBook(int bookId) {
		boolean bookExists = DataBase.BOOK_DB.containsKey(bookId);
		if (bookExists) {
			BookPrimaryInfo bookInfo = DataBase.BOOK_DB.get(bookId);
			int noOfBooksAvailable = bookInfo.getNoOfBooks();
			if (noOfBooksAvailable != 0) {
				noOfBooksAvailable--;
				bookInfo.setNoOfBooks(noOfBooksAvailable);
				DataBase.BOOK_DB.put(bookInfo.getBookId(), bookInfo);
			} else {
				throw new BookException("No Books Are Avaliable in Library with " + bookId);
			}
			return true;
		} else {
			throw new BookException("Maybe Book is not avaliable in Library");
		}

	}

	public BookPrimaryInfo issueBook(int borrowBookId, String studentMailId) {
		boolean isExists = DataBase.BOOK_DB.containsKey(borrowBookId);
		boolean isStudentExists = DataBase.STUDENT_DB.containsKey(studentMailId);
		if (isExists && isStudentExists) {

			BookPrimaryInfo bookPrimaryInfo = DataBase.BOOK_DB.get(borrowBookId);
			StudentPrimaryInfo studentPrimaryInfo = DataBase.STUDENT_DB.get(studentMailId);
			int noOfBooksBorrowed = studentPrimaryInfo.getNoOfBooksBorrowed();

			if (noOfBooksBorrowed <= 3) {
				int noOfBooksAvailble = bookPrimaryInfo.getNoOfBooks();
				if (noOfBooksAvailble > 0) {

					noOfBooksAvailble--;
					bookPrimaryInfo.setNoOfBooks(noOfBooksAvailble);
					DataBase.BOOK_DB.put(borrowBookId, bookPrimaryInfo);
					noOfBooksBorrowed++;
					studentPrimaryInfo.setNoOfBooksBorrowed(noOfBooksBorrowed);
					DataBase.STUDENT_DB.put(studentMailId, studentPrimaryInfo);
					return bookPrimaryInfo;
				} else {
					throw new BookException("Books Are Not Available in Library");
				}
			} else {
				throw new BookException("Student has max limit of books");
			}
		} else {
			throw new BookException("Book Data or Student Data is Not Correct ");
		}

	}

	public boolean returnBook(int returnBookId, String studentMailId) {
		boolean isStudentExists = DataBase.STUDENT_DB.containsKey(studentMailId);
		boolean isBookExits = DataBase.BOOK_DB.containsKey(returnBookId);
		if (isBookExits && isStudentExists) {
			BookPrimaryInfo bookInfo = DataBase.BOOK_DB.get(studentMailId);
			StudentPrimaryInfo studentInfo = DataBase.STUDENT_DB.get(returnBookId);
			int books = bookInfo.getNoOfBooks();
			books++;
			bookInfo.setNoOfBooks(books);
			int studentBooks = studentInfo.getNoOfBooksBorrowed();
			studentBooks--;
			studentInfo.setNoOfBooksBorrowed(studentBooks);
			DataBase.BOOK_DB.put(returnBookId, bookInfo);
			DataBase.STUDENT_DB.put(studentMailId, studentInfo);
			return true;
		} else {
			throw new AdminException("Student or Book Data Is Not Available");
		}
	}

	public BookPrimaryInfo searchBook(int searchBookId) {

		boolean isExists = DataBase.BOOK_DB.containsKey(searchBookId);
		if (isExists) {
			BookPrimaryInfo bookInfo = DataBase.BOOK_DB.get(searchBookId);
			return bookInfo;
		} else {
			throw new BookException("Enter Valid Book ID");
		}

	}
}