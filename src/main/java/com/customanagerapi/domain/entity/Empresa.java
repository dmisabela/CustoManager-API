package com.customanagerapi.domain.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.br.CNPJ;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "EMPRESAS")
public class Empresa implements Serializable {
	
	private static final long serialVersionUID = 1L;
		
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;
	
	@Column(name = "razao_social", length = 100)
	@NotEmpty(message = "{campo.obrigatorio.razao_social}")
	private String nome;
	
	@Column(name = "cnpj", length = 18)
	@NotEmpty(message = "{campo.obrigatorio.cnpj}")
	@CNPJ(message = "{campo.valido.cnpj}")
	private String cnpj;
	
	@Column(name = "telefone", length = 15)
	@NotEmpty(message = "{campo.obrigatorio.telefone}")
	private String telefone;
	
	@Column(name = "endereco", length = 200)
	@NotEmpty(message = "{campo.obrigatorio.endereco}")
	private String endereco;	
		
	@Column (name = "data_criacao")
	private LocalDateTime dataCriacao;	

	@ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = Usuario.class)
	@JoinColumn(name = "id_usuario_criador", nullable = false)
	@JsonBackReference
	private Usuario usuario;
	
	@OneToMany(mappedBy = "empresa")
    Set<VinculoAssociadoEmpresa> vinculos;
	
}