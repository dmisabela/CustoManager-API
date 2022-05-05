package com.customanagerapi.rest.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import com.customanagerapi.domain.entity.Usuario;
import com.customanagerapi.domain.utils.SearchRequest;
import com.customanagerapi.exception.RegistroNaoEncontradoException;
import com.customanagerapi.exception.UsuarioOuSenhaInvalidaException;
import com.customanagerapi.rest.dto.CredenciaisDTO;
import com.customanagerapi.rest.dto.TokenDTO;
import com.customanagerapi.security.jwt.JwtService;
import com.customanagerapi.service.UsuarioService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:8080", "https://customanager.netlify.app"})
public class UsuarioController {
	
	private final UsuarioService usuarioService;
	private final JwtService jwtService;
	
	
	@PostMapping("/login")
	@ApiOperation("Autenticação de usuário")
	public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais) {		
		try {	
			Usuario usuario = Usuario.builder()
					.login(credenciais.getLogin())
					.senha(credenciais.getSenha())
					.build();			
						
			UserDetails usuarioAutenticado = usuarioService.autenticar(usuario);	
			
			String token = "Bearer " + jwtService.gerarToken(usuario);				
			return new TokenDTO(usuarioAutenticado.getUsername(), token);			
		}		
		catch (UsuarioOuSenhaInvalidaException e) {			
			throw new UsuarioOuSenhaInvalidaException();
		}
	}
	
	
	@PostMapping("/register")
	@ApiOperation("Cadastro de usuário")
	@ResponseStatus(HttpStatus.CREATED)
	public Usuario insertUser(@RequestBody @Valid Usuario usuario) throws Exception {		
		return usuarioService.salvar(usuario);
	}
	
	@GetMapping("/get-all")
	@ApiOperation("Obter todos os usuários")
	public Page<Usuario> getAllUsers(
			String orderBy, 
			Boolean orderAsc,
			Integer pageNumber, 
			Integer pageSize) {		
		
		return usuarioService.getAllUsers(orderBy, orderAsc, pageNumber, pageSize);
	}
	
	
    @PostMapping("/search")
    @ApiOperation("Pesquisar usuário(s) por filtros")
    public Page<Usuario> search(@RequestBody SearchRequest request,
    					String orderBy, Boolean orderAsc, Integer pageNumber, Integer pageSize ) {
        return usuarioService.searchUsuarios(request, orderBy, orderAsc, pageNumber, pageSize);
    }

	
	@GetMapping("/get-by-id/{id}") 
	@ApiOperation("Obter detalhes de um usuário pelo ID")
	public Usuario getUserById(@PathVariable("id") long id) {
		return usuarioService.getUserById(id);
	}
	
	@GetMapping("/get-by-cpf")
	@ApiOperation("Obter um usuário por seu CPF")
	public Usuario getByCpf(String cpf) throws Exception {
		
		return usuarioService.getByCpf(cpf);		
		
	}
	
	
	@PutMapping("/update")
	@ApiOperation("Alterar usuário")
	public Usuario updateUser(@RequestBody @Valid Usuario usuario) throws Exception {
		return usuarioService.update(usuario);
	}
	
		
	@DeleteMapping("/delete/{id}")
	public void deleteUser(@PathVariable("id") Long id) {
		usuarioService.delete(id);
	}
	
	
}
