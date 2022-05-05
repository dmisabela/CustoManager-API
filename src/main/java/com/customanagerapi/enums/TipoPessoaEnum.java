package com.customanagerapi.enums;

import com.customanagerapi.interfaces.CnpjGroup;
import com.customanagerapi.interfaces.CpfGroup;

public enum TipoPessoaEnum {
    PESSOA_FISICA("PF", "CPF", "000.000.000-00", CpfGroup.class),
    PESSOA_JURIDICA("PJ", "CNPJ", "00.000.000/0000-00", CnpjGroup.class);

    private final String descricao;
    private final String documento;
    private final String mascara;
    private final Class<?> group;
    
    

    private TipoPessoaEnum(String descricao, String documento, String mascara, Class<?> group) {
		this.descricao = descricao;
		this.documento = documento;
		this.mascara = mascara;
		this.group = group;
	}

    

    public String getDescricao() {
		return descricao;
	}

	public String getDocumento() {
		return documento;
	}

	public String getMascara() {
		return mascara;
	}
	
	public Class<?> getGroup() {
		return group;
	}

	public static TipoPessoaEnum fromString(String text) {
        for (TipoPessoaEnum tp : TipoPessoaEnum.values()) {
            if (tp.descricao.equalsIgnoreCase(text)) {
                return tp;
            }
        }
        return null;
    }
}