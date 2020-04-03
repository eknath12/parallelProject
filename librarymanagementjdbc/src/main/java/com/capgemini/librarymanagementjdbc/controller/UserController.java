package com.capgemini.librarymanagementjdbc.controller;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.capgemini.librarymanagementjdbc.dto.BookIssueInfo;
import com.capgemini.librarymanagementjdbc.dto.BookPrimaryInfo;
import com.capgemini.librarymanagementjdbc.dto.UserPrimaryInfo;
import com.capgemini.librarymanagementjdbc.exception.UserException;
import com.capgemini.librarymanagementjdbc.factory.UserFactory;
import com.capgemini.librarymanagementjdbc.service.UserService;
import com.capgemini.librarymanagementjdbc.service.Validation;

public class UserController {

	private static final Validation VALIDATION = new Validation();
	private static final UserService USERSERVICE = UserFactory.getUserService();
	public static Scanner scanner = new Scanner(System.in);

	public static int inputChoice() {
		// taking a input in the form String after validation it flag variable becomes
		// true and
		// converting a String to the integer
		// we can also take a input in the form of Integer but when i am entering in
		// value integer it will give exact output
		// when i am giving a input like a,b,c(any characters) bascially it will throw a
		// InputMismatchException but in this we are catching that exception
		// and it will execute the alternative code and then control will go to the
		// while block that charcters will be assigned to the gloabally or local
		// decalred varable
		// and again it will doesn't ask the input again it will thorw the same
		// exception everytime .In console alternative code(catch inputmismatchExcep)
		// will exceute everytime
		// because of that i am taking the input as String after validating the input it
		// will convert it into integer and return to the caller.
		String input = null;
		boolean flag = false;
		int choice = 0;
		do {
			try {
				input = scanner.next();
				VALIDATION.validateChoice(input);
				flag = true;
				choice = Integer.parseInt(input);
			} catch (InputMismatchException e) {
				flag = false;
				System.err.println("Choice should contails only one digit(0-9) ");
			} catch (UserException e) {
				flag = false;
				System.err.println(e.getMessage());
			}
		} while (!flag);
		return choice;
	}

	public static int inputId() {
		// Same as inputChoice();
		String input = null;
		boolean flag = false;
		int userId = 0;
		do {
			try {
				input = scanner.next();
				VALIDATION.validateId(input);
				flag = true;
				userId = Integer.parseInt(input);
			} catch (InputMismatchException e) {
				flag = false;
				System.err.println("Id Should contains only digits(0-9)");
			} catch (UserException e) {
				flag = false;
				System.err.println(e.getMessage());
			}
		} while (!flag);

		return userId;
	}

	public static String inputName() {
		// in this we didn't need to convert it into int because return type is String
		String userName = null;
		boolean flag = false;
		do {
			try {
				userName = scanner.next();
				VALIDATION.validateName(userName);
				flag = true;
			} catch (InputMismatchException e) {
				flag = false;
				System.err.println("Name should contains only Alphabates(A-Z and a-z)");
			} catch (UserException e) {
				flag = false;
				System.err.println(e.getMessage());
			}
		} while (!flag);
		return userName.toLowerCase();

	}

	public static String inputEmailId() {
		String userEmailId = null;
		boolean flag = false;
		do {
			try {
				userEmailId = scanner.next();
				VALIDATION.validateEmailId(userEmailId);
				flag = true;
			} catch (InputMismatchException e) {
				flag = false;
				System.err.println("Email should be proper ");
			} catch (UserException e) {
				flag = false;
				System.err.println(e.getMessage());
			}
		} while (!flag);
		// in this i am converting userEmailId into lowerCase(small letters) alphabets
		return userEmailId.toLowerCase();
	}

	public static String inputPassword() {
		String userPassword = null;
		boolean flag = false;
		do {
			try {
				userPassword = scanner.next();
				VALIDATION.validatePassword(userPassword);
				flag = true;
			} catch (InputMismatchException e) {
				flag = false;
				System.err.println("Enter correct Password ");
			} catch (UserException e) {
				flag = false;
				System.err.println(e.getMessage());
			}
		} while (!flag);
		return userPassword.trim();
	}

