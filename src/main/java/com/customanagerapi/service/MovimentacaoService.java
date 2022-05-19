package com.customanagerapi.service;

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

import com.customanagerapi.domain.entity.Associado;
import com.customanagerapi.domain.entity.Empresa;
import com.customanagerapi.domain.entity.Movimentacao;
import com.customanagerapi.domain.entity.MovimentacaoProduto;
import com.customanagerapi.repository.AssociadoRepository;
import com.customanagerapi.repository.EmpresaRepository;
import com.customanagerapi.repository.MovimentacaoRepository;
import com.customanagerapi.repository.ProdutoRepository;


@Service
public class MovimentacaoService {
	
	@Autowired
	private MovimentacaoProdutoService movimentacaoProdService;
	
	@Autowired
	private MovimentacaoRepository movimentacaoRepository;
			
	@Autowired
	@Lazy
	private EmpresaRepository empresaRepository;
	
	@Autowired
	@Lazy
	private AssociadoRepository associadoRepository;
	
	@Autowired
	@Lazy
	private ProdutoRepository produtoRepository;
	

	@Transactional
	public Boolean salvar(Movimentacao movimentacao) throws Exception  {	
		
		try {
			
			Empresa emp = empresaRepository.getById(movimentacao.getIdEmpresa());
			movimentacao.setEmpresa(emp);
			
			Associado assoc = associadoRepository.getById(movimentacao.getIdAssociado());
			movimentacao.setAssociado(assoc);
			
			List<MovimentacaoProduto> mp = movimentacao.getMovimentacaoProdutos();	
			
			if (movimentacao.getTipoMovimentacao().toString().equals("VENDA")) {
				calcularValorTotalMovimentacao(movimentacao, mp);	
			}
						
			movimentacaoRepository.save(movimentacao);	

			for(MovimentacaoProduto m1 : mp) {
				m1.setMovimentacao(movimentacao);
			}
			
			movimentacaoProdService.salvar(mp);
			
			return true;
			
		}
		
		catch (Exception e) {
			throw new Exception("Ocorreu um erro ao salvar a movimentação");
		}
		
		
	}
	
	
	public Boolean calcularValorTotalMovimentacao(Movimentacao m, List<MovimentacaoProduto> mp) throws Exception {
		
		try {
			
			Double valorTotalMovimentacao = 0.0;
			
			for (MovimentacaoProduto m1 : mp) {
				Double valorTotalProdutoMov = m1.getValorUnitario() * m1.getQuantidade();
				
				valorTotalMovimentacao = valorTotalMovimentacao + valorTotalProdutoMov;
			}
			
			m.setValorTotal(valorTotalMovimentacao);				

			return true;			
		}	
		
		catch (Exception e) {
			throw new Exception("Ocorreu um erro ao calcular valor total da movimentação");
		}
	}
	
	@Transactional
	public Page<Movimentacao> getMovimentacoesByEmpresaId(
			Long empresaId,
			String orderBy, 
			Boolean orderAsc,
			Integer pageNumber, 
			Integer pageSize) {
		
		Empresa emp = empresaRepository.getById(empresaId);
		
		
		Sort sort = orderAsc ? Sort.by(orderBy).ascending() : Sort.by(orderBy).descending();		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
	    Page<Movimentacao> movimentacao = movimentacaoRepository.findByEmpresa(emp, pageable);
	    
		return movimentacao;
	}
	

	@Transactional
	public Optional<Movimentacao> getMovimentacaoById(long id) {						
		return movimentacaoRepository.findById(id);
		
	}
	
	

}
