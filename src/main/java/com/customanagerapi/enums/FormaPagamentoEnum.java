package com.customanagerapi.enums;

public enum FormaPagamentoEnum {
    CARTAO_CREDITO("CARTAO_CREDITO"),
    CARTAO_DEBITO("CARTAO_DEBITO"),
    PIX("PIX"),
    DINHEIRO("DINHEIRO");

    private String text;

    FormaPagamentoEnum(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static FormaPagamentoEnum fromString(String text) {
        for (FormaPagamentoEnum fp : FormaPagamentoEnum.values()) {
            if (fp.text.equalsIgnoreCase(text)) {
                return fp;
            }
        }
        return null;
    }
}