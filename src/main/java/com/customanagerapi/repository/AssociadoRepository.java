package com.customanagerapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.customanagerapi.domain.entity.Associado;

public interface AssociadoRepository extends JpaRepository<Associado, Long>,
JpaSpecificationExecutor<Associado> {


}	
