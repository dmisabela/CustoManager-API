package com.customanagerapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.customanagerapi.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Usuario findById(long id);

	List<Usuario> findAll();	
	
	Optional<Usuario> findByLogin(String login);	
	
	
}	
