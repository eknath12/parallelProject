package com.capgemini.librarymanagementjdbc.factory;

import com.capgemini.librarymanagementjdbc.dao.UserDAO;
import com.capgemini.librarymanagementjdbc.dao.UserDAOImplementation;
import com.capgemini.librarymanagementjdbc.service.UserService;
import com.capgemini.librarymanagementjdbc.service.UserServiceImplementation;

public class UserFactory {
	public static  UserDAO getUserDAO() {
		return new UserDAOImplementation();
	}
	public static UserService getUserService() {
		return new UserServiceImplementation();
	}
	
}
