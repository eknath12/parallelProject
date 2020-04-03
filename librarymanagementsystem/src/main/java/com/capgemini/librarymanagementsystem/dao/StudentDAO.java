package com.capgemini.librarymanagementsystem.dao;

import com.capgemini.librarymanagementsystem.dto.BookPrimaryInfo;
import com.capgemini.librarymanagementsystem.dto.StudentPrimaryInfo;

public interface StudentDAO {
	
	boolean registerStudent(StudentPrimaryInfo stuInfo);
	
	StudentPrimaryInfo loginStudent(String studentUserName,String StudentPassword);
	
	BookPrimaryInfo requestBook(int requestBookId);
	
	BookPrimaryInfo searchBook(int searchBookId);
	
	StudentPrimaryInfo borrowedBooks(String studentMailId);
	
	
}
