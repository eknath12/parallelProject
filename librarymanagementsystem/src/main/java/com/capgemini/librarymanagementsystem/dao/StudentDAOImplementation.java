package com.capgemini.librarymanagementsystem.dao;

import com.capgemini.librarymanagementsystem.database.DataBase;
import com.capgemini.librarymanagementsystem.dto.BookPrimaryInfo;
import com.capgemini.librarymanagementsystem.dto.StudentPrimaryInfo;
import com.capgemini.librarymanagementsystem.exception.BookException;
import com.capgemini.librarymanagementsystem.exception.StudentException;

public class StudentDAOImplementation implements StudentDAO {

	StudentPrimaryInfo studentBean = new StudentPrimaryInfo();

	public boolean registerStudent(StudentPrimaryInfo studentInfo) {
		int size = DataBase.STUDENT_DB.size();
		if (size == 0) {
			DataBase.STUDENT_DB.put(studentInfo.getMailId(), studentInfo);
			return true;
		} else {
			if (studentInfo.getMailId() != null) {
				boolean isAdminExists = DataBase.STUDENT_DB.containsKey(studentInfo.getMailId());
				if (isAdminExists == false) {
					DataBase.STUDENT_DB.put(studentInfo.getMailId(), studentInfo);
					return true;
				} else {
					throw new StudentException("Student mail Id Already Exits");
				}
			} else {
				throw new StudentException("Student mail Id can't be Empty");
			}
		}

	}

	public StudentPrimaryInfo loginStudent(String studentMailId, String studentPassword) {
		boolean isExists = DataBase.STUDENT_DB.containsKey(studentMailId);
		if (isExists) {
			System.out.println(isExists);
			StudentPrimaryInfo dbStudentInfo = DataBase.STUDENT_DB.get(studentMailId);

			if (dbStudentInfo.getMailId().equals(studentMailId)
					&& dbStudentInfo.getPassword().equals(studentPassword)) {

				return dbStudentInfo;
			} else {

				throw new StudentException("Invalid Credentials");
			}
		} else {
			throw new StudentException("Mail Id Not Exists");
		}

	}

	public BookPrimaryInfo requestBook(int requestBookId) {
//		boolean isExists = DataBase.BOOK_DB.containsKey(requestBookId);
//		if (isExists) {
//			return DataBase.BOOK_DB.get(requestBookId);
//		} else {
//			throw new BookException("Book is Not Avaliable in library");
//		}
		return null;
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

	public StudentPrimaryInfo borrowedBooks(String studentEmailId) {
		boolean isStudentExist = DataBase.BOOK_DB.containsKey(studentEmailId);
		if(isStudentExist) {
			StudentPrimaryInfo studentInfo = DataBase.STUDENT_DB.get(studentEmailId);
			return studentInfo;
		}else {
			throw new StudentException("Student Data Not Available");
		}

}
}
