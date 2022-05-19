package com.customanagerapi.domain.entity;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "PRODUTOS")
public class Produto implements Serializable {
	
	private static final long serialVersionUID = 1L;
		
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	protected long id;
	
	@Column(name = "nome", length = 100)
	@NotEmpty(message = "{campo.obrigatorio.nome}")
	private String nome;
	
	@Column(name = "valor_unitario", length = 100)
	@NotNull(message = "{campo.obrigatorio.valor}")
	private Double valor_unitario;
	
	@Transient
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Long idEmpresa;
	
	@Transient
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Long idTipo;
	
	@Transient
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Long idMarca;
	
	@ManyToOne
	@JoinColumn(name = "id_tipo", nullable = false)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private TipoProduto tipoProduto;	
	
	@ManyToOne
	@JoinColumn(name = "id_marca", nullable = false)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private MarcaProduto marcaProduto;
	
	@ManyToOne
	@JoinColumn(name = "id_empresa", nullable = false)
	@JsonIgnore
	private Empresa empresa;	
	
	@OneToMany(mappedBy = "produto", fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	@JsonIgnore 
	    Set<MovimentacaoProduto> movimentacaoProdutos;
	
}