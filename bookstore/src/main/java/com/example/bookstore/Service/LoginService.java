package com.example.bookstore.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstore.Entity.Book;
import com.example.bookstore.Entity.Login;
import com.example.bookstore.Repositry.LogRepo;

@Service
public class LoginService {
	
	@Autowired
	private LogRepo logRepo;
	
    public void saveMyBook(Login log) {
		
		logRepo.save(log);
	}
    
    

}