	public static long inputMobileNo() {
		// as same as inputChoice()
		// but in this i am chaning the String into Long value
		String input = null;
		long userMobileNo = 0;
		boolean flag = false;
		do {
			try {
				input = scanner.next();
				VALIDATION.validateMobile(input);
				userMobileNo = Long.parseLong(input);
				flag = true;
			} catch (InputMismatchException e) {
				flag = false;
				System.err.println("Mobile Number should contains only numbers");
			} catch (UserException e) {
				flag = false;
				System.err.println(e.getMessage());
			}
		} while (!flag);
		return userMobileNo;

	}

	public static String inputBookDetails() {
		String bookDetails = null;
		boolean flag = false;
		do {
			try {
				bookDetails = scanner.next();
				VALIDATION.validateName(bookDetails);
				flag = true;
			} catch (InputMismatchException e) {
				flag = false;
				System.err.println("It should contains only Alphabates(A-Z and a-z)");
			} catch (UserException e) {
				flag = false;
				System.err.println(e.getMessage());
			}
		} while (!flag);
		return bookDetails.toUpperCase();
	}

	public static String inputBookCategory() {
		boolean flag = false;
		String bookCategory = null;
		do {
			try {
				bookCategory = scanner.next();
				VALIDATION.validateBookCategory(bookCategory);
				flag = true;
			} catch (InputMismatchException e) {
				flag = false;
				System.err.println("Enter only alphabets ");
			} catch (UserException e) {
				flag = false;
				System.err.println(e.getMessage());
			}
		} while (!flag);
		return bookCategory.toUpperCase().trim();
	}

	public static int inputNoOfBooks() {
		String input = null;
		int noOfBooks = 0;
		boolean flag = false;
		do {
			try {
				input = scanner.next();
				VALIDATION.validateNoOfBooks(input);
				noOfBooks = Integer.parseInt(input);
				flag = true;
			} catch (InputMismatchException e) {
				flag = false;
				System.err.println("Enter only alphabets ");
			} catch (UserException e) {
				flag = false;
				System.err.println(e.getMessage());
			}
		} while (!flag);
		return noOfBooks;
	}

	public static void main(String[] args) {
		performingOperations();
	}

	public static void performingOperations() {

		System.out.println("Welcome To Library Managemnt System");
		boolean bool = false;
		int choice = 0;
		do {
			boolean isLoggedIn = false;
			System.out.println("\nTo Perform any Operation . Please choose and \nEnter key ");
			System.out.println("To Perform Admin Operations Enter 1 ");
			System.out.println("To Perform Student Operations Enter 2");
			System.out.println("To Terminate Opertions Enter 3");

			System.out.println("Enter Choice");
			// it will go to the public static int inputChoice() and it will return a int
			// value
			choice = inputChoice();

			switch (choice) {
			case 1:
				isLoggedIn = loginInfo("ADMIN");
				if (isLoggedIn) {
					System.out.println("Now You Can Perform Any Operations");
					adminOperations();
				} else {
					System.out.println("Please go to login Page");

				}

				break;

			case 2:
				isLoggedIn = loginInfo("STUDENT");
				if (isLoggedIn) {
					System.out.println("Now You Can Perform Any  Operations");
					studentOperations();
				} else {
					System.out.println("Please go to login Page");

				}
				break;

			case 3:
				exit();

				break;

			default:
				System.out.println("Please Enter corrct Key");
				bool = true;
				break;
			}
		} while (bool);

	}

