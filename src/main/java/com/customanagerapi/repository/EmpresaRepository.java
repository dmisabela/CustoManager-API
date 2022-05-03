package com.customanagerapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.customanagerapi.domain.entity.Empresa;
import com.customanagerapi.domain.entity.Usuario;

public interface EmpresaRepository extends JpaRepository<Empresa, Long>,
										   JpaSpecificationExecutor<Usuario> {
	
	List<Empresa> findByUsuario(Usuario usuario);
	
	Boolean existsByCnpj(String cnpj);
	
		
	
	
	
}	
