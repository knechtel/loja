package br.com.acertsis.loja.entity;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;

public enum MeioPagamentoEnum {
    CARTAO_CREDITO("Cartão de Crédito"),
    BOLETO("Boleto");

    @Getter
    private String tipo;

    private MeioPagamentoEnum(String tipo) {
        this.tipo = tipo;
    }

    public static List<MeioPagamentoEnum> toList() {
        return Arrays.asList(values());
    }
}
