package com.customanagerapi.rest.controller;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
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
	
	@PutMapping("/update")
	@ApiOperation("Editar dados da movimentação")
	public Boolean updateMovimentacao(@RequestBody @Valid Movimentacao movimentacao) throws Exception {		
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
	
	@GetMapping("/get-by-id/{id}") 
	@ApiOperation("Obter detalhes de uma movimentação pelo ID")
	public Optional<Movimentacao> getMovimentacaoById(@PathVariable("id") long id) {
		return movimentacaoService.getMovimentacaoById(id);
	}
	
	@GetMapping("/get-quantidade-by-tipo/{empresaId}") 
	@ApiOperation("Obter quantidade de movimentações por tipo")
	public Map<String, String> getMovimentacoesByTipo(@PathVariable("empresaId") long empresaId) {
		return movimentacaoService.getMovimentacoesByTipo(empresaId);
	}
	
	@GetMapping("/search")
	@ApiOperation("Pesquisar movimentacão por filtros")
    public Page<Movimentacao> search(
    		Long idEmpresa,
    		String chave,
			String busca,
			String orderBy, 
			Boolean orderAsc,
			Integer pageNumber, 
			Integer pageSize) throws Exception {
        return movimentacaoService.search(idEmpresa, chave, busca, orderBy, orderAsc, pageNumber, pageSize);
    }
	
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable("id") Long id) {
		movimentacaoService.delete(id);
	}
	

}
