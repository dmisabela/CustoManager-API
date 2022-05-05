package com.customanagerapi.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.customanagerapi.domain.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>,
										   JpaSpecificationExecutor<Usuario> {
	
	Usuario findById(long id);
	
	Usuario findByCpf(String cpf);
	
	Optional<Usuario> findByLogin(String login);	
	
	Boolean existsByLogin(String login);
	
	Boolean existsByCpf(String cpf);
	
	
}	
