package com.customanagerapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.customanagerapi.domain.entity.Movimentacao;
import com.customanagerapi.domain.entity.MovimentacaoProduto;

public interface MovimentacaoProdutoRepository extends JpaRepository<MovimentacaoProduto, Long>,
JpaSpecificationExecutor<MovimentacaoProduto> {

	Page<MovimentacaoProduto> findByMovimentacao(Movimentacao movimentacao, Pageable pageable);
	
}	