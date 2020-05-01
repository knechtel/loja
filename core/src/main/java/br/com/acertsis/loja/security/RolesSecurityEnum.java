/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.security;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;

public enum RolesSecurityEnum {
    ROLE_ADMIN("ADMIN", "Adminstrador"),
    ROLE_PARCEIRO("PARCEIRO", "Parceiro");

    @Getter
    private String label;
    /**
     * Algumas classes no sistema inferem o pr ROLE_, então é cessário criar um
     * metodo que retorno o nome da ROLE sem o prefixo "ROLE_"
     *
     */
    @Getter
    private String nameSemPrefixo;

    private RolesSecurityEnum(String role, String label) {
        this.nameSemPrefixo = role;
        this.label = label;
    }

    public static List<RolesSecurityEnum> toList() {
        return Arrays.asList(values());
    }
}
