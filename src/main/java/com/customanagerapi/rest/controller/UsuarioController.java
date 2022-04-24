package com.customanagerapi.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.customanagerapi.entity.Usuario;
import com.customanagerapi.exception.UsuarioOuSenhaInvalidaException;
import com.customanagerapi.rest.dto.CredenciaisDTO;
import com.customanagerapi.rest.dto.TokenDTO;
import com.customanagerapi.security.jwt.JwtService;
import com.customanagerapi.service.impl.UsuarioServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:8080", "https://customanager.netlify.app"})
public class UsuarioController {
	
	private final UsuarioServiceImpl usuarioService;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	
	
	@PostMapping("/login")
	public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais) {		
		try {	
			Usuario usuario = Usuario.builder()
					.login(credenciais.getLogin())
					.senha(credenciais.getSenha())
					.build();			
						
			UserDetails usuarioAutenticado = usuarioService.autenticar(usuario);	
			
			String token = jwtService.gerarToken(usuario);				
			return new TokenDTO(usuarioAutenticado.getUsername(), token);			
		}		
		catch (UsuarioOuSenhaInvalidaException e) {			
			throw new UsuarioOuSenhaInvalidaException();
		}
	}
	
	
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public Usuario insertUser(@RequestBody @Valid Usuario usuario) throws Exception {
		String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
		usuario.setSenha(senhaCriptografada);			
		return usuarioService.salvar(usuario);
	}
	
	@GetMapping("/get-all")
	public List<Usuario> getAllUsers() {
		return usuarioService.getAllUsers();
	}
	
	@GetMapping("/get-by-id/{id}") 
	public Usuario getUserById(@PathVariable("id") long id) {
		return usuarioService.getUserById(id);
	}
	
	
	@PutMapping("/update")
	public Usuario updateUser(@RequestBody @Valid Usuario usuario) throws Exception {
		String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
		usuario.setSenha(senhaCriptografada);	
		return usuarioService.update(usuario);
	}
	
		
//	@DeleteMapping("/delete/{id}")
//	public Usuario deleteUser(@PathVariable("id") long id) {
//		return usuarioService.delete(id);
//	}
	
}
