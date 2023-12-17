package com.example.bookstore.Repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookstore.Entity.Login;


@Repository
public interface LogRepo extends JpaRepository<Login, Integer> {
	
	Login findByUserName(String userName);
	

}
