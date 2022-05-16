package com.customanagerapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.customanagerapi.domain.entity.Empresa;
import com.customanagerapi.domain.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>,
JpaSpecificationExecutor<Produto> {

List<Produto> findByEmpresa(Empresa empresa);


}	

