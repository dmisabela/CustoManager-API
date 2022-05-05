package com.customanagerapi.domain.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.group.GroupSequenceProvider;

import com.customanagerapi.enums.TipoAssociadoEnum;
import com.customanagerapi.enums.TipoPessoaEnum;
import com.customanagerapi.interfaces.CnpjGroup;
import com.customanagerapi.interfaces.CpfGroup;
import com.customanagerapi.validators.AssociadoGroupSequenceProvider;
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
@Table(name = "ASSOCIADOS")
@GroupSequenceProvider(AssociadoGroupSequenceProvider.class)
public class Associado implements Serializable {
	
	private static final long serialVersionUID = 1L;
		
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	protected long id;
	
	//nome do usuario funcionario ou razao social da empresa cliente/fornecedor
	@Column(name = "nome", length = 100)
	@NotEmpty(message = "{campo.obrigatorio.nome}")
	private String nome;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_pessoa", length = 100)
	//@NotEmpty(message = "campo.obrigatorio.tipo_pessoa")
	private TipoPessoaEnum tipoPessoa;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_associado", length = 100)
	//@NotEmpty(message = "campo.obrigatorio.tipo_associado")
	private TipoAssociadoEnum tipoAssociado;
	
	@Column(name = "documento", length = 18)	
	@NotEmpty(message = "{campo.obrigatorio.documento}")
	@CPF(groups = CpfGroup.class)
	@CNPJ(groups = CnpjGroup.class)
	private String documento;
	
	@Column(name = "telefone", length = 15)
	private String telefone;
	
	@Column(name = "endereco", length = 200)
	private String endereco;	
	
	@Column(name = "observacoes", length = 200)
	private String observacoes;	
	
	@Column (name = "data_criacao")
	private LocalDateTime dataCriacao;
	
	@Transient
	private Long idEmpresa; 
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_empresa", nullable = false)
	@JsonIgnore
	private Empresa empresaAssociado;


	
}