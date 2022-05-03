package com.customanagerapi.rest.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.customanagerapi.domain.entity.Empresa;
import com.customanagerapi.domain.entity.Usuario;
import com.customanagerapi.service.EmpresaService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/empresas")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:8080", "https://customanager.netlify.app"})
public class EmpresaController {
	
	private final EmpresaService empresaService;
	
	
	@PostMapping("/register")
	@ApiOperation("Cadastro de empresa")
	@ResponseStatus(HttpStatus.CREATED)
	public Empresa insertEmpresa(@RequestBody @Valid Empresa empresa) throws Exception {		
		return empresaService.salvar(empresa);
	}
	
	@GetMapping("/get-all")
	@ApiOperation("Obter todas as empresas")
	public Page<Empresa> getAllEmpresas(
			String orderBy, 
			Integer pageNumber, 
			Integer pageSize) {		
		
		return empresaService.getAllEmpresas(orderBy, pageNumber, pageSize);
	}
	
	@GetMapping("/get-by-id/{id}") 
	@ApiOperation("Obter detalhes de uma empresa pelo ID")
	public Optional<Empresa> getEmpresaById(@PathVariable("id") long id) {
		return empresaService.getEmpresaById(id);
	}
	
	
	@GetMapping("/get-empresas-by-usuario")
	@ApiOperation("Obter todas as empresas de um usuário específico")
	public List<Empresa> getEmpresaByUserId(Usuario usuario) {		
		
		return empresaService.getEmpresaByUserId(usuario);
	}
	

}
