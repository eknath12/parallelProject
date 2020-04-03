package com.capgemini.librarymanagementsystem.service;

import com.capgemini.librarymanagementsystem.dao.StudentDAO;
import com.capgemini.librarymanagementsystem.dao.StudentDAOImplementation;
import com.capgemini.librarymanagementsystem.dto.BookPrimaryInfo;
import com.capgemini.librarymanagementsystem.dto.StudentPrimaryInfo;

public class StudentServiceImplementation implements StudentService {
	
	StudentDAO dao = new StudentDAOImplementation();

	public boolean registerStudent(StudentPrimaryInfo studentInfo) {
		return dao.registerStudent(studentInfo);
	}

	public StudentPrimaryInfo loginStudent(String studentUserName, String StudentPassword) {
		return dao.loginStudent(studentUserName, StudentPassword);
	}

	public BookPrimaryInfo requestBook(int requestBookId) {
		return dao.requestBook(requestBookId);
	}

	public BookPrimaryInfo searchBook(int searchBookId) {
		return dao.searchBook(searchBookId);
	}
	public StudentPrimaryInfo borrowBook( String studentMailId) {
		return dao.borrowedBooks(studentMailId);
	}
}
