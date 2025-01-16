
package com.example.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookstore.entity.User;

public interface UserRepository extends JpaRepository<User,Integer>{
	
	User findByUsername(String username);
    User findByPhone(String Phone);
    User findByCookieId(String coookieId);

}
