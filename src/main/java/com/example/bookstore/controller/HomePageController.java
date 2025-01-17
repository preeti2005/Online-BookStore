package com.example.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.bookstore.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.example.bookstore.entity.Role;
import com.example.bookstore.entity.User;
import com.example.bookstore.services.AuthenticationService;
import com.example.bookstore.services.BookService;

@Controller
public class HomePageController {

	@Autowired
	private AuthenticationService authService;
	
	@Autowired 
	private UserService userService;
	
	@Autowired
	private BookService bookService;
	
	//return homepage
		@GetMapping("/")
		public String getHomePage(Model model)
		{
			model.addAttribute("BookList", bookService.getAllBook());

			return "/homepage.html"; 
		}
		
		//return view all Book 
		@GetMapping("/viewBook")
		public String getViewBookPage(Model model)
		{
			model.addAttribute("bookList", bookService.getAllBook());
	        return "viewbook";
		}
		
		//get signup page
		@GetMapping("/signup")
		public String getSignupPage(Model model)
		{
			if(!model.containsAttribute("user"))
			model.addAttribute("user",new User());
			
			return "/signup.html";
		}
		
	    //get page not found 
		@GetMapping("/pageNotFound")
		public String showPageNOtFound()
		{
			return "/pageNOtFound";
		}
	
	//signup new user
	@PostMapping("/signup/user")
	public String signupUser(@ModelAttribute User user,
		@RequestParam(name="userImage")MultipartFile file,RedirectAttributes redirect)
	{
		
		if((userService.isUsernameExists(user.getUsername()) == true)    //check if user details already exists
			|| (userService.isEmailExists(user.getEmail()) == true)	)
	{
		if((userService.isUsernameExists(user.getUsername()) == true))
			redirect.addFlashAttribute("usernameErr","Username already exists");
		if(userService.isEmailExists(user.getEmail()) == true)
			redirect.addFlashAttribute("emailErr","Email already exists");
		
		redirect.addFlashAttribute("user",user);
		return "redirect:/signup";  //return to the signup page 
	}
		else
		{
			User newUser= userService.addNewUser(user,file);    //add new user to the database
			if(newUser != null)
			{
				redirect.addFlashAttribute("addStatus","New User Created");
				return "redirect:/login";   //return to the login page
			}
			else
			{
				redirect.addFlashAttribute("addStatus","Failed to create new user");
				return "redirect:/signup";   //return to the sign up
			}
			
		}
	
}
	
	//User Login
	@GetMapping("/login")
	public String showUserLoginPage(HttpServletRequest req,HttpServletResponse resp,Model model)
	{
		
     User user = authService.loginByCookie(req, resp);
     if(user != null)
     {
    	 model.addAttribute("user",user);
    	 
    	 if(user.getRole() == Role.admin)
    	 return "/admin/adminhomepage";  //return to admin homepage
    	 if(user.getRole() == Role.customer)
    	 return "/customer/home"; //return to customer homepage
    		 
    	 return "/"; //return to  homepage
     }
     else
        return "login";      //return to the log in page
	}
	
	
	//validate login
	@PostMapping("login/user")
	public String validateLogin(@RequestParam(name="username") String username,
			                    @RequestParam(name="password") String password,
			                    HttpServletResponse resp,HttpServletRequest req,RedirectAttributes redirect)
	{

		User user = authService.authenticateUser(username, password, req, resp);

		if(user == null)
		{
			redirect.addFlashAttribute("loginStatus","Invalid credentials");
			return "redirect:/login"; //return to the login page
		}
		else
		{
			redirect.addFlashAttribute("user",user);
			if(user.getRole() == Role.admin)
			return "redirect:/admin/login"; //return to the admin homepage
			if(user.getRole() == Role.customer)
				return "redirect:/customer/home";
			else
				return "redirect:/home"; //return to the user page
		}
	}
	
	//logout user
	@GetMapping("logout/user")
	public String logoutUser(HttpServletResponse resp,HttpServletRequest req)
	{
		authService.logoutByCookie(req,resp);   
        return "redirect:/";		
	}
}
