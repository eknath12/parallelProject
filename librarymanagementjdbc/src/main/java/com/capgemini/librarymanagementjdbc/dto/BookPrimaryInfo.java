package com.capgemini.librarymanagementjdbc.dto;


import lombok.Data;

@Data
public class BookPrimaryInfo {
	private int bookId;
	private String bookUniqueId;
	private String bookName;
	private String bookAuthor;
	private String bookPublisherName;
	private String bookCategory;
	private String bookStatus;
	private int noOfBooks;

}
