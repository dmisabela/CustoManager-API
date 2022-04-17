package com.customanagerapi.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.customanagerapi.entity.ApiErrors;

@RestControllerAdvice
public class ApplicationControllerAdvice {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ApiErrors handleMethodNotValidException ( MethodArgumentNotValidException ex ) {
		List<String> errors = ex.getBindingResult().getAllErrors()
		.stream()
		.map( erro -> erro.getDefaultMessage() )
		.collect(Collectors.toList());
		
		return new ApiErrors(errors);
		
	}

}
