package com.capgemini.librarymanagementsystem.factory;

import com.capgemini.librarymanagementsystem.dao.AdminDAO;
import com.capgemini.librarymanagementsystem.dao.AdminDAOImplementation;
import com.capgemini.librarymanagementsystem.service.AdminService;
import com.capgemini.librarymanagementsystem.service.AdminServiceImplementation;

public class AdminFactory {
	public static  AdminDAO getAdminDAO() {
		return new AdminDAOImplementation();
	}
	public static AdminService getAdminService() {
		return	new AdminServiceImplementation();
	}
}
