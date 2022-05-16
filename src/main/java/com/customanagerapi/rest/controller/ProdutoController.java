package com.customanagerapi.rest.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.customanagerapi.domain.entity.MarcaProduto;
import com.customanagerapi.domain.entity.Produto;
import com.customanagerapi.domain.entity.TipoProduto;
import com.customanagerapi.service.ProdutoService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProdutoController {
	
	private final ProdutoService produtoService;
	
	@PostMapping("/cadastrar")
	@ApiOperation("Cadastro de produto")
	@ResponseStatus(HttpStatus.CREATED)
	public Produto insertProdutos(@RequestBody @Valid Produto produto) throws Exception {		
		return produtoService.salvar(produto);
	}
	
	
	@GetMapping("/get-all")
	@ApiOperation("Obter todos os produtos")
	public Page<Produto> getAllProdutos(
			String orderBy, 
			Boolean orderAsc,
			Integer pageNumber, 
			Integer pageSize) {		
		
		return produtoService.getAllProdutos(orderBy, orderAsc, pageNumber, pageSize);
	}
	
	

}
