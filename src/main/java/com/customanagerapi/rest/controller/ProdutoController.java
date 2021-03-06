package com.customanagerapi.rest.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.customanagerapi.domain.entity.Produto;
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
			Integer pageSize) throws Exception {		
		
		return produtoService.getProdutosByEmpresaId(idEmpresa, orderBy, orderAsc, pageNumber, pageSize);
	}
	
	@GetMapping("/get-by-id/{id}") 
	@ApiOperation("Obter detalhes de um produto pelo ID")
	public Optional<Produto> getProdutoById(@PathVariable("id") long id) {
		return produtoService.getProdutoById(id);
	}
	
	@PutMapping("/update")
	@ApiOperation("Alterar dados do produto")
	public Produto updateProduto(@RequestBody @Valid Produto produto) throws Exception {
		return produtoService.update(produto);
	}
	
	@GetMapping("/search")
	@ApiOperation("Pesquisar produto(s) por filtros")
    public Page<Produto> search(
    		Long idEmpresa,
    		String chave,
			String busca,
			String orderBy, 
			Boolean orderAsc,
			Integer pageNumber, 
			Integer pageSize) throws Exception {
        return produtoService.searchProdutos(idEmpresa, chave, busca, orderBy, orderAsc, pageNumber, pageSize);
    }

}
