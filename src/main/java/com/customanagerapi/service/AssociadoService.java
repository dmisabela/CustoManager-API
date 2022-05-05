package com.customanagerapi.service;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import com.customanagerapi.domain.entity.Associado;
import com.customanagerapi.domain.entity.Empresa;
import com.customanagerapi.repository.AssociadoRepository;
import com.customanagerapi.repository.EmpresaRepository;

@Service
public class AssociadoService {
	
	
	@Autowired
	private AssociadoRepository associadoRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	
	@Transactional
	public Associado salvar(Associado associado)  {
		
		Empresa emp = empresaRepository.getById(associado.getIdEmpresa());		
		associado.setEmpresaAssociado(emp);
		
		return associadoRepository.save(associado);
	}	
	
	@Transactional
	public List<Associado> getAssociadosByEmpresaId(@PathVariable long id) {
		
		Optional<Empresa> empresaAssociado = empresaRepository.findById(id);			
		return associadoRepository.findByEmpresaAssociado(empresaAssociado.get());
	}
	

}
