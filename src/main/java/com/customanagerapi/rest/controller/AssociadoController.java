package com.customanagerapi.rest.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
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

import com.customanagerapi.domain.entity.Associado;
import com.customanagerapi.service.AssociadoService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/associados")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AssociadoController {
	
	private final AssociadoService associadoService; 
	
	
	@PostMapping("/register")
	@ApiOperation("Cadastro de associados")
	@ResponseStatus(HttpStatus.CREATED)
	public Associado insertAssociado(@RequestBody @Valid Associado associado) throws Exception {		
		return associadoService.salvar(associado);
	}
	
	@GetMapping("/get-by-id/{id}") 
	@ApiOperation("Obter detalhes do associado pelo ID")
	public Optional<Associado> getAssociadoById(@PathVariable("id") long id) {
		return associadoService.getAssociadoById(id);
	}
	
	@GetMapping("/get-associados-by-empresa-id/{idEmpresa}")
	@ApiOperation("Obter todos os associados de uma empresa específica")
	public List<Associado> getAssociadosByEmpresaId(@PathVariable("idEmpresa") long idEmpresa) throws Exception {		
		
		return associadoService.getAssociadosByEmpresaId(idEmpresa);
	}
	
	@PutMapping("/update")
	@ApiOperation("Alterar associado")
	public Associado updateAssociado(@RequestBody @Valid Associado associado) throws Exception {
		return associadoService.update(associado);
	}
	
	@DeleteMapping("/delete/{id}")
	@ApiOperation("Excluir associado da empresa")
	public void deleteAssociado(@PathVariable("id") Long id) {
		associadoService.delete(id);
	}	

}
