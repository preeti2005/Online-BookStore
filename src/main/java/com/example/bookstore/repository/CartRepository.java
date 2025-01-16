
package com.example.bookstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookstore.entity.Cart;
import com.example.bookstore.entity.User;

public interface CartRepository extends JpaRepository<Cart,Integer>{
	
    Optional<Cart> findByUser(User user);
    Cart findCartByUser(User user);
    

}
