
package com.example.bookstore.exception;

public class OnlyAdminIsAuthorizedException extends Exception{

	public OnlyAdminIsAuthorizedException()
	{
		super("Access Denied !! This Page is Only for admin !!");
	}
}
