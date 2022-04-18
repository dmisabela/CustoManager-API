package com.customanagerapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
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

import com.customanagerapi.JwtService;
import com.customanagerapi.entity.Usuario;
import com.customanagerapi.service.impl.UsuarioServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:8080", "https://customanager.netlify.app"})
public class UsuarioController {
	
	private final UsuarioServiceImpl usuarioService;
	private final PasswordEncoder passwordEncoder;
	
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public Usuario insertUser(@RequestBody @Valid Usuario usuario) {
		String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
		usuario.setSenha(senhaCriptografada);
		
		//Teste de geração do token
		JwtService jwt = new JwtService();
		String token = jwt.gerarToken(usuario);		
		System.out.println(token);
		
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
	public Usuario updateUser(@RequestBody @Valid Usuario usuario) {
		return usuarioService.update(usuario);
	}
	
		
//	@DeleteMapping("/delete/{id}")
//	public Usuario deleteUser(@PathVariable("id") long id) {
//		return usuarioService.delete(id);
//	}
	
}
