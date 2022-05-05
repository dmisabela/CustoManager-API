package com.customanagerapi.domain.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.br.CNPJ;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "EMPRESAS")
@EqualsAndHashCode(exclude = {"associado", "vinculos"})
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
	
	@Transient
	private Integer idUsuarioCriador;

	@ManyToOne
	@JoinColumn(name = "id_usuario_criador", nullable = false)
	@JsonIgnore
	private Usuario usuario;
	
	@OneToMany(mappedBy = "empresaTipoProd", fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	@JsonIgnore
    private Set<TipoProduto> tipoProduto;
	
	@OneToMany(mappedBy = "empresaMarcaProd", fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	@JsonIgnore
    private Set<MarcaProduto> marcaProduto;
	
	@OneToMany(mappedBy = "empresaProd", fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	@JsonIgnore
    private Set<Produto> produto;

	@OneToMany(mappedBy = "empresaAssociado", fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
    private Set<Associado> associado;

	@OneToMany(mappedBy = "empresaVinculo", fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	    Set<VinculoUsuarioEmpresa> vinculos;	
	
	
	
}