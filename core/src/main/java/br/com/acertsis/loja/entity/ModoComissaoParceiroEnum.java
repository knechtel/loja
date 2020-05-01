/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.entity;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;

public enum ModoComissaoParceiroEnum {
    SOBRE_TODAS_AS_VENDAS("Sobre todas as vendas realizadas."),
    APENAS_DOS_CLIENTES("Somente sobre suas vendas.");

    @Getter
    private String label;

    private ModoComissaoParceiroEnum(String label) {
        this.label = label;
    }

    public static List<ModoComissaoParceiroEnum> toList() {
        return Arrays.asList(APENAS_DOS_CLIENTES, SOBRE_TODAS_AS_VENDAS);
    }
}
