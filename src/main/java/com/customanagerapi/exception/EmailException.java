package com.customanagerapi.exception;

public class EmailException extends RuntimeException {
	
	public EmailException() {
		super("Ocorreu um erro ao enviar o e-mail");
	}
}
