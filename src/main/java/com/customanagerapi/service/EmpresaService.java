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
		
		if(empresaRepository.existsByCnpj(empresa.getCnpj())) {
			throw new RegraNegocioException("CNPJ já cadastrado");
		}
				
		empresa.setDataCriacao(LocalDateTime.now());	
		
		Usuario criador = usuarioRepository
                .findById(empresa.getUsuario().getId());
		
		if (criador == null) {
			throw new RegraNegocioException("Código de usuário inválido.");
		}
		
				
		return empresaRepository.save(empresa);
	}	
	
	
	@Transactional
	public Page<Empresa> getAllEmpresas(
			String orderBy, 
			Integer pageNumber, 
			Integer pageSize) {	
		
		
		Sort sort = Sort.by(orderBy);
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		return empresaRepository.findAll(pageable);
	} 
	
	
	@Transactional
	public Optional<Empresa> getEmpresaById(long id) {
		return empresaRepository.findById(id);
	}
	
	@Transactional
	public List<Empresa> getEmpresaByUserId(Usuario usuario) {
		return empresaRepository.findByUsuario(usuario);
	}

}
