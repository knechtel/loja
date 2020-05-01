package br.com.acertsis.loja.entity;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;

public enum PessoaEnum {
    FISICA("Física"),
    JURIDICA("Jurídica");

    @Getter
    private String label;

    private PessoaEnum(String label) {
        this.label = label;
    }

    public static List<PessoaEnum> toList() {
        return Arrays.asList(FISICA, JURIDICA);
    }
}
