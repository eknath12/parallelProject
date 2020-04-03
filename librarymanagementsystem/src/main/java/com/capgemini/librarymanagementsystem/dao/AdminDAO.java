package com.capgemini.librarymanagementsystem.dao;


import com.capgemini.librarymanagementsystem.dto.AdminPrimaryInfo;
import com.capgemini.librarymanagementsystem.dto.BookPrimaryInfo;

public interface AdminDAO {
	
	
	
	boolean registerAdmin(AdminPrimaryInfo admin);
	
	AdminPrimaryInfo loginAdmin(String mailId ,String userPassword);

	boolean updateDetails(String mailId,String password,String newPassword);
	
	boolean addBook(BookPrimaryInfo bookInfo);
	
	boolean removeBook(int  bookId);

	BookPrimaryInfo issueBook(int borrowBookId,String studentMailId);
	
	 boolean returnBook(int returnBookId,String studentMailId);

	BookPrimaryInfo searchBook(int searchBookId);
}
