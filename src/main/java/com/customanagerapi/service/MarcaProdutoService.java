package com.customanagerapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.customanagerapi.domain.entity.Empresa;
import com.customanagerapi.domain.entity.MarcaProduto;
import com.customanagerapi.domain.utils.SearchRequest;
import com.customanagerapi.domain.utils.SearchSpecification;
import com.customanagerapi.repository.EmpresaRepository;
import com.customanagerapi.repository.MarcaProdutoRepository;


@Service
public class MarcaProdutoService {
	
	@Autowired
	private MarcaProdutoRepository marcaProdutoRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	

	@Transactional
	public MarcaProduto salvar(MarcaProduto marcaProduto)  {	
		
		Empresa emp = empresaRepository.getById(marcaProduto.getIdEmpresa());
		marcaProduto.setEmpresa(emp);
		
		return marcaProdutoRepository.save(marcaProduto);
	}
	
	@Transactional
	public Page<MarcaProduto> getMarcasByEmpresaId(
			Long empresaId,
			String orderBy, 
			Boolean orderAsc,
			Integer pageNumber, 
			Integer pageSize) throws Exception {
		
		try {
		
		Empresa emp = empresaRepository.getById(empresaId);
		
		
		Sort sort = orderAsc ? Sort.by(orderBy).ascending() : Sort.by(orderBy).descending();		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
	    Page<MarcaProduto> marcas = marcaProdutoRepository.findByEmpresa(emp, pageable);
	    
	    if(marcas.getContent().isEmpty()) {
	    	throw new Exception("Ocorreu um erro ao obter as marcas de produto. "
	    			+ "Verifique se a empresa selecionada não possui nenhuma marca cadastrada.");
	    }  
	    
		return marcas;
		
		}
		catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Transactional
	public Optional<MarcaProduto> getMarcaProdutoById(long id) {						
		return marcaProdutoRepository.findById(id);
		
	}
	
	@Transactional
	public MarcaProduto update(MarcaProduto marcaProduto)  {	
		
		Empresa emp = empresaRepository.getById(marcaProduto.getIdEmpresa());
		marcaProduto.setEmpresa(emp);
		
		return marcaProdutoRepository.save(marcaProduto);
	}
	
	public Page<MarcaProduto> searchMarca(SearchRequest request, String orderBy, 
    		Boolean orderAsc, Integer pageNumber, Integer pageSize) {
        SearchSpecification<MarcaProduto> specification = new SearchSpecification<>(request);
        Sort sort = orderAsc ? Sort.by(orderBy).ascending() : Sort.by(orderBy).descending();		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
	        
        return marcaProdutoRepository.findAll(specification, pageable);
    }
	
	

}
