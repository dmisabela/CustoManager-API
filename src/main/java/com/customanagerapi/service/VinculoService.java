package com.customanagerapi.service;

import java.time.LocalDateTime;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.customanagerapi.domain.entity.Associado;
import com.customanagerapi.domain.entity.Empresa;
import com.customanagerapi.domain.entity.Usuario;
import com.customanagerapi.domain.entity.VinculoAssociadoEmpresa;
import com.customanagerapi.exception.RegraNegocioException;
import com.customanagerapi.repository.EmpresaRepository;
import com.customanagerapi.repository.UsuarioRepository;
import com.customanagerapi.repository.VinculoRepository;

@Service
public class VinculoService {
	

	@Autowired
	private VinculoRepository vinculoRepository;
	
	@Autowired
	@Lazy
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	@Lazy
	private EmpresaRepository empresaRepository;
	
	@Autowired
	@Lazy
	private AssociadoService associadoService;
	
	public VinculoAssociadoEmpresa validarVinculo(VinculoAssociadoEmpresa vinculo) throws Exception {			
		try {
			
			vinculo.setDataCriacao(LocalDateTime.now());	
			
			Usuario criador = usuarioRepository
					.findById(vinculo.getUsuarioCriador().getId());
			
			if (criador == null) {
				throw new RegraNegocioException("Código de usuário criador inválido.");
			}
			
			Usuario funcionario = usuarioRepository
					.findById(vinculo.getUsuarioFuncionario().getId());
			
			if (funcionario == null) {
				throw new RegraNegocioException("Código de usuário funcionário inválido.");		
			}	
			
		    Associado associado = vinculo.getAssociado();
		    
		    String documentoAssociado = associado.getDocumento();		    
		    Integer tamanhoDoc = documentoAssociado.length();
		    
		    if(tamanhoDoc == 14) {
		    	associado.setCpf(documentoAssociado);
		    }
		    else {
		    	associado.setCnpj(documentoAssociado);
		    }	 	    
		    
		    associadoService.salvar(associado);	
		    
			return this.salvar(vinculo);			
		}
		
		catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}	
	
	@Transactional
	public VinculoAssociadoEmpresa salvar(VinculoAssociadoEmpresa vinculo)  {
		return vinculoRepository.saveAndFlush(vinculo);
	}	
	
}
