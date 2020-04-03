package com.capgemini.librarymanagementjdbc.service;

import java.util.List;

import com.capgemini.librarymanagementjdbc.dao.UserDAO;
import com.capgemini.librarymanagementjdbc.dto.BookIssueInfo;
import com.capgemini.librarymanagementjdbc.dto.BookPrimaryInfo;
import com.capgemini.librarymanagementjdbc.dto.UserPrimaryInfo;
import com.capgemini.librarymanagementjdbc.factory.UserFactory;

public class UserServiceImplementation implements UserService {

	UserDAO dao = UserFactory.getUserDAO();

	@Override
	public boolean registerUser(UserPrimaryInfo user, String typeOfUser) {
		return dao.registerUser(user, typeOfUser);
	}

	@Override
	public UserPrimaryInfo loginUser(String mailId, String userPassword, String typeOfUser) {
		return dao.loginUser(mailId, userPassword, typeOfUser);
	}

	@Override
	public boolean updateDetails(String mailId, String password, String newPassword, String typeOfUser) {
		return dao.updateDetails(mailId, password, newPassword, typeOfUser);
	}

	@Override
	public boolean addBook(BookPrimaryInfo bookInfo) {
		return dao.addBook(bookInfo);
	}

	@Override
	public boolean removeBook(int bookId) {
		return dao.removeBook(bookId);
	}

	@Override
	public boolean issueBook(int borrowBookId, String studentMailId, String typeOfUser) {
		return dao.issueBook(borrowBookId, studentMailId, typeOfUser);
	}

	@Override
	public boolean returnBook(int returnBookId, String studentMailId) {
		return dao.returnBook(returnBookId, studentMailId);
	}

	@Override
	public BookPrimaryInfo searchBook(int searchBookId) {
		return dao.searchBook(searchBookId);
	}

	@Override
	public BookPrimaryInfo requestBook(int requestBookId) {
		return dao.requestBook(requestBookId);
	}

	@Override
	public List<BookIssueInfo> borrowedBooks(String studentMailId) {
		return dao.borrowedBooks(studentMailId);
	}

}
