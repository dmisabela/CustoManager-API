package com.customanagerapi.service;

import java.time.LocalDateTime;
import java.util.List;
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
import com.customanagerapi.domain.entity.Usuario;
import com.customanagerapi.domain.utils.SearchRequest;
import com.customanagerapi.domain.utils.SearchSpecification;
import com.customanagerapi.exception.RegraNegocioException;
import com.customanagerapi.repository.EmpresaRepository;
import com.customanagerapi.repository.UsuarioRepository;

@Service
public class EmpresaService {
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	@Lazy
	private UsuarioRepository usuarioRepository;
	
	
	@Transactional
	public Empresa salvar(Empresa empresa) throws RegraNegocioException {	
		
		this.existsByCnpj(empresa);
				
		empresa.setDataCriacao(LocalDateTime.now());	
		
		Usuario criador = usuarioRepository
                .findById(empresa.getIdUsuarioCriador());
		
		if (criador == null) {
			throw new RegraNegocioException("Código de usuário inválido.");
		}
		
		empresa.setUsuario(criador);
		
				
		return empresaRepository.save(empresa);
	}	
	
	
	@Transactional
	public Page<Empresa> getAllEmpresas(
			String orderBy, 
			Boolean orderAsc,
			Integer pageNumber, 
			Integer pageSize) throws Exception {	
		
		
		try {		
		
			Sort sort = orderAsc ? Sort.by(orderBy).ascending() : Sort.by(orderBy).descending();		
			Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
			
			Page<Empresa> emp = empresaRepository.findAll(pageable);
			
			for (Empresa empr : emp.getContent()) {
				empr.setIdUsuarioCriador((int) empr.getUsuario().getId());
			}
			
			return emp;
		
		}
		catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	} 
	
	public Page<Empresa> searchEmpresas(SearchRequest request, String orderBy, 
    		Boolean orderAsc, Integer pageNumber, Integer pageSize) {
        SearchSpecification<Empresa> specification = new SearchSpecification<>(request);
        Sort sort = orderAsc ? Sort.by(orderBy).ascending() : Sort.by(orderBy).descending();		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
	        
        return empresaRepository.findAll(specification, pageable);
    }
	
	
	@Transactional
	public Optional<Empresa> getEmpresaById(long id) {
		
		Optional<Empresa> emp = empresaRepository.findById(id);
		
		if (emp.isPresent()) {
			Integer idUsuarioCriador = (int) emp.get().getUsuario().getId();			
			emp.get().setIdUsuarioCriador(idUsuarioCriador);
		}
		
		return emp; 
		
	}
	
	@Transactional
	public List<Empresa> getEmpresaByUserId(long id) throws Exception {
		
		try {
			Usuario user = usuarioRepository.findById(id);		
			return empresaRepository.findByUsuario(user);
		}
		catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}
	
    public Boolean existsByCnpj(Empresa empresa) {
		
		if(empresaRepository.existsById(empresa.getId())) {
			
			Empresa actualEmp = empresaRepository.getById(empresa.getId());
			
			String oldCnpj = actualEmp.getCnpj();
			String newCnpj = empresa.getCnpj();
			
			if(!newCnpj.equals(oldCnpj) && empresaRepository.existsByCnpj(newCnpj)) {
				throw new RegraNegocioException("CNPJ já cadastrado");
			}	
			else {
				return false;
			}
		}	
		
		else {	
			
			if(empresaRepository.existsByCnpj(empresa.getCnpj())) {
				throw new RegraNegocioException("CNPJ já cadastrado");
			}		
			else {
				return false;
			}
			
		}
		
	}
	
	
	@Transactional
	public Empresa update(Empresa empresa) {
		

		this.existsByCnpj(empresa);
		
		Usuario criador = usuarioRepository
                .findById(empresa.getIdUsuarioCriador());		
		
		empresa.setUsuario(criador);
		
		return empresaRepository.save(empresa);
	}
	
	@Transactional
	public void delete(Long id) { 
		empresaRepository.deleteById(id);
	}

}
