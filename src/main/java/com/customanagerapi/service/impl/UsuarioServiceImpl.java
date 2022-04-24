package com.customanagerapi.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.customanagerapi.entity.Usuario;
import com.customanagerapi.exception.UsuarioOuSenhaInvalidaException;
import com.customanagerapi.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UserDetailsService {	
	
	@Autowired
	private PasswordEncoder encoder;

		
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
	
	
	public UserDetails autenticar(Usuario usuario) throws UsuarioOuSenhaInvalidaException {		
		
		UserDetails user = loadUserByUsername(usuario.getLogin());
		
		boolean senhaCorreta = encoder.matches(usuario.getSenha(), user.getPassword());
		
		if(senhaCorreta) {			
			Usuario verifyClaims = repository.findByLogin(usuario.getLogin())
									.orElseThrow(() -> new UsuarioOuSenhaInvalidaException());
			
			usuario.setAdmin(verifyClaims.isAdmin());
			usuario.setAcessoAoSistema(verifyClaims.isAcessoAoSistema());
			usuario.setExternal(verifyClaims.isExternal());
			usuario.setFuncionario(verifyClaims.isFuncionario());			
			
			return user;
		}
		
		throw new UsuarioOuSenhaInvalidaException();
		
	}	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsuarioOuSenhaInvalidaException {
		
		Usuario usuario = repository.findByLogin(username)
                .orElseThrow(() -> new UsuarioOuSenhaInvalidaException());
		
		String[] roles = usuario.isAdmin() ? new String[] {"ADMIN", "USER"} : new String[] {"USER"};
		
		return User
				.builder()
				.username(usuario.getLogin())
				.password(usuario.getSenha())
				.roles(roles)
				.build();
	}

}
