
package com.example.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookstore.entity.User;

public interface UserRepository extends JpaRepository<User,Integer>{
	
	User findByUsername(String username);
    User findByEmail(String Email);
    User findByCookieId(String coookieId);

}
