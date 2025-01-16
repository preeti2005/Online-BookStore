package com.example.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.bookstore.entity.Book;
import com.example.bookstore.services.BookService;
import com.example.bookstore.services.OrderService;

@Controller
public class BookController {

	@Autowired
	private BookService bookService;

	@Autowired
	private OrderService orderService;

//add new book item to the database 
	@PostMapping("/admin/addbook/save")
	public String saveNewFood(@ModelAttribute Book book, @RequestParam(name = "bookImage") MultipartFile file,
			Model model) {

		Book newBook = bookService.addNewBook(book, file);
		if (newBook != null) {
			model.addAttribute("addStatus", "New Book Added Successfully");
			model.addAttribute("bookList", bookService.getAllBook());
			return "redirect:/admin/viewbook";
		} else {
			model.addAttribute("addStatus", "Failed To Add New Book");
			return "/admin/addbook";
		}
	}

//update book
	@GetMapping("/admin/updatebook/{id}")
	public String showUpdateFood(@PathVariable("id") String id, Model model) {
		int bookId = Integer.parseInt(id);
		Book book = bookService.getBook(bookId);
		if (book != null) {
			model.addAttribute("book", book);
			model.addAttribute("updatedBook", book);
			return "/admin/updatebook";
		} else
			return "redirect:/admin/viewbook"; // Thymeleaf template name
	}

//save updated book
	@PostMapping("/admin/updatebook/{id}/saved")
	public String saveUpdateBook(@PathVariable("id") String id, @ModelAttribute("updatedBook") Book updatedBook,
			@RequestParam(name = "bookImage") MultipartFile file, Model model) {
		// set food id
		updatedBook.setId(Integer.parseInt(id));
		// update in repository
		Book book = bookService.updateBook(updatedBook, file);
		if (book != null) {
			model.addAttribute("updateStatus", "Update Successfully");
			return "redirect:/admin/viewbook";
		} else {
			model.addAttribute("updateStatus", "Update Failed");
			return "redirect:/admin/update/{id}";
		}
	}

//delete existing Book
	@GetMapping("/admin/deletebook/{id}")
	public String deleteBook(@PathVariable("id") String id, Model model) {
		boolean isBookDeleted =bookService.deleteBook(Integer.parseInt(id));
		if (isBookDeleted) {
			model.addAttribute("deleteStatus", "Book is deleted");
			return "redirect:/admin/viewbook";
		} else {
			model.addAttribute("deleteStatus", "Book deletion failed");
			return "redirect:/admin/login";
		}
	}

}