	public static boolean loginInfo(String user) {

		boolean isLoggedIn = false;
		do {

			System.out.println("Press 1 to " + user + " Register");
			System.out.println("Press 2 to " + user + " Login");
			System.out.println("Press 3 to Go to Users Page");
			System.out.println("Press 4 to Exit");
			System.out.println("Please choose your Choice");
			int choice = inputChoice();

			switch (choice) {

			case 1:
				// start of user Reg

				System.out.println("Welcome to " + user + " Page\n");
				// "\n" means control will goes to the next lint and it will not print on the
				// console
				// beacuse i want extra one line gap between welocme(sopln statment) and
				// (adminID(sopln stmt)
				// it is a Escape Sequence
				System.out.println("Enter " + user + " ID");
				int userId = inputId();

				System.out.println("Enter " + user + " Name");
				String userName = inputName();

				System.out.println("Enter " + user + " EmailID");
				String userEmailId = inputEmailId();

				System.out.println("Enter " + user + " Passoword");
				String userPassword = inputPassword();

				System.out.println("Enter " + user + " Mobile Number");
				long userMobileNo = inputMobileNo();

				try {
					UserPrimaryInfo userInfo = new UserPrimaryInfo();
					userInfo.setId(userId);
					userInfo.setName(userName);
					userInfo.setEmailId(userEmailId);
					userInfo.setPassword(userPassword);
					userInfo.setMobileNo(userMobileNo);
					if (user.equals("ADMIN")) {
						userInfo.setTypeOfUser("ADMIN");
					} else {
						userInfo.setTypeOfUser("STUDENT");
					}
					boolean isRegistered = USERSERVICE.registerUser(userInfo, userInfo.getTypeOfUser());
					if (isRegistered) {
						System.out.println(user + " is successfully Registered Go to Login\n");
					} else {
						System.out.println(user + " is Not Registered");
					}
				} catch (Exception e) {
					System.err.println("SOME THING WENT WRONG");
				}

				// End Of  Registration
				break;
			case 2:
				System.out.println("Welcome to Login Page\n");

				System.out.println("Enter "+user+" EmailId");
				String userLoginEmailId = inputEmailId();

				System.out.println("Enter "+user+" Password");
				String userLoginPassword = inputPassword();
				
				UserPrimaryInfo userInfo = USERSERVICE.loginUser(userLoginEmailId, userLoginPassword, user);
				if (userInfo != null) {
					// "\t" also know as Escape Sequence and it will give one tab space gap in
					// console
					isLoggedIn = true;
					System.out.println("Id------->" + userInfo.getId());
					System.out.println("Name----->" + userInfo.getName());
					System.out.println("EmailId-->" + userInfo.getEmailId());
					System.out.println("MobileNo->" + userInfo.getMobileNo());

				} else {
					System.err.println("Error Occuered");
				}

				// End Of Login 
				break;

			case 3:
				System.out.println("Going Back To the Users Page");
				performingOperations();
				break;
			case 4:
				exit();
				performingOperations();
				break;
			default:
				System.out.println("Please Choose Correct Choice/n");
				loginInfo(user);
				break;
			}

		} while (!isLoggedIn);

		return isLoggedIn;
	}

	public static void adminOperations() {
		boolean isLoggedOut = false;
		try (Scanner scanner = new Scanner(System.in)) {
			do {

				System.out.println("Welcome To Admin Operations\n\n");
				System.out.println("Press 1 to  Update Password Page");
				System.out.println("Press 2 to Add Books Page");
				System.out.println("Press 3 to Issue  a book from Library Page");
				System.out.println("Press 4 to remove a book to Student Page");
				System.out.println("Press 5 to return a book to Library ");
				System.out.println("Press 6 to search a book from Library");
				System.out.println("Press 7 to Logout from ");
				System.out.println("-----------------------------------");

				int key = inputChoice();
				switch (key) {
				case 1:
					// Start of  Update Password
					System.out.println("Welcome to Update Password Page\n");

					System.out.println("Enter  Mail Id");
					String updateUserEmailId = inputEmailId();

					System.out.println("Enter  Old Password");
					String oldUserPassword = inputPassword();

					System.out.println("Enter  New Password");
					String newUserPssword = inputPassword();

					boolean isUpdated = USERSERVICE.updateDetails(updateUserEmailId, oldUserPassword, newUserPssword,
							"ADMIN");
					if (isUpdated) {
						System.out.println(" Password Successfulyy Updated");
					} else {
						System.out.println("Password Not Updated");
					}
					// End of Updated Password
					break;
				case 2:
					// Start of Add Books
					System.out.println("Welcome To add Books Page\n");

					System.out.println("Enter Book Name");
					String bookName = inputBookDetails();

					System.out.println("Enter Book Author Name");
					String bookAuthorName = inputBookDetails();

					System.out.println("Enter Book Publisher Name");
					String bookPublisherName = inputBookDetails();

					System.out.println("Enter Book Category");
					String bookCategory = inputBookCategory();

					System.out.println("Enter Number Of Books");
					int noOfBooks = inputNoOfBooks();

					BookPrimaryInfo bookInfo = new BookPrimaryInfo();
					bookInfo.setBookName(bookName);
					bookInfo.setBookAuthor(bookAuthorName);
					bookInfo.setBookPublisherName(bookPublisherName);
					bookInfo.setBookCategory(bookCategory);
					bookInfo.setNoOfBooks(noOfBooks);

					boolean isBookAdded = USERSERVICE.addBook(bookInfo);

					if (isBookAdded) {
						System.out.println("Books Added in to library");
					} else {
						System.out.println("Book Not Added into Library");
					}

					// End of Adding Books
					break;
				case 3:
					// Start of Issue books by admin
					System.out.println("Welcome to IssueBooks Page\n");

					System.out.println("Enter Book Requested Id");
					int bookId = inputNoOfBooks();

					System.out.println("Enter Requested Student Email ID");
					String reqEmailId = inputEmailId();

					boolean isIssued = USERSERVICE.issueBook(bookId, reqEmailId, "STUDENT");

					if (isIssued) {
						System.out.println("Book Issued To Student Mail id" + reqEmailId);
					} else {
						System.out.println("Book Not Issued to Studend");
					}
					// End of IssueBooks by 
				case 4:
					// Startt of Remove Book
					System.out.println("Welcome to Remove Book Page\n");

					System.out.println("Enter Book Id To Remove Book from Library");
					int removeBookId = inputNoOfBooks();
					boolean isRemoved = USERSERVICE.removeBook(removeBookId);
					if (isRemoved) {
						System.out.println("Book Is Removed From Library");
					} else {
						System.out.println("Not Removed From Library");
					}
					break;
				// end of Remove books
				case 5:
					// Start of return books
					System.out.println("Welcome to Return Books Page\n");

					System.out.println("Enter Book Id ");
					int returnBookId = inputNoOfBooks();

					System.out.println("Enter Student EmailId");
					String returnEmailId = inputEmailId();

					boolean isReturned = USERSERVICE.returnBook(returnBookId, returnEmailId);
					if (isReturned) {
						System.out.println("Book Successfuly returned to library by Student :" + returnEmailId);
					} else {
						System.out.println("NOt deleted");
					}
					break;
				// End of return books
				case 6:
					// Search Book by Id
					System.out.println("Welcome to Search Book By ID Page");
					int searchBookId = inputNoOfBooks();
					BookPrimaryInfo searchbookInfo = USERSERVICE.searchBook(searchBookId);
					if (searchbookInfo != null) {
						System.out.println("bookId----------------->" + searchbookInfo.getBookId());
						System.out.println("bookCategoryUniqueId--->" + searchbookInfo.getBookUniqueId());
						System.out.println("bookName--------------->" + searchbookInfo.getBookName());
						System.out.println("bookAuthorName--------->" + searchbookInfo.getBookAuthor());
						System.out.println("bookPublisherName------>" + searchbookInfo.getBookCategory());
						String bookStatus = searchbookInfo.getBookStatus();
						if (bookStatus.equals("YES")) {
							System.out.println("bookStatus------------>Availble");
						} else {
							System.out.println("bookStatus------------->Not Availnle");
						}
					} else {
						System.out.println("MayBe BookID Not Correct");
					}
					break;
				case 7:
					isLoggedOut = true;
					System.out.println("Succesfully Logged Out From  Operations");
					performingOperations();
					break;
				default:
					System.out.println("Please Enter Valid Key");
					break;
				}

			} while (isLoggedOut == false);
		}
	}// End Of  Operations

