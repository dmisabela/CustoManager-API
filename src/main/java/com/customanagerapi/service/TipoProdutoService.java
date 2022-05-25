package com.customanagerapi.service;

import java.util.Optional;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.customanagerapi.domain.entity.Empresa;
import com.customanagerapi.domain.entity.TipoProduto;
import com.customanagerapi.domain.utils.SearchRequest;
import com.customanagerapi.domain.utils.SearchSpecification;
import com.customanagerapi.repository.EmpresaRepository;
import com.customanagerapi.repository.TipoProdutoRepository;

@Service
public class TipoProdutoService {
	
	
	@Autowired
	private TipoProdutoRepository tipoProdutoRepository;
	
	@Autowired
	@Lazy
	private EmpresaRepository empresaRepository;
	
	@Transactional
	public TipoProduto salvar(TipoProduto tipoProduto)  {
		
		Empresa emp = empresaRepository.getById(tipoProduto.getIdEmpresa());
		tipoProduto.setEmpresa(emp);
		
		return tipoProdutoRepository.save(tipoProduto);
	}
	
	
	@Transactional
	public Page<TipoProduto> getTiposByEmpresaId(
			Long empresaId,
			String orderBy, 
			Boolean orderAsc,
			Integer pageNumber, 
			Integer pageSize) throws Exception {
		
		try {
			Empresa emp = empresaRepository.getById(empresaId);
			
			
			Sort sort = orderAsc ? Sort.by(orderBy).ascending() : Sort.by(orderBy).descending();		
			Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
			
		    Page<TipoProduto> tipos = tipoProdutoRepository.findByEmpresa(emp, pageable);
		    
		    if(tipos.getContent().isEmpty()) {
		    	throw new Exception("Ocorreu um erro ao obter os tipos de produto. "
		    			+ "Verifique se a empresa selecionada não possui nenhum tipo cadastrado.");
		    }  
		    
			return tipos;
			
		}
		catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}
	
	@Transactional
	public Optional<TipoProduto> getTipoProdutoById(long id) {						
		return tipoProdutoRepository.findById(id);
		
	}
	
	@Transactional
	public TipoProduto update(TipoProduto tipoProduto)  {	
		
		Empresa emp = empresaRepository.getById(tipoProduto.getIdEmpresa());
		tipoProduto.setEmpresa(emp);
		
		return tipoProdutoRepository.save(tipoProduto);
	}
	
	public Page<TipoProduto> searchTipoProdutos(SearchRequest request, String orderBy, 
    		Boolean orderAsc, Integer pageNumber, Integer pageSize) {
        SearchSpecification<TipoProduto> specification = new SearchSpecification<>(request);
        Sort sort = orderAsc ? Sort.by(orderBy).ascending() : Sort.by(orderBy).descending();		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
	        
        return tipoProdutoRepository.findAll(specification, pageable);
    }
	
	
	
	
	
	
}
