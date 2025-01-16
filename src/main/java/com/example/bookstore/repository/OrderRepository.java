
package com.example.bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookstore.entity.Order;

public interface OrderRepository extends JpaRepository<Order,Integer>{

	Order findOrderById(int id);
	
    List<Order> findAllByOrderByCreatedAtDesc();

}
