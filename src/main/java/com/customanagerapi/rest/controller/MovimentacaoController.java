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

import com.customanagerapi.domain.entity.Movimentacao;
import com.customanagerapi.service.MovimentacaoService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/movimentacoes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MovimentacaoController {
	
	private final MovimentacaoService movimentacaoService;
	
	@PostMapping("/cadastrar")
	@ApiOperation("Cadastro de movimentação")
	@ResponseStatus(HttpStatus.CREATED)
	public Boolean insertMovimentacao(@RequestBody @Valid Movimentacao movimentacao) throws Exception {		
		return movimentacaoService.salvar(movimentacao);
	}
	
	
	@GetMapping("/get-movimentacoes-by-empresa-id")
	@ApiOperation("Obter todas as movimentações de uma empresa específica")
	public Page<Movimentacao> getMovimentacoesByEmpresaId(
			Long idEmpresa,
			String orderBy, 
			Boolean orderAsc,
			Integer pageNumber, 
			Integer pageSize) {		
		
		return movimentacaoService.getMovimentacoesByEmpresaId(idEmpresa, orderBy, orderAsc, pageNumber, pageSize);
	}
	

}
