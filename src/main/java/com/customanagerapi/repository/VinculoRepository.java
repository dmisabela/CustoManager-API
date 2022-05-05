package com.customanagerapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.customanagerapi.domain.entity.VinculoUsuarioEmpresa;

public interface VinculoRepository extends JpaRepository<VinculoUsuarioEmpresa, Long>,
JpaSpecificationExecutor<VinculoUsuarioEmpresa> {
	
	


}	

