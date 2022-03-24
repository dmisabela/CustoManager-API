package com.customanagerapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customanagerapi.model.User;
import com.customanagerapi.repository.UserRepository;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = {"http://localhost:8080", "https://customanager.netlify.app"})
public class UserController {

	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/get-all-users")
	public List<User> getString() {
		return userRepository.findAll();
	}
	
	@PostMapping("/register")
	public User insertUser(@RequestBody User user) {
		return userRepository.save(user);
	}
}
