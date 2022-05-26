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
import com.customanagerapi.domain.entity.MarcaProduto;
import com.customanagerapi.domain.entity.Produto;
import com.customanagerapi.domain.entity.TipoProduto;
import com.customanagerapi.repository.EmpresaRepository;
import com.customanagerapi.repository.MarcaProdutoRepository;
import com.customanagerapi.repository.ProdutoRepository;
import com.customanagerapi.repository.TipoProdutoRepository;


@Service
public class ProdutoService {
		
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	@Lazy
	private EmpresaRepository empresaRepository;
	
	@Autowired
	@Lazy
	private MarcaProdutoRepository marcaProdutoRepository;
	
	@Autowired
	@Lazy
	private TipoProdutoRepository tipoProdutoRepository;
		
	
	@Transactional
	public Produto salvar(Produto produto) throws Exception {	
		
		Empresa emp = empresaRepository.getById(produto.getIdEmpresa());
		produto.setEmpresa(emp);
		
		TipoProduto tp = tipoProdutoRepository.getById(produto.getIdTipo());
		MarcaProduto mp = marcaProdutoRepository.getById(produto.getIdMarca());
		
		produto.setAtivo(true);
		
		if (this.validarStatusTipoEMarca(tp, mp)) {
			produto.setTipoProduto(tp);	
			produto.setMarcaProduto(mp);
		}			
		
		return produtoRepository.save(produto);		
		
	}
	
	public Boolean validarStatusTipoEMarca(TipoProduto tp, MarcaProduto mp) throws Exception {
		
		if(tp.getAtivo() && mp.getAtivo()) {
			return true;
		}		

		else {
			throw new Exception(
					"Marca ou tipo de produto escolhido está inativo. "
					+ "Verifique e tente novamente");
		}
	}
	
	@Transactional
	public Page<Produto> getProdutosByEmpresaId(
			Long empresaId,
			String orderBy, 
			Boolean orderAsc,
			Integer pageNumber, 
			Integer pageSize) throws Exception {	
		
		try {	
		
			Empresa emp = empresaRepository.getById(empresaId);
			
			Sort sort = orderAsc ? Sort.by(orderBy).ascending() : Sort.by(orderBy).descending();		
			Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
			
			Page<Produto> prd = produtoRepository.findByEmpresa(emp, pageable);			
						
			return prd;
		
		}
		catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	} 
	
	@Transactional
	public Page<Produto> searchProdutos(
			Long empresaId,
			String chave,
			String busca,
			String orderBy, 
			Boolean orderAsc,
			Integer pageNumber, 
			Integer pageSize) throws Exception {
		
		Empresa emp = empresaRepository.getById(empresaId);
		
		Sort sort = orderAsc ? Sort.by(orderBy).ascending() : Sort.by(orderBy).descending();		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Produto> prd = null;
		
		switch(chave) {
		
		case "nome": 
			prd = produtoRepository.
			findByEmpresaAndNomeContainingIgnoreCase(
					emp, 
					busca, 
					pageable);
			break;
		
		case "valorUnitario":
			Double valorUnitario = Double.valueOf(busca);
			prd = produtoRepository.
					findByEmpresaAndValorUnitario(
							emp, 
							valorUnitario, 
							pageable); 	
			break;
			
		case "ativo":
			
			Boolean status = Boolean.valueOf(busca);
			prd = produtoRepository.
					findByEmpresaAndAtivo(
							emp, 
							status, 
							pageable);
			break;	
			
		case "tipoProduto":
			prd = produtoRepository.
			findByEmpresaAndTipoProduto_NomeContainingIgnoreCase(
					emp, 
					busca, 
					pageable);
			break;
			
		case "marcaProduto":
			prd = produtoRepository.
			findByEmpresaAndMarcaProduto_NomeContainingIgnoreCase(
					emp, 
					busca, 
					pageable);
			break;
			
		default: 
			throw new Exception("Chave adicionada inválida.");
			
		}
		
		return prd;
	}
	

	@Transactional
	public Optional<Produto> getProdutoById(long id) {	
		
		Optional<Produto> prd = produtoRepository.findById(id);
		
		if(prd.isPresent()) {			
			Empresa prodEmp = prd.get().getEmpresa();			
			prd.get().setIdEmpresa(prodEmp.getId());
			prd.get().setNomeEmpresa(prodEmp.getNome());
		}
		
		return prd;
		
	}	
	
	@Transactional
	public Produto update(Produto produto) throws Exception {	
			
			Empresa emp = empresaRepository.getById(produto.getIdEmpresa());
			produto.setEmpresa(emp);
			
			TipoProduto tp = tipoProdutoRepository.getById(produto.getIdTipo());
			MarcaProduto mp = marcaProdutoRepository.getById(produto.getIdMarca());
			
			
			if (this.validarStatusTipoEMarca(tp, mp)) {
				produto.setTipoProduto(tp);	
				produto.setMarcaProduto(mp);
			}			
							
			return produtoRepository.save(produto);
	}
	
}