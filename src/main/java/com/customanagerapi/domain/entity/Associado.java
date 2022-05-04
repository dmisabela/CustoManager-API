package com.customanagerapi.domain.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import com.customanagerapi.enums.TipoAssociadoEnum;
import com.customanagerapi.enums.TipoPessoaEnum;
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
	
	@Column(name = "tipo_pessoa", length = 100)
	//@NotEmpty(message = "campo.obrigatorio.tipo_pessoa")
	private TipoPessoaEnum tipoPessoa;
	
	@Column(name = "tipo_associado", length = 100)
	//@NotEmpty(message = "campo.obrigatorio.tipo_associado")
	private TipoAssociadoEnum tipoAssociado;
	
	@Column(name = "documento", length = 18)	
	@NotEmpty(message = "{campo.obrigatorio.documento}")
	private String documento;
	
	@Transient
	@CPF(message = "{campo.valido.cpf}")
	private String cpf;
	
	@Transient
	@CNPJ(message = "{campo.valido.cnpj}")
	private String cnpj;	
	
	@Column(name = "telefone", length = 15)
	private String telefone;
	
	@Column(name = "endereco", length = 200)
	private String endereco;	
	
	@Column(name = "observacoes", length = 200)
	private String observacoes;	
	
	@Column (name = "data_criacao")
	private LocalDateTime dataCriacao;
	
	@OneToMany(mappedBy = "associado")
    Set<VinculoAssociadoEmpresa> vinculos;


	
}