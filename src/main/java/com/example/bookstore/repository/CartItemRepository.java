
package com.example.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookstore.entity.CartItem;
import com.example.bookstore.entity.User;

public interface CartItemRepository extends JpaRepository<CartItem,Integer>{
	
	CartItem findById(int itemId);


}
