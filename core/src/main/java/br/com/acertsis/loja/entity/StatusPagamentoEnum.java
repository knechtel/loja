package br.com.acertsis.loja.entity;

import lombok.Getter;

public enum StatusPagamentoEnum {
    AGUARDANDO("Aguardando Pagamento"),
    QUITADO("Pago"),
    PARCIALMENTE("Parcialmente Pago"),
    CANCELADO("CANCELADO"),
    ATRASO("EM ATRASO"),
    BOLETO_NAO_EMITIDO("Não Emitido");

    @Getter
    private String status;

    StatusPagamentoEnum(String status) {
        this.status = status;
    }
}