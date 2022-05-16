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

import com.customanagerapi.domain.entity.TipoProduto;
import com.customanagerapi.service.TipoProdutoService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tipo-produto")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TipoProdutoController {
	
	private final TipoProdutoService tipoService;

	@PostMapping("/cadastrar-tipo")
	@ApiOperation("Cadastro tipo de produtos")
	@ResponseStatus(HttpStatus.CREATED)
	public TipoProduto insertTipoProduto(@RequestBody @Valid TipoProduto tipoProduto) throws Exception {		
		return tipoService.salvar(tipoProduto);
	} 
	
	@GetMapping("/get-tipos-produto-by-empresa-id")
	@ApiOperation("Obter os tipos de produto de uma empresa específica")
	public Page<TipoProduto> getTiposByEmpresaId(
			Long idEmpresa,
			String orderBy, 
			Boolean orderAsc,
			Integer pageNumber, 
			Integer pageSize) throws Exception {		
		
		return tipoService.getTiposByEmpresaId(idEmpresa, orderBy, orderAsc, pageNumber, pageSize);
	}
	
	@PutMapping("/update")
	@ApiOperation("Alterar dados do tipo de produto")
	public TipoProduto updateTipo(@RequestBody @Valid TipoProduto tipoProduto) throws Exception {
		return tipoService.update(tipoProduto);
	}

}
