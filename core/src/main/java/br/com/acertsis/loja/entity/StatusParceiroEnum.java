/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.entity;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;

/**
 *
 *
 */
public enum StatusParceiroEnum {

    SOLICITADO("Solicitado"),
    ATIVO("Ativo"),
    INATIVO("Inativo"),
    REJEITADO("Rejeitado"),
    CANCELADO("Cancelado");

    @Getter
    private String label;

    private StatusParceiroEnum(String label) {
        this.label = label;
    }

    public static List<StatusParceiroEnum> toList() {
        return Arrays.asList(ATIVO, INATIVO, REJEITADO, CANCELADO, SOLICITADO);
    }
    
}
