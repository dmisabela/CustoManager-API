package com.customanagerapi.domain.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "MOVIMENTACOES_PRODUTOS")
@EqualsAndHashCode(exclude = "produto")
public class MovimentacaoProduto implements Serializable {
	
	private static final long serialVersionUID = 1L;
		
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	protected long id;		
	
	@Column(name = "quantidade")
	private Integer quantidade;
	
	@Column(name = "valor_unitario")
	@NotNull(message = "{campo.obrigatorio.valor}")
	private Double valorUnitario;	
	
	@ManyToOne
	@JoinColumn(name = "id_movimentacao", nullable = false)
	@JsonIgnore
	private Movimentacao movimentacao;
	
	@ManyToOne
	@JoinColumn(name = "id_produto", nullable = false)
	@JsonIgnore
	private Produto produto;
	
}