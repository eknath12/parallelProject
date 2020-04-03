package com.capgemini.librarymanagementjdbc.service;

import java.util.regex.Pattern;

import com.capgemini.librarymanagementjdbc.exception.UserException;

public class Validation {

	public boolean validateChoice(String userId) throws UserException {
		String validating = "[0-9]{1}";
		boolean isValidated = Pattern.matches(validating, userId);
		if (isValidated) {
			return true;
		} else {
			throw new UserException("Choice should contain only digits");

		}
	}

	public boolean validateId(String input) throws UserException {
		// refer all these things in javatpoint
		// ==== \\d is also [1-9]
		// ==== && means intersection
		// ==== [^0] except zero
		// ==== it means it accepts first chracter should be 1 to 9 and it must have 5
		// characters inclusive of 0 to 9 letters
		// ==== Pattern is class present in the java.util.regex package
		// ==== we have to check weather the enterd id should be in the thses pattern or
		// not in order to that we have to use
		// we can match the pattern in three ways
		// the follwing way is one of those three ways
		// 1
//		String idRegx = "[\\d&&[^0]][\\d]{5}";
//		Pattern pat = Pattern.compile(idRegx);
//		Matcher match = pat.matcher(input);
//		boolean bool = match.matches();
		// way 2
		// another way is
//		String idRegX = "[\\d&&[^0]][\\d]{5}";
//		boolean b2=Pattern.compile(idRegX).matcher(input).matches();  		

		// way 3

		String idRegx = "[\\d&&[^0]][\\d]{5}";
		boolean isValidated = Pattern.matches(idRegx, String.valueOf(input));
		if (isValidated) {
			return true;
		} else {
			throw new UserException("Id should contain 6 digits and first letter should be non zero digit");
		}
	}

	public boolean validateName(String inputName) throws UserException {

		String nameRegx = "\\D{3,50}";

		boolean isValidated = Pattern.matches(nameRegx, inputName);
		if (isValidated) {
			return true;
		} else {
			throw new UserException("Name should contain atleast 3 characters and alphabets only");
		}
	}

	public boolean validateEmailId(String inputEmail) throws UserException {
		String emailRegx = "[\\w&&[^_]]{3,50}[@]{1}\\D{2,50}[.]{1}\\D{2,50}";
		boolean isValidated = Pattern.matches(emailRegx, inputEmail);
		if (isValidated) {
			return true;
		} else {
			throw new UserException("EmailId Should contain  @ and extensions(.com,.in,.org)");
		}
	}

	public boolean validateMobile(String inputMobile) throws UserException {
		String mobileRegx = "[6789]{1}[\\d]{9}";
		boolean isValidated = Pattern.matches(mobileRegx, inputMobile);
		if (isValidated) {
			return true;
		} else {
			throw new UserException(
					"Mobile number should start with 6 or 7 or 8 or 9 and must contains 10 digit number");
		}
	}

	public boolean validatePassword(String inputPassword) throws UserException {
		String passwordRegx = "[\\w&&[^_]]{3,50}";
		boolean isValidated = Pattern.matches(passwordRegx, inputPassword);
		if (isValidated) {
			return true;
		} else {
			throw new UserException("password should contains alphabets and atleast 5 words and atmost 10");
		}
	}

	public boolean validateBookDetails(String bookDetails) throws UserException {
		String bookRegx = "[\\w&&[0-9]&&[^_]]{3,50}";
		boolean isValidated = Pattern.matches(bookRegx, bookDetails);
		if (isValidated) {
			return true;
		} else {
			throw new UserException("Book Details should be alphabets and digits only ");
		}
	}

	public boolean validateBookCategory(String bookCategory) throws UserException {
		String bookRegx = "[A-Z]{3}";
		boolean isValidated = Pattern.matches(bookRegx, bookCategory);
		if (isValidated) {
			return true;
		} else {
			throw new UserException("Book Category Should be 3 Letters and It Should be Capital letters");

		}
	}

	public boolean validateNoOfBooks(String noOfBooks) throws UserException {
		String noOfBooksRegx = "[0-9]{1,3}";
		boolean isValidated = Pattern.matches(noOfBooksRegx, noOfBooks);
		if (isValidated)
			return true;
		else
			throw new UserException("Enter Only Numbers");
	}

}
