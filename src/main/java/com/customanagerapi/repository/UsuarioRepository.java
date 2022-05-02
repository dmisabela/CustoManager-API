package com.customanagerapi.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.customanagerapi.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Usuario findById(long id);

	Page<Usuario> findAll(Pageable pageable);	
	
	Optional<Usuario> findByLogin(String login);	
	
	Boolean existsByLogin(String login);
	
	Boolean existsByCpf(String cpf);
	
	
}	
