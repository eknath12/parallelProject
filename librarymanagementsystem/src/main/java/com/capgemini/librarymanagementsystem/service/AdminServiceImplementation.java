package com.capgemini.librarymanagementsystem.service;

import com.capgemini.librarymanagementsystem.dao.AdminDAO;
import com.capgemini.librarymanagementsystem.dto.AdminPrimaryInfo;
import com.capgemini.librarymanagementsystem.dto.BookPrimaryInfo;
import com.capgemini.librarymanagementsystem.factory.AdminFactory;

public class AdminServiceImplementation implements AdminService {
	private AdminDAO dao = AdminFactory.getAdminDAO();

	public boolean registerAdmin(AdminPrimaryInfo admin) {
		boolean t = dao.registerAdmin(admin);
		return t;
	}

	public AdminPrimaryInfo loginAdmin(String mailId, String userPassword) {
		return dao.loginAdmin(mailId, userPassword);
	}

	public boolean updateDetails(String mailId, String oldPassword,String newPassword) {

	return dao.updateDetails(mailId, oldPassword,newPassword);
	}

	public boolean addBook(BookPrimaryInfo bookInfo) {
		return dao.addBook(bookInfo);
	}

	public boolean removeBook(int bookId) {
		return dao.removeBook(bookId);
	}

	public BookPrimaryInfo issueBook(int bookId,String studentMailId) {
		return dao.issueBook(bookId,studentMailId);
	}

	public boolean returnBook(int returnBookId,String studentMailId) {
		return dao.returnBook(returnBookId,studentMailId);
	}
	public BookPrimaryInfo searchBook(int searchBookId) {
		return dao.searchBook(searchBookId);
	}

}
