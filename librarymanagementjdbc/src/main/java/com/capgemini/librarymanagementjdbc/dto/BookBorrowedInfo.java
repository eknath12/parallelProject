package com.capgemini.librarymanagementjdbc.dto;

import java.util.Date;

import lombok.Data;

@Data 

public class BookBorrowedInfo {

	private int borrowedBookId;
	private String borrowedMailId;
	private Date issuedDate;
	
}
