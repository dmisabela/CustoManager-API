package com.customanagerapi.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.customanagerapi.domain.entity.Usuario;
import com.customanagerapi.domain.utils.SearchRequest;
import com.customanagerapi.domain.utils.SearchSpecification;
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
		
		String senhaCriptografada = encoder.encode(usuario.getSenha());
		usuario.setSenha(senhaCriptografada);	
		usuario.setDataCriacao(LocalDateTime.now());
	
		
		return repository.save(usuario);
	}	
	
	
    public List<Usuario> searchUsuarios(SearchRequest request) {
        SearchSpecification<Usuario> specification = new SearchSpecification<>(request);
        return repository.findAll(specification);
    }

	
	@Transactional
	public Page<Usuario> getAllUsers(
			String orderBy, 
			Integer pageNumber, 
			Integer pageSize) {	
		
		
		Sort sort = Sort.by(orderBy);
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		return repository.findAll(pageable);
	} 
	
	@Transactional
	public Usuario getUserById(long id) {
		return repository.findById(id);
	}
	
	
	@Transactional
	public Usuario update(Usuario usuario) {
		String senhaCriptografada = encoder.encode(usuario.getSenha());
		usuario.setSenha(senhaCriptografada);	
		return repository.save(usuario);
	}
	
	@Transactional
	public void delete(Long id) { 
		repository.deleteById(id);
	}
	
	
	public UserDetails autenticar(Usuario usuario) throws UsuarioOuSenhaInvalidaException {		
		
		UserDetails user = loadUserByUsername(usuario.getLogin());
		
		boolean senhaCorreta = encoder.matches(usuario.getSenha(), user.getPassword());
		
		
		if(senhaCorreta) {			
			Usuario verifyClaims = repository.findByLogin(usuario.getLogin())
									.orElseThrow(() -> new UsuarioOuSenhaInvalidaException());
			
			usuario.setId(verifyClaims.getId());
			usuario.setAdmin(verifyClaims.isAdmin());		
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
