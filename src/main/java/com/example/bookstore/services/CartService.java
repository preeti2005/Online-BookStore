
package com.example.bookstore.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstore.entity.Cart;
import com.example.bookstore.entity.CartItem;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.User;
import com.example.bookstore.repository.CartItemRepository;
import com.example.bookstore.repository.CartRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private CartItemRepository cartItemRepository;

    public void addItemToCart(User user, int bookId, int quantity) {
        // Retrieve the cart for the user, or create a new one if not found
        Cart cart = cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });

        // Find the Book item by ID
        Book book = bookService.getBook(bookId);

        // Check if the Book is already in the cart
        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getBook().getId() == bookId)
                .findFirst()
                .orElse(null);

        if (existingItem == null) {
            // Create a new CartItem if it doesn't exist
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setBook(book);
            newItem.setQuantity(quantity);
            cart.getItems().add(newItem);
            cartItemRepository.save(newItem);
        } else {
            // Update the quantity if the item already exists
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            cartItemRepository.save(existingItem);
        }

        // Save the updated cart
        cartRepository.save(cart);
    }
    
    
    public Cart getCartByUser(User user) {
    	 // Retrieve the cart for the user, or create a new one if not found
        Cart cart = cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });
        return cart;
    }

 // Decrease quantity of an item
    public void decreaseItemQuantity(User user, int itemId) {
        Cart cart = getCartByUser(user);
        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getId() == itemId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));
        
        if (cartItem.getQuantity() > 1) {
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            cartItemRepository.save(cartItem);
        }
    }

    // Increase quantity of an item
    public void increaseItemQuantity(User user, int itemId) {
        Cart cart = getCartByUser(user);
        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getId() == itemId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));

        cartItem.setQuantity(cartItem.getQuantity() + 1);
        cartItemRepository.save(cartItem);
    }
    
    //clear whole cart 
    public void clearCart(User user) {
        Cart cart = cartRepository.findCartByUser(user);
        cart.getItems().clear();  // Clear the items from the cart
        cartRepository.save(cart); // Save the empty cart to the database
    }


    public void removeItem(int itemId) {
        cartItemRepository.deleteById(itemId);
    }
    
    public CartItem getItemById(int itemId)
    {
    	return cartItemRepository.findById(itemId);
    }
}
