package com.capgemini.librarymanagementsystem.service;

import com.capgemini.librarymanagementsystem.dto.BookPrimaryInfo;
import com.capgemini.librarymanagementsystem.dto.StudentPrimaryInfo;

public interface StudentService {
	
	boolean registerStudent(StudentPrimaryInfo stuInfo);
	
	StudentPrimaryInfo loginStudent(String studentUserName,String StudentPassword);
	
	BookPrimaryInfo requestBook(int requestBookId);
	
	BookPrimaryInfo searchBook(int searchBookId);
	
	 StudentPrimaryInfo borrowBook( String studentMailId);
	
}
