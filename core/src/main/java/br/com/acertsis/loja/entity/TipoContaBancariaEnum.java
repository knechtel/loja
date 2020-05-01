/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.entity;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;

public enum TipoContaBancariaEnum {
    FISICA("Fisica"),
    JURIDICA("Juridica");

    @Getter
    private String label;

    private TipoContaBancariaEnum(String label) {
        this.label = label;
    }

    public static List<TipoContaBancariaEnum> toList() {
        return Arrays.asList(FISICA, JURIDICA);
    }

}
