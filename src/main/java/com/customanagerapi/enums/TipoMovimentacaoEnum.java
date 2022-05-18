package com.customanagerapi.enums;

public enum TipoMovimentacaoEnum {
    VENDA("VENDA"),
    COMPRA("COMPRA");

    private String text;

    TipoMovimentacaoEnum(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static TipoMovimentacaoEnum fromString(String text) {
        for (TipoMovimentacaoEnum tm : TipoMovimentacaoEnum.values()) {
            if (tm.text.equalsIgnoreCase(text)) {
                return tm;
            }
        }
        return null;
    }
}