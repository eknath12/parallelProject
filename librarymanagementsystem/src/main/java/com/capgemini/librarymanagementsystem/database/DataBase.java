	package com.capgemini.librarymanagementsystem.database;
	
	import java.util.HashMap;
	import java.util.Map;
	
	import com.capgemini.librarymanagementsystem.dto.AdminPrimaryInfo;
	import com.capgemini.librarymanagementsystem.dto.BookPrimaryInfo;
	import com.capgemini.librarymanagementsystem.dto.StudentPrimaryInfo;
	
	public class DataBase {
		public static final Map<String, AdminPrimaryInfo> ADMIN_DB = new HashMap<String, AdminPrimaryInfo>();
		public static final Map<String, StudentPrimaryInfo> STUDENT_DB = new HashMap<String, StudentPrimaryInfo>();
		public static final Map<Integer, BookPrimaryInfo> BOOK_DB = new HashMap<Integer, BookPrimaryInfo>();
	
//		public boolean setAdminInfo(AdminPrimaryInfo a) {
//			ADMIN_DB.put(a.getMailId(), a);
//			if(ADMIN_DB.containsKey(a.getMailId())) {
//				return true;
//			}else {
//				return false;
//			}
//		}
//	
//		public  AdminPrimaryInfo getAdminInfo(String mailId) {
//			if (ADMIN_DB.containsKey(mailId)) {
//				AdminPrimaryInfo adminInfo = ADMIN_DB.get(mailId);
//				return adminInfo;
//			} else {
//				return null;
//			}
//	
//		}
//	
//		public void setStudentInfo(StudentPrimaryInfo s) {
//			STUDENT_DB.put(s.getId(), s);
//		}
//	
//		public StudentPrimaryInfo getStudentInfo(int id) {
//			if (STUDENT_DB.containsKey(id)) {
//				StudentPrimaryInfo studentInfo = STUDENT_DB.get(id);
//				return studentInfo;
//			} else {
//				return null;
//			}
//		}
//	
//		public void setBookInfo(BookPrimaryInfo b) {
//			BOOK_DB.put(b.getBookId(), b);
//		}
//	
//		public BookPrimaryInfo getBookInfo(int id) {
//			if (BOOK_DB.containsKey(id)) {
//				BookPrimaryInfo bookInfo = BOOK_DB.get(id);
//				return bookInfo;
//			} else {
//				return null;
//			}
//		}
	
	}
