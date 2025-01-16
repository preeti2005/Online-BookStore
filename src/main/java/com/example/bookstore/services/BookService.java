
package com.example.bookstore.services;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.bookstore.entity.Book;
import com.example.bookstore.exception.*;
import com.example.bookstore.repository.BookRepository;

@Service
public class BookService {
	
	@Autowired
	private BookRepository bookRepository;
	
	
	//getting directory from application.properties
	@Value("${book.upload-dir}")
	private String uploadDirectory;
	
    //check if book already exists
	public boolean isBookExists(String bookName)
	{
		if (bookRepository.findByName(bookName) != null) 
		{
			System.out.println("Book Name "+bookName+"alreadyExists");
            return true;
		}
		else
			return false;
	}
	//add new Book to the database 
	public Book addNewBook(Book book,MultipartFile file)
	{
		if(isBookExists(book.getName()))
			return null;
		
		Book newBook = new Book();

		try {
			String imageName = this.generateBookImageId(file);
			book.setImageId(imageName);
			newBook = bookRepository.save(book);
		}
		catch(Exception ex)
		{
			System.out.println("Failed To Create New Book "+book.getName()+ex);
			return null;
		}
		return newBook;

		}
	
	//update Book already existing in databse
	public Book updateBook(Book book,MultipartFile file)
	{
		try {
		if(!file.isEmpty()) //for new image
		{
			String imageName = this.generateBookImageId(file);
			book.setImageId(imageName);
		}
		else     //for old image
		{
			Book oldBook = bookRepository.findById(book.getId());
			book.setImageId(oldBook.getImageId());
		}
		return bookRepository.save(book);
		}
		catch(Exception ex)
		{
			System.out.println("Failed to update Book "+book.toString()+" Exception:"+ex);
			return null;
		}
	}
	
	//delete existing Book
	public boolean deleteBook(int id)
	{
		try {
			 bookRepository.delete(bookRepository.findById(id));
			  return true;
		    }
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	
	//generate Book image new url
	public String generateBookImageId(MultipartFile file)
	{
		try {
			 // Ensure directory exists
            Path directoryPath = Paths.get(uploadDirectory);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);  // Create directory if not exists
            }
			
		String originalFileName = file.getOriginalFilename();
		//get unique file name
		String uniqueFileName = System.currentTimeMillis()+"_"+originalFileName;
		//set file name with  the default directory
		Path fileNameWithPath = Paths.get(uploadDirectory,uniqueFileName);
		//save file to the directory
        Files.write(fileNameWithPath, file.getBytes());
		return uniqueFileName;
		}
		catch(Exception ex)
		{
			System.out.println("Image File Not Uploaded ");
			ex.printStackTrace();
			return "";
		}
		
	}
	
	//View all Book from database 
	public List<Book> getAllBook()
	{
		try {
			return bookRepository.findAll();
		}
		catch(Exception ex)
		{
			System.out.println("No Book Rows Retrieved");
			return null;
		}
	}
	
	//get Book object by id
	public Book getBook(int id)
	{
		try {
		return bookRepository.findById(id);
		}
		catch(Exception ex)
		{
			System.out.println("No Book available with id"+id+" "+ex);
			return null;
		}
	}

			
	
}
