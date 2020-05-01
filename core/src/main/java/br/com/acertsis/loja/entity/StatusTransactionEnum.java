package br.com.acertsis.loja.entity;

public enum StatusTransactionEnum {
    NotFinished(0), Authorized(1), PaymentConfirmed(2), Denied(3), Voided(10),
    Refunded(11), Pending(12), Aborted(13), Scheduled(20);

    private Integer codigo;

    public int getcodigo() {
        return codigo;
    }

    private StatusTransactionEnum(Integer codigo){this.codigo=codigo;}
}
