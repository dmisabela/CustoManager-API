package com.customanagerapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.customanagerapi.domain.entity.Associado;
import com.customanagerapi.domain.entity.Empresa;

public interface AssociadoRepository extends JpaRepository<Associado, Long>,
JpaSpecificationExecutor<Associado> {
	
	
	List<Associado> findByEmpresaAssociado(Empresa empresaAssociado);
}	
