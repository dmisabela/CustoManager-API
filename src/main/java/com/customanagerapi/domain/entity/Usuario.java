package com.customanagerapi.domain.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.br.CPF;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "USUARIOS")
public class Usuario implements Serializable {
	
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
	
	@Column(name = "data_nascimento", length = 10)
	@NotNull(message = "{campo.obrigatorio.data_nasc}")
	@Past(message = "{campo.valido.data_nasc}")
	private LocalDate dataNascimento;	
		
	@Column(name = "email", length = 100)
	@Email(message = "{campo.valido.email}")
	@NotEmpty(message = "{campo.obrigatorio.email}")
	private String login;	
	
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
	
	@Column (name = "data_criacao")
	private LocalDateTime dataCriacao;

	@Lob
	@Column(name = "foto_perfil")
	private byte[] fotoPerfil;
	
}