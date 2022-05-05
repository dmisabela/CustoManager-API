package com.customanagerapi.enums;

public enum TipoAssociadoEnum {
    CLIENTE("CLIENTE"),
    FORNECEDOR("FORNECEDOR");

    private String text;

    TipoAssociadoEnum(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static TipoAssociadoEnum fromString(String text) {
        for (TipoAssociadoEnum ta : TipoAssociadoEnum.values()) {
            if (ta.text.equalsIgnoreCase(text)) {
                return ta;
            }
        }
        return null;
    }
}