package br.com.acertsis.loja.entity;

import lombok.Getter;

public enum PeriodoEnum {
    ANUAL(12);

    @Getter
    private int periodo;

    public int getPeriodo() {
        return periodo;
    }

    private PeriodoEnum(int periodo) {
        this.periodo = periodo;
    }

}
