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

import com.customanagerapi.domain.entity.Produto;
import com.customanagerapi.domain.utils.SearchRequest;
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
	
	
	@GetMapping("/get-produtos-by-empresa-id")
	@ApiOperation("Obter todos os produtos de uma empresa específica")
	public Page<Produto> getProdutosByEmpresaId(
			Long idEmpresa,
			String orderBy, 
			Boolean orderAsc,
			Integer pageNumber, 
			Integer pageSize) {		
		
		return produtoService.getProdutosByEmpresaId(idEmpresa, orderBy, orderAsc, pageNumber, pageSize);
	}
	
	@PutMapping("/update")
	@ApiOperation("Alterar dados do produto")
	public Produto updateProduto(@RequestBody @Valid Produto produto) throws Exception {
		return produtoService.update(produto);
	}
	
	@PostMapping("/search")
    @ApiOperation("Pesquisar produto(s) por filtros")
    public Page<Produto> search(@RequestBody SearchRequest request,
    					String orderBy, Boolean orderAsc, Integer pageNumber, Integer pageSize ) {
        return produtoService.searchProdutos(request, orderBy, orderAsc, pageNumber, pageSize);
    }
	
	
	

}
