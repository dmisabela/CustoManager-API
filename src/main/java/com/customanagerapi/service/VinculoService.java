package com.customanagerapi.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.PathVariable;

import com.customanagerapi.domain.entity.Associado;
import com.customanagerapi.domain.entity.Empresa;
import com.customanagerapi.domain.entity.Usuario;
import com.customanagerapi.domain.entity.VinculoUsuarioEmpresa;
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
	
	public VinculoUsuarioEmpresa salvar(VinculoUsuarioEmpresa vinculo) throws Exception {			
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
			
			Empresa emp = empresaRepository.getById(vinculo.getIdEmpresaVinculo());		
			vinculo.setEmpresaVinculo(emp);
		    
			return vinculoRepository.save(vinculo);			
		}
		
		catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}		
	
	@Transactional
	public List<VinculoUsuarioEmpresa> getVinculosByEmpresaId(@PathVariable long id) {
		
		Optional<Empresa> empresaVinculo = empresaRepository.findById(id);			
		return vinculoRepository.findByEmpresaVinculo(empresaVinculo.get());
	}
	
	@Transactional
	public VinculoUsuarioEmpresa update(VinculoUsuarioEmpresa vinculo) {		
		
		Empresa emp = empresaRepository.getById(vinculo.getIdEmpresaVinculo());		
		vinculo.setEmpresaVinculo(emp);
		
		return vinculoRepository.save(vinculo);
	}
	
	
}
