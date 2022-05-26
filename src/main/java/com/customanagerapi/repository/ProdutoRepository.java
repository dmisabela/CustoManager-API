package com.customanagerapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.customanagerapi.domain.entity.Empresa;
import com.customanagerapi.domain.entity.Produto;
import com.customanagerapi.domain.utils.SearchSpecification;

public interface ProdutoRepository extends JpaRepository<Produto, Long>,
JpaSpecificationExecutor<Produto> {

	Page<Produto> findByEmpresa(Empresa empresa, Pageable pageable);
	
	Page<Produto> findByEmpresaAndIdContainingIgnoreCase(
			Empresa empresa, long id, Pageable pageable);
	
	Page<Produto> findByEmpresaAndNomeContainingIgnoreCase(
			Empresa empresa, String nome, Pageable pageable);
	
	Page<Produto> findByEmpresaAndValorUnitario(
			Empresa empresa, Double valorUnitario, Pageable pageable);
	
	Page<Produto> findByEmpresaAndAtivo(
			Empresa empresa, Boolean ativo, Pageable pageable);
	
	Page<Produto> findByEmpresaAndTipoProduto_NomeContainingIgnoreCase(
			Empresa empresa, String nome, Pageable pageable);
	
	Page<Produto> findByEmpresaAndMarcaProduto_NomeContainingIgnoreCase(
			Empresa empresa, String nome, Pageable pageable);

	Page<Produto> findAllByEmpresa(Empresa emp, SearchSpecification<Produto> specification, Pageable pageable);

}	

