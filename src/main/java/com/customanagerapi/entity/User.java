package com.customanagerapi.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.br.CPF;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USUARIOS")
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
		
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;
	
	@Column(name = "nome", length = 100)
	@NotEmpty(message = "{campo.obrigatorio.nome}")
	private String nome;
	
	@Column(name = "cpf", length = 14)
	@NotEmpty(message = "{campo.obrigatorio.cpf}")
	@CPF(message = "{campo.valido.cpf}")
	private String cpf;
		
	@Column(name = "email", length = 100)
	@NotEmpty(message = "{campo.obrigatorio.email}")
	private String email;	
	
	@Column(name = "telefone", length = 15)
	@NotEmpty(message = "{campo.obrigatorio.telefone}")
	private String telefone;
	
	@Column(name = "endereco", length = 200)
	@NotEmpty(message = "{campo.obrigatorio.endereco}")
	private String endereco;	
	
	@Column(name = "senha", length = 100)
	@NotEmpty(message = "{campo.obrigatorio.senha}")
	private String senha;
	
	@Column (name = "admin")
	private boolean admin;
	
	@Column (name = "funcionario")
	private boolean funcionario;
	
	@Column (name = "status")
	private boolean status;
	
	@Column (name = "acesso_sistema")
	private boolean acessoAoSistema;
	
}