package com.customanagerapi.service;



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
			Integer pageSize) {	
		
		Empresa emp = empresaRepository.getById(empresaId);
		
		Sort sort = orderAsc ? Sort.by(orderBy).ascending() : Sort.by(orderBy).descending();		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Produto> prd = produtoRepository.findByEmpresa(emp, pageable);
		
		return prd;
	} 
	
	
	@Transactional
	public Produto update(Produto produto)  {	
		
		try {
			
			Empresa emp = empresaRepository.getById(produto.getIdEmpresa());
			produto.setEmpresa(emp);
			
			TipoProduto tp = tipoProdutoRepository.getById(produto.getIdTipo());
			MarcaProduto mp = marcaProdutoRepository.getById(produto.getIdMarca());
			
			
			if (this.validarStatusTipoEMarca(tp, mp)) {
				produto.setTipoProduto(tp);	
				produto.setMarcaProduto(mp);
			}			
				
		} 
		
		catch (Exception e) {
				e.getMessage();
			}
			
			return produtoRepository.save(produto);
	}
	
}