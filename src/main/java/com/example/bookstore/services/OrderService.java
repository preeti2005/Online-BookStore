
package com.example.bookstore.services;

import com.example.bookstore.entity.Cart;
import com.example.bookstore.entity.CartItem;
import com.example.bookstore.entity.Order;
import com.example.bookstore.entity.StatusType;
import com.example.bookstore.entity.User;
import com.example.bookstore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService;

    public void placeOrder(User user) {
        // Get the user's cart
        Cart cart = cartService.getCartByUser(user);
        List<CartItem> cartItems = cart.getItems();  // Assuming Cart has a list of items
        
        for (CartItem cartItem : cartItems) {
            Order order = new Order();
            order.setBook(cartItem.getBook());
            order.setQuantity(cartItem.getQuantity());
            order.setAmount(cartItem.getBook().getPrice() * cartItem.getQuantity());
            order.setUser(user);
            order.setOrderStatus(StatusType.pending); // Assuming a default status
            
            System.out.println(order);
            // Save the order to the database
            orderRepository.save(order);
        }

        // Clear the cart after order is placed
        cartService.clearCart(user); // Assuming you have this method to clear the user's cart
    }

	public void save(Order order) {
		orderRepository.save(order);
		// TODO Auto-generated method stub
		
	}
	
	public List<Order> getOrderList()
	{
		return orderRepository.findAllByOrderByCreatedAtDesc();
	}
	
	public List<Order> getOrderListByUser(User user)
	{
		List<Order> allOrderList = this.getOrderList();
		List<Order> userOrderList = new ArrayList();
		
		for(Order order:allOrderList)
		{
			if(order.getUser() == user)
				userOrderList.add(order);
		}
		return userOrderList;
	}
	
	
	public Order findById(int id)
	{
		return orderRepository.findOrderById(id);
	}
	
	//update order status
	public void updateOrderStatus(int orderId,String status)
	{
		
		Order order =orderRepository.findOrderById(orderId);
		order.setOrderStatus(StatusType.valueOf(status.toLowerCase()));
		orderRepository.save(order);
	}
}
