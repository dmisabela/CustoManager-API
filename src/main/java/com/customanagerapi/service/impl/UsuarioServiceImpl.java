package com.customanagerapi.service.impl;


import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.customanagerapi.entity.Usuario;
import com.customanagerapi.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UserDetailsService {	
		
	@Autowired
	private UsuarioRepository repository;
	
	@Transactional
	public Usuario salvar(Usuario usuario) throws Exception {
		
		if(repository.existsByCpf(usuario.getCpf())) {
			throw new Exception("CPF já cadastrado");
		}
		
		if (repository.existsByLogin(usuario.getLogin())) {
			throw new Exception("E-mail já cadastrado");
		}
		
		return repository.save(usuario);
	}	
	
	@Transactional
	public List<Usuario> getAllUsers() {
		return repository.findAll();
	} 
	
	@Transactional
	public Usuario getUserById(long id) {
		return repository.findById(id);
	}
	
	
	@Transactional
	public Usuario update(Usuario usuario) {
		return repository.save(usuario);
	}
	
	//TODO: implementar delete
	
//	@Transactional
//	public Usuario delete(long id) { 
//		return repository.deleteUser(id);
//	}
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = repository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados."));
		
		String[] roles = usuario.isAdmin() ? new String[] {"ADMIN", "USER"} : new String[] {"USER"};
		
		return User
				.builder()
				.username(usuario.getLogin())
				.password(usuario.getSenha())
				.roles(roles)
				.build();
	}

}
