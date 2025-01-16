
package com.example.bookstore.exception;

//failed to create new user
public class UserFailedToCreateException extends CustomUserException{

	
	public UserFailedToCreateException(String msg)
	{
	super("Failed to create new user "+msg);
	}
}
