
package com.example.bookstore.exception;

public class OnlyCustomerIsAuthorizedException extends Exception{
	
	public OnlyCustomerIsAuthorizedException()
	{
		super("This Page is only allowed to customer");
	}

}
