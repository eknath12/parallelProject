package com.capgemini.librarymanagementjdbc.dao;

import java.util.List;

import com.capgemini.librarymanagementjdbc.dto.BookIssueInfo;
import com.capgemini.librarymanagementjdbc.dto.BookPrimaryInfo;
import com.capgemini.librarymanagementjdbc.dto.UserPrimaryInfo;

public interface UserDAO {

	//1
	boolean registerUser(UserPrimaryInfo user, String typeOfUser);
	
	//2
	UserPrimaryInfo loginUser(String mailId, String userPassword,String typeOfUser);

	//3
	boolean updateDetails(String mailId, String password, String newPassword,String typeOfUser);

	//4
	boolean addBook(BookPrimaryInfo bookInfo);

	//5
	boolean removeBook(int bookId);

	//6
	boolean issueBook(int borrowBookId, String studentMailId, String typeOfUser);

	//7
	boolean returnBook(int returnBookId, String studentMailId);

	//8
	BookPrimaryInfo searchBook(int searchBookId);
	
	//9
	BookPrimaryInfo requestBook(int requestBookId);
	
	//10
	List<BookIssueInfo> borrowedBooks(String studentMailId);
}
