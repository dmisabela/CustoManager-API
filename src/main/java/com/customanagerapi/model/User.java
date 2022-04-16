package com.customanagerapi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USUARIOS")
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
		
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;
	
	@Column(name = "nome", length = 100, nullable = false)
	private String nome;
	
	@Column(name = "cpf", length = 14, nullable = false)
	private String cpf;
	
	@Column(name = "email", length = 100, nullable = false)
	private String email;	
	
	@Column(name = "telefone", length = 15)
	private String telefone;
	
	@Column(name = "endereco", length = 200, nullable = false)
	private String endereco;	
	
	@Column(name = "senha", length = 100)
	private String senha;
	
	@Column (name = "admin")
	private boolean admin;
	
	@Column (name = "funcionario")
	private boolean funcionario;
	
	@Column (name = "status")
	private boolean status;
	
	@Column (name = "acesso_sistema")
	private boolean acessoAoSistema;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isFuncionario() {
		return funcionario;
	}

	public void setFuncionario(boolean funcionario) {
		this.funcionario = funcionario;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isAcessoAoSistema() {
		return acessoAoSistema;
	}

	public void setAcessoAoSistema(boolean acessoAoSistema) {
		this.acessoAoSistema = acessoAoSistema;
	}
	
	
	

	
}