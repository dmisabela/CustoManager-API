package com.customanagerapi.exception;

public class UsuarioOuSenhaInvalidaException extends RuntimeException {
	
	public UsuarioOuSenhaInvalidaException() {
		super("Usuário e/ou senha inválidos");
	}

}
