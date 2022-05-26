package com.customanagerapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.customanagerapi.domain.entity.Empresa;
import com.customanagerapi.domain.entity.Movimentacao;
import com.customanagerapi.domain.entity.Produto;
import com.customanagerapi.domain.utils.SearchSpecification;
import com.customanagerapi.enums.TipoMovimentacaoEnum;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long>,
JpaSpecificationExecutor<Movimentacao> {

	Page<Movimentacao> findByEmpresa(Empresa empresa, Pageable pageable);	
	
	
	Page<Movimentacao> findByEmpresaAndId(
			Empresa empresa, long id, Pageable pageable);
	
	Page<Movimentacao> findByEmpresaAndTipoMovimentacao(
			Empresa empresa, TipoMovimentacaoEnum tipo, Pageable pageable);
	
	Page<Movimentacao> findByEmpresaAndAssociado_NomeContainingIgnoreCase(
			Empresa empresa, String nome, Pageable pageable);
	
	Page<Movimentacao> findByEmpresaAndDescricaoContainingIgnoreCase(
			Empresa empresa, String nome, Pageable pageable);	
	
	Page<Movimentacao> findByEmpresaAndValorTotal(
			Empresa empresa, Double valor, Pageable pageable);

	Page<Movimentacao> findAllByEmpresa(Empresa emp, SearchSpecification<Movimentacao> specification, Pageable pageable);
	
}	