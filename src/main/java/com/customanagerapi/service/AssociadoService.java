package com.customanagerapi.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.customanagerapi.domain.entity.Associado;
import com.customanagerapi.repository.AssociadoRepository;

@Service
public class AssociadoService {
	
	
	@Autowired
	private AssociadoRepository associadoRepository;
	
	
	@Transactional
	public Associado salvar(Associado associado)  {
		return associadoRepository.saveAndFlush(associado);
	}	
	

}
