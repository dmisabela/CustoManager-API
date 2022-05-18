package com.customanagerapi.service;

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
			Integer pageSize) {
		
		Empresa emp = empresaRepository.getById(empresaId);
		
		
		Sort sort = orderAsc ? Sort.by(orderBy).ascending() : Sort.by(orderBy).descending();		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
	    Page<MarcaProduto> marcas = marcaProdutoRepository.findByEmpresa(emp, pageable);
	    
		return marcas;
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
