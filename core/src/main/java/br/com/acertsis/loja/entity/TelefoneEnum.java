package br.com.acertsis.loja.entity;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;

public enum TelefoneEnum {
    GERAL("Geral"), CELULAR("Celular"), COMERCIAL("Comercial"), RESIDENCIAL("Residencial");

    @Getter
    private String label;

    private TelefoneEnum(String label) {
        this.label = label;
    }

    public static List<TelefoneEnum> toList() {
        return Arrays.asList(values());
    }

}
