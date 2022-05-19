package com.customanagerapi.domain.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.customanagerapi.enums.TipoMovimentacaoEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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
@Table(name = "MOVIMENTACOES")
@EqualsAndHashCode(exclude = "movimentacaoProdutos")
public class Movimentacao implements Serializable {
	
	private static final long serialVersionUID = 1L;
		
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	protected long id;
		
	@Column(name = "valor_total", nullable = false)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Double valorTotal;	
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_movimentacao", length = 6)
	private TipoMovimentacaoEnum tipoMovimentacao;
	
	@Column (name = "data_movimentacao")
	private LocalDateTime dataMovimentacao;	
	
	@Transient
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Long idEmpresa;
	
	@Transient
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Long idAssociado;
		
	@ManyToOne
	@JoinColumn(name = "id_empresa", nullable = false)
	@JsonIgnore
	private Empresa empresa;
	
	@ManyToOne
	@JoinColumn(name = "id_associado", nullable = false)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Associado associado;
	
	@OneToMany(mappedBy = "movimentacao", cascade = CascadeType.ALL)	
	    List<MovimentacaoProduto> movimentacaoProdutos;
	
}