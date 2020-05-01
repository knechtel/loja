package br.com.acertsis.loja.entity;

public enum StatusContratoEnum {
    ATIVO("Ativo"), SUSPENSO("Suspenso"), CANCELADO("Cancelado"), EXPIRADO("Expirado"),
    PENDENTEPAG("Pendente Pagamento"), PENDENTEATV("Pendente Ativação");

    private String label;

    StatusContratoEnum(String label) {
        this.label = label;
    }
}
