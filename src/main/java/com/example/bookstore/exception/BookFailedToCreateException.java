
package com.example.bookstore.exception;

public class BookFailedToCreateException extends RuntimeException{

	
	BookFailedToCreateException(String msg)
	{
	super("Book Failed To Create "+msg);
	}
}
