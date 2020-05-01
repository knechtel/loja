package br.com.acertsis.loja.dto;

public class NotaFiscalRenovacaoDTO {
    private int quantidade;
    private String mes;
    private Double porcComissao;
    private Double valorComissao;

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Double getPorcComissao() {
        return porcComissao;
    }

    public void setPorcComissao(Double porcComissao) {
        this.porcComissao = porcComissao;
    }

    public Double getValorComissao() {
        return valorComissao;
    }

    public void setValorComissao(Double valorComissao) {
        this.valorComissao = valorComissao;
    }
}
