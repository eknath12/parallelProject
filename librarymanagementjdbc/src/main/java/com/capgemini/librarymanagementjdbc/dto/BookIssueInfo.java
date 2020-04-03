package com.capgemini.librarymanagementjdbc.dto;

import java.util.Date;

import lombok.Data;

@Data
public class BookIssueInfo {

	private int issuedBookId;
	private String issuedBookUniqueId;
	private String issuedBookName;
	private String issuedBookAuthor;
	private String issuedBookPublisherName;
	private String issuedBookCategory;
	private int noOfBooksBorrowed;
	private int studentId;
	private String studentName;
	private int noOfDays;
	private Date issuedDate;
	private Date returnDate;
	private Date actualReturnDate;
	private String bookStatus;
	private float fine;
	
}
