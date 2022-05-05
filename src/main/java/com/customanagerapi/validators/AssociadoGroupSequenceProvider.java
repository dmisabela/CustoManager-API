package com.customanagerapi.validators;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import com.customanagerapi.domain.entity.Associado;

public class AssociadoGroupSequenceProvider implements DefaultGroupSequenceProvider<Associado>{

	@Override
	public List<Class<?>> getValidationGroups(Associado associado) {
	
		List<Class<?>> groups = new ArrayList<>();
		groups.add(Associado.class);
		
		if(isPessoaSelecionada(associado)) {
			groups.add(associado.getTipoPessoa().getGroup());
		}
		
		return groups;
	}
	
	protected boolean isPessoaSelecionada (Associado associado) {
		return associado != null && associado.getTipoPessoa() != null;
	}
	
	
	
	

}
