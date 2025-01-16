
package com.example.bookstore.exception;

//searched user not found 
public class UserNotFoundException extends CustomUserException{
	
	public UserNotFoundException()
	{
		super("No User Login Found !!"+" Login First!!!");
	}
}