	public static void studentOperations() {
		System.out.println("\nWelcome to Student Operations");
		boolean isLoggedOut = true;

		do {
			int choice = 0;
			System.out.println("Press 1 to Student Update Password");
			System.out.println("Press 2 to student Borrowed Books ");
			System.out.println("Press 3 to search books");
			System.out.println("Press 4 to Request book by BookId");
			System.out.println("Press 5 to logout\n");
			System.out.println("Enter Choice:");
			choice = inputChoice();

			switch (choice) {

			case 1:
				// Start of  Update Password
				System.out.println("Welcome to Update Password Page\n");

				System.out.println("Enter Student Mail Id");
				String updateUserEmailId = inputEmailId();

				System.out.println("Enter Student Old Password");
				String oldUserPassword = inputPassword();

				System.out.println("Enter Student New Password");
				String newUserPssword = inputPassword();

				boolean isUpdated = USERSERVICE.updateDetails(updateUserEmailId, oldUserPassword, newUserPssword,
						"STUDENT");
				if (isUpdated) {
					System.out.println("Student Password Successfulyy Updated");
				} else {
					System.out.println("Password Not Updated");
				}
				// End of Updated Password
				break;
			case 2:
				System.out.println("Welcome to Borrowed Books Page");
				int count = 0;
				System.out.println("Enter Your Email Id");
				String userEmailId = inputEmailId();
				float totalFine = 0.0f;
				List<BookIssueInfo> list = USERSERVICE.borrowedBooks(userEmailId);
				count = list.size();
				System.out.println("Number of Books Borrowed by Student " + count);
				for (BookIssueInfo info : list) {
					totalFine = info.getFine();
					System.out.println("Book ID------------------>" + info.getIssuedBookId());
					System.out.println("Book Name---------------->" + info.getIssuedBookName());
					System.out.println("Book Author Name--------->" + info.getIssuedBookAuthor());
					System.out.println("Book Publisher Name------>" + info.getIssuedBookPublisherName());
					System.out.println("Book Category------------>" + info.getIssuedBookCategory());
					System.out.println("Book Issued Date--------->" + info.getIssuedDate());
					System.out.println("Book Actual Return Date" + info.getActualReturnDate());
					System.out.println("Book Fine---------------->"+info.getFine());
				}
					System.out.println("Total Fine--------------->"+totalFine);
				if (count <= 3) {
					System.out.println("Student Not Eligible to take Anotjer Book");
				} else {
					System.out.println("Student Eligible to take " + count);
				}
				break;

			case 3:
				System.out.println("Welcome to Search Book By ID Page\n");

				int searchBookId = inputNoOfBooks();

				BookPrimaryInfo bookInfo = USERSERVICE.searchBook(searchBookId);
				if (bookInfo != null) {
					System.out.println("bookId----------------->" + bookInfo.getBookId());
					System.out.println("bookCategoryUniqueId--->" + bookInfo.getBookUniqueId());
					System.out.println("bookName--------------->" + bookInfo.getBookName());
					System.out.println("bookAuthorName--------->" + bookInfo.getBookAuthor());
					System.out.println("bookPublisherName------>" + bookInfo.getBookCategory());
					String bookStatus = bookInfo.getBookStatus();
					if (bookStatus.equals("YES")) {
						System.out.println("bookStatus------------>Availble");
					} else {
						System.out.println("bookStatus------------->Not Availnle");
					}
				} else {
					System.out.println("MayBe BookID Not Correct");
				}
				break;
			case 4:
				System.out.println("Welcome to Request Book Page");

				System.out.println("Enter Requested Book Id");
				int reqBookId = inputNoOfBooks();

				BookPrimaryInfo reqBookInfo = USERSERVICE.requestBook(reqBookId);
				if (reqBookInfo != null) {
					System.out.println("bookId----------------->" + reqBookInfo.getBookId());
					System.out.println("bookCategoryUniqueId--->" + reqBookInfo.getBookUniqueId());
					System.out.println("bookName--------------->" + reqBookInfo.getBookName());
					System.out.println("bookAuthorName--------->" + reqBookInfo.getBookAuthor());
					System.out.println("bookPublisherName------>" + reqBookInfo.getBookCategory());
					String bookStatus = reqBookInfo.getBookStatus();
					if (bookStatus.equals("YES")) {
						System.out.println("bookStatus------------>Availble");
					} else {
						System.out.println("bookStatus------------->Not Availnle");
					}
				} else {
					System.out.println("MayBe BookID Not Correct");
				}
				break;

			case 5:
				isLoggedOut = true;
				System.out.println("Succesfully Logged Out From  Operations");
				performingOperations();
				break;
			default:
				System.out.println("Please Correct Choice");
				break;
			}

		} while (isLoggedOut == true);
	}

	public static void exit() {
		System.out.println("Thank You ");
	}
}
