package com.customanagerapi.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
@CrossOrigin(origins = {"http://localhost:8081/", "https://customanager.netlify.app"})
public class HelloController {

	@GetMapping("")
	public String getString() {
		return "Olá mundo!";
	}
}