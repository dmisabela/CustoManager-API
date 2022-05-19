package com.customanagerapi.service;


import java.util.List;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.customanagerapi.domain.entity.Movimentacao;
import com.customanagerapi.domain.entity.MovimentacaoProduto;
import com.customanagerapi.repository.MovimentacaoProdutoRepository;
import com.customanagerapi.repository.MovimentacaoRepository;
import com.customanagerapi.repository.ProdutoRepository;


@Service
public class MovimentacaoProdutoService {
	
	@Autowired
	private MovimentacaoProdutoRepository movimentacaoProdRepository;
	
	@Autowired
	@Lazy
	private MovimentacaoRepository movimentacaoRepository;
	
	@Autowired
	@Lazy
	private ProdutoRepository produtoRepository;
	

	@Transactional
	public Boolean salvar(List<MovimentacaoProduto> mp)  {	
				
		movimentacaoProdRepository.saveAll(mp);
		
		return true;
	}
	
	@Transactional
	public Page<MovimentacaoProduto> getProdutosByMovimentacaoId(
			Long idMovimentacao,
			String orderBy, 
			Boolean orderAsc,
			Integer pageNumber, 
			Integer pageSize) {
				
		
		Sort sort = orderAsc ? Sort.by(orderBy).ascending() : Sort.by(orderBy).descending();		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		Movimentacao mov = movimentacaoRepository.getById(idMovimentacao);
		
	    Page<MovimentacaoProduto> movimentacaoProdutos = movimentacaoProdRepository
	    										.findByMovimentacao(mov, pageable);
	    
		return movimentacaoProdutos;
	}
	
	

}
