package com.example.bookstore.controller;

import java.util.Arrays;
import java.util.Map;

import org.hibernate.usertype.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Role;
import com.example.bookstore.entity.User;
import com.example.bookstore.exception.OnlyAdminIsAuthorizedException;
import com.example.bookstore.exception.UserNotFoundException;
import com.example.bookstore.services.AuthenticationService;
import com.example.bookstore.services.BookService;
import com.example.bookstore.services.OrderService;
import com.example.bookstore.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class AdminPageController {


	@Autowired
	private BookService bookService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private AuthenticationService authService;
	
	@GetMapping("/admin/adminhomepage")
	public String adminhomepage() {
	    return "admin/adminhomepage";  
	}

	//add book page
	@GetMapping("/admin/addbook")
	public String showAddBookPage(HttpServletResponse resp,
			             HttpServletRequest req,Model model) throws Exception
	{
	   //authorization
		if(authService.identifyUserRole(resp, req) != Role.admin)
			throw new OnlyAdminIsAuthorizedException();
		
		model.addAttribute("book",new Book());
		return "admin/bookregister";
	}
	
	//admin login page
	@GetMapping("/admin/login")
	public String showAdminLoginPage(HttpServletResponse resp,
            HttpServletRequest req,Model model) throws Exception
	{
		   //authorization
			if(authService.identifyUserRole(resp, req) != Role.admin)
				throw new OnlyAdminIsAuthorizedException();
			
			
	    model.addAttribute("bookList",bookService.getAllBook());
		return "admin/adminhomepage";
	}
	
	//book dashboard
	@GetMapping("/admin/viewbook")
	public String showViewBookPage(HttpServletResponse resp,
	            HttpServletRequest req, Model model) throws Exception
	{
	    if(authService.identifyUserRole(resp, req) != Role.admin)
	        throw new OnlyAdminIsAuthorizedException();

	    model.addAttribute("bookList", bookService.getAllBook());
	    model.addAttribute("updatedBook", new Book());
	    return "admin/viewbook";  
	}

	//update book menu
	@GetMapping("/admin/updatebook")
	public String showUpdateBookPage(HttpServletResponse resp,
            HttpServletRequest req) throws Exception
	{
		   //authorization
			if(authService.identifyUserRole(resp, req) != Role.admin)
				throw new OnlyAdminIsAuthorizedException();
		
		return "admin/updatebook";
	}

	//view all foods order
	@GetMapping("/admin/bookorder")
	public String showBookOrder(HttpServletResponse resp,
            HttpServletRequest req,Model model) throws Exception
	{
		//authorization
		if(authService.identifyUserRole(resp, req) != Role.admin)
			throw new OnlyAdminIsAuthorizedException();
		model.addAttribute("orders",orderService.getOrderList());
		model.addAttribute("statusType",Arrays.asList("pending", "ready", "delivered", "cancelled"));
		return "/admin/bookorder";
	}
	
	//update order status
	
	@PostMapping("/admin/updateOrderStatus")
	public String updateOrderStatus(@RequestParam("orderId")String orderId, 
	                                 @RequestParam Map<String, String> allParams, 
	                                 Model model) {
	    try {
	    	
	        System.out.println("All parameters: " + allParams);

	        // Get the specific status using the orderId
	        String statusKey = "status_" +orderId;
	        String newStatus = allParams.get(statusKey);
    		System.out.println("Ready to Update orderstatus_"+newStatus);


	        if (newStatus != null) {
	            // Update order status
	            orderService.updateOrderStatus(Integer.parseInt(orderId), newStatus);
	    		System.out.println("Update orderstatus "+newStatus);

	        }

	        return "redirect:/admin/bookorder";
	    } catch (Exception e) {
	        model.addAttribute("error", "Failed to update order status.");
	        System.out.println("Failed to update status ");
	        return "/admin/bookorder";
	    }
	}
	
	//view admin profile
	@GetMapping("/admin/adminprofile")
	public String showAdminProfile(HttpServletResponse resp,
	            HttpServletRequest req, Model model) throws Exception {
	    if (authService.identifyUserRole(resp, req) != Role.admin) {
	        throw new OnlyAdminIsAuthorizedException();
	    }
	    
	    User user = userService.getByCookieId(resp, req);
	    if (user == null) {
	        throw new UserNotFoundException(); // Ensure this is properly handled
	    }

	    model.addAttribute("user", user);
	    return "admin/adminprofile";
	}

	
}
