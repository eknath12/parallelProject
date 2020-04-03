package com.capgemini.librarymanagementsystem.factory;

import com.capgemini.librarymanagementsystem.dao.StudentDAO;
import com.capgemini.librarymanagementsystem.dao.StudentDAOImplementation;
import com.capgemini.librarymanagementsystem.service.StudentService;
import com.capgemini.librarymanagementsystem.service.StudentServiceImplementation;

public class StudentFactory {
	public static  StudentDAO getStudentDAO() {
		return new StudentDAOImplementation();
	}
	public static StudentService getStudentService() {
		return	new StudentServiceImplementation();
	}
}
