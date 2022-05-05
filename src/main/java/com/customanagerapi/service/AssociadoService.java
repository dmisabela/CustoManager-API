package com.customanagerapi.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	

}
