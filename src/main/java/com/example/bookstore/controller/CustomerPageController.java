package com.example.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.bookstore.entity.Role;
import com.example.bookstore.entity.User;
import com.example.bookstore.exception.OnlyCustomerIsAuthorizedException;
import com.example.bookstore.exception.UserNotFoundException;
import com.example.bookstore.services.AuthenticationService;
import com.example.bookstore.services.BookService;
import com.example.bookstore.services.CartService;
import com.example.bookstore.services.OrderService;
import com.example.bookstore.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class CustomerPageController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationService authService;

	@Autowired
	private BookService bookService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private CartService cartService;

	@GetMapping("/customer/home")
	public String getCustomerHomepage(Model model, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// Layer 1 Validation for role authorization63
		if (authService.identifyUserRole(resp, req) != Role.customer)
			throw new OnlyCustomerIsAuthorizedException();

		model.addAttribute("bookList", bookService.getAllBook());
		return "customer/customerhomepage";
	}

	@GetMapping("/customer/viewbook")
	public String getCustomerViewBookPage(HttpServletRequest req, HttpServletResponse resp, Model model)
			throws Exception {
		// Layer 1 Validation for role authorization
		if (authService.identifyUserRole(resp, req) != Role.customer)
			throw new OnlyCustomerIsAuthorizedException();

		model.addAttribute("bookList", bookService.getAllBook());
		return "customer/viewbook";
	}

	@GetMapping("/customer/cart")
	public String getCustomerCartPage(Model model, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// Layer 1 Validation for role authorization
		if (authService.identifyUserRole(resp, req) != Role.customer)
			throw new OnlyCustomerIsAuthorizedException();

		User user = userService.getByCookieId(resp, req);
		if (user == null)
			throw new UserNotFoundException();
		model.addAttribute("cart", cartService.getCartByUser(user));
		return "customer/cart";
	}

	@GetMapping("/customer/profile")
	public String getCustomerProfilePage(HttpServletResponse resp, HttpServletRequest req, Model model)
			throws Exception {
		// Layer 1 Validation for role authorization
		if (authService.identifyUserRole(resp, req) != Role.customer)
			throw new OnlyCustomerIsAuthorizedException();

		User user = userService.getByCookieId(resp, req);
		if (user != null) {
			model.addAttribute("user", user);
			return "customer/customerprofile.html";
		} else
			throw new UserNotFoundException();
	}

	@GetMapping("/customer/orderConfirmation")
	public String getCustomerOrderConfirmationPage() {
		return "/customer/orderconfirmation.html";
	}

//view all book order
	@GetMapping("/customer/bookorder")
	public String showBookOrder(HttpServletResponse resp, HttpServletRequest req, Model model) throws Exception {
		// authorization
		if (authService.identifyUserRole(resp, req) != Role.customer)
			throw new OnlyCustomerIsAuthorizedException();

		User user = userService.getByCookieId(resp, req);
		model.addAttribute("orders", orderService.getOrderListByUser(user));
		return "/customer/bookorder.html";
	}
}
