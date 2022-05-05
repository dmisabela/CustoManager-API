package com.customanagerapi.domain.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "VINCULO_USUARIOS_EMPRESAS")
public class VinculoUsuarioEmpresa implements Serializable {
	
	private static final long serialVersionUID = 1L;
		
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;
	
	@Transient
	private Long idEmpresaVinculo;
	
	@ManyToOne
	@JoinColumn(name = "id_empresa", nullable = false)
	@JsonIgnore
	Empresa empresaVinculo;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario_funcionario")
	Usuario usuarioFuncionario;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario_criador")
	Usuario usuarioCriador;
		
	@Column (name = "data_criacao")
	private LocalDateTime dataCriacao;	
	
	@Column (name = "admin_empresarial")
	private Boolean adminEmpresarial;	
	
	@Column (name = "status")
	private Boolean status;

	
	
}