package br.com.acertsis.loja.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id_pagamento", foreignKey = @ForeignKey(name = "fk_pagamentoboleto"))
public class PagamentoBoleto extends Pagamento {

    @Column(name = "reftran")
    private String refTran;
    @Column(name = "codigoMovimento")
    private String codigoMovimento;
    private Double valorPagamento;
    private LocalDate dataVencimento;


    public Double getValorPagamento() {
        return valorPagamento;
    }

    public void setValorPagamento(Double valorPagamento) {
        this.valorPagamento = valorPagamento;
    }

    public String getRefTran() {
        return refTran;
    }

    public void setRefTran(String refTran) {
        this.refTran = refTran;
    }

    public String getCodigoMovimento() {
        return codigoMovimento;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public void setCodigoMovimento(String codigoMovimento) {
        this.codigoMovimento = codigoMovimento;
    }
}
