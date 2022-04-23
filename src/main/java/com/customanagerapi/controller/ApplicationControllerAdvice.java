package com.customanagerapi.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.customanagerapi.entity.ApiErrors;

@RestControllerAdvice
public class ApplicationControllerAdvice {
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ApiErrors handleMethodNotValidException ( MethodArgumentNotValidException ex ) {
		List<String> errors = ex.getBindingResult().getAllErrors()
		.stream()
		.map( erro -> erro.getDefaultMessage() )
		.collect(Collectors.toList());
		
		return new ApiErrors(errors);
		
	}
	
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(Exception.class)
	public ApiErrors handleExceptions (Exception ex) {
		String errors = ex.getMessage();		
		return new ApiErrors(errors);
	}
	
	
	

}
