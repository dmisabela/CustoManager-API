package com.customanagerapi.domain.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.br.CNPJ;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "VINCULO_ASSOCIADOS_EMPRESAS")
public class VinculoAssociadoEmpresa implements Serializable {
	
	private static final long serialVersionUID = 1L;
		
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "id_empresa")
	Empresa empresa;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name = "id_associado")
	Associado associado;	
	
	@ManyToOne
	@JoinColumn(name = "id_usuario_funcionario")
	Usuario usuarioFuncionario;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario_criador")
	Usuario usuarioCriador;
		
	@Column (name = "data_criacao")
	private LocalDateTime dataCriacao;	
	
	@Column (name = "status")
	private Boolean status;

	
	
}