package com.customanagerapi.enums;

public enum TipoPessoaEnum {
    PESSOA_FISICA("PF"),
    PESSOA_JURIDICA("PJ");

    private String text;

    TipoPessoaEnum(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static TipoPessoaEnum fromString(String text) {
        for (TipoPessoaEnum tp : TipoPessoaEnum.values()) {
            if (tp.text.equalsIgnoreCase(text)) {
                return tp;
            }
        }
        return null;
    }
}