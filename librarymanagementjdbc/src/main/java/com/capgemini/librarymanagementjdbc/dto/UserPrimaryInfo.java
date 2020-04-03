package com.capgemini.librarymanagementjdbc.dto;


import lombok.Data;

@Data
public class UserPrimaryInfo {
	
	private int id;
	private String name;
	private String emailId;
	private String password;
	private long mobileNo;
	private String typeOfUser;
	
	
}
