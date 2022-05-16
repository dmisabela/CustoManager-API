package com.customanagerapi.rest.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.customanagerapi.domain.entity.MarcaProduto;
import com.customanagerapi.service.MarcaProdutoService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/marca-produto")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MarcaProdutoController {
	
	private final MarcaProdutoService marcaService;
	
	
	@PostMapping("/cadastrar-marca")
	@ApiOperation("Cadastro de marca de produtos")
	@ResponseStatus(HttpStatus.CREATED)
	public MarcaProduto insertMarcaProduto(@RequestBody @Valid MarcaProduto marcaProduto) throws Exception {		
		return marcaService.salvar(marcaProduto);
	}
	
	@GetMapping("/get-marcas-produto-by-empresa-id")
	@ApiOperation("Obter as marcas de uma empresa específica")
	public Page<MarcaProduto> getMarcasByEmpresaId(
			Long idEmpresa,
			String orderBy, 
			Boolean orderAsc,
			Integer pageNumber, 
			Integer pageSize) throws Exception {		
		
		return marcaService.getMarcasByEmpresaId(idEmpresa, orderBy, orderAsc, pageNumber, pageSize);
	}
	
	@PutMapping("/update")
	@ApiOperation("Alterar dados da marca")
	public MarcaProduto updateMarca(@RequestBody @Valid MarcaProduto marcaProduto) throws Exception {
		return marcaService.update(marcaProduto);
	}
	
}
