package com.customanagerapi.rest.controller;

import java.util.List;
import java.util.stream.Collectors;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.customanagerapi.domain.utils.ApiErrors;
import com.customanagerapi.exception.EmailException;
import com.customanagerapi.exception.RegistroNaoEncontradoException;
import com.customanagerapi.exception.RegraNegocioException;
import com.customanagerapi.exception.UsuarioOuSenhaInvalidaException;

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
	
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UsuarioOuSenhaInvalidaException.class)
	public ApiErrors handleUsuarioOuSenhaInvalidaException (UsuarioOuSenhaInvalidaException ex) {
		String errors = ex.getMessage();		
		return new ApiErrors(errors);
	}	
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(RegraNegocioException.class)
	public ApiErrors handleUsuarioOuSenhaInvalidaException (RegraNegocioException ex) {
		String errors = ex.getMessage();		
		return new ApiErrors(errors);
	}	
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(RegistroNaoEncontradoException.class)
	public ApiErrors handleUsuarioOuSenhaInvalidaException (RegistroNaoEncontradoException ex) {
		String errors = ex.getMessage();		
		return new ApiErrors(errors);
	}
		
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(EmailException.class)
	public ApiErrors handleEmailException (EmailException ex) {
		String errors = ex.getMessage();		
		return new ApiErrors(errors);
	}

}
