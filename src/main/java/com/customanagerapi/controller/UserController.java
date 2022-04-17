package com.customanagerapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customanagerapi.entity.User;
import com.customanagerapi.repository.UserRepository;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = {"http://localhost:8080", "https://customanager.netlify.app"})
public class UserController {

	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/get-all")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	@GetMapping("/get-by-id/{id}") 
	public User getUserById(@PathVariable("id") long id) {
		return userRepository.findById(id);
	}
	
	@PostMapping("/register")
	public User insertUser(@RequestBody @Valid User user) {
		return userRepository.save(user);
	}
	
	@PutMapping("/update")
	public User updateUser(@RequestBody User user) {
		return userRepository.save(user);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteUser(@PathVariable("id") long id) {
		userRepository.deleteById(id);
	}
}
