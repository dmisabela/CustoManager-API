package com.customanagerapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.customanagerapi.domain.entity.Empresa;
import com.customanagerapi.domain.entity.MarcaProduto;

public interface MarcaProdutoRepository extends JpaRepository<MarcaProduto, Long>,
JpaSpecificationExecutor<MarcaProduto> {

	Page<MarcaProduto> findByEmpresa(Empresa empresa, Pageable pageable);


}	
