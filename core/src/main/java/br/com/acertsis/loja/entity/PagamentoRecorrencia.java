package br.com.acertsis.loja.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.*;

@PrimaryKeyJoinColumn(name="id_pagamento", foreignKey = @ForeignKey(name = "fk_pagamentorecorrencia"))
@Entity
public class PagamentoRecorrencia extends Pagamento{

    //ProofOfSale
    @Column(name = "numero_autorizacao")
    private String numeroAutorizacao;
    //Id da transação na adquirente.
    private String tid;
    //AuthorizationCode
    @Column(name = "codigo_autorizacao")
    private String codAutorizacao;
    //PaymentID
    @Column(name = "identificador_pedido")
    private String identificadorPedido;
    //Código de retorno da Adquirência.
    @Column(name = "codigo_retorno")
    private String codRetorno;
    //Mensagem de retorno da Adquirência.
    @Column(name = "mensagem_retorno")
    private String mensagemRetorno;
    @Column(name = "dt_retorno")
    private LocalDateTime dtRetorno;
    //status da transação
    @Column(name = "status_transacao")
    //@Enumerated(EnumType.STRING)
    //private StatusPagamentoEnum statusTransaction;
    private Integer statusTransaction;
    //Data da próxima recorrência.
    @Column(name = "dt_proxima_recorrencia")
    private LocalDate dtProximaRecorrencia;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_dado_cartao", foreignKey = @ForeignKey(name = "fk_dadosrecorrencia"))
    private DadosRecorrencia dadosRecorrencia;

    public String getNumeroAutorizacao() {
        return numeroAutorizacao;
    }

    public void setNumeroAutorizacao(String numeroAutorizacao) {
        this.numeroAutorizacao = numeroAutorizacao;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getCodAutorizacao() {
        return codAutorizacao;
    }

    public void setCodAutorizacao(String codAutorizacao) {
        this.codAutorizacao = codAutorizacao;
    }

    public String getIdentificadorPedido() {
        return identificadorPedido;
    }

    public void setIdentificadorPedido(String identificadorPedido) {
        this.identificadorPedido = identificadorPedido;
    }

    public String getCodRetorno() {
        return codRetorno;
    }

    public void setCodRetorno(String codRetorno) {
        this.codRetorno = codRetorno;
    }

    public String getMensagemRetorno() {
        return mensagemRetorno;
    }

    public void setMensagemRetorno(String mensagemRetorno) {
        this.mensagemRetorno = mensagemRetorno;
    }

    public LocalDateTime getDtRetorno() {
        return dtRetorno;
    }

    public void setDtRetorno(LocalDateTime dtRetorno) {
        this.dtRetorno = dtRetorno;
    }

    public Integer getStatusTransaction() {
        return statusTransaction;
    }

    public void setStatusTransaction(Integer statusTransaction) {
        this.statusTransaction = statusTransaction;
    }

    public LocalDate getDtProximaRecorrencia() {
        return dtProximaRecorrencia;
    }

    public void setDtProximaRecorrencia(LocalDate dtProximaRecorrencia) {
        this.dtProximaRecorrencia = dtProximaRecorrencia;
    }

    public DadosRecorrencia getDadosRecorrencia() {
        return dadosRecorrencia;
    }

    public void setDadosRecorrencia(DadosRecorrencia dadosRecorrencia) {
        this.dadosRecorrencia = dadosRecorrencia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PagamentoRecorrencia that = (PagamentoRecorrencia) o;
        return Objects.equals(numeroAutorizacao, that.numeroAutorizacao) &&
                Objects.equals(tid, that.tid) &&
                Objects.equals(codAutorizacao, that.codAutorizacao) &&
                Objects.equals(identificadorPedido, that.identificadorPedido) &&
                Objects.equals(codRetorno, that.codRetorno) &&
                Objects.equals(mensagemRetorno, that.mensagemRetorno) &&
                Objects.equals(dtRetorno, that.dtRetorno) &&
                statusTransaction == that.statusTransaction &&
                Objects.equals(dtProximaRecorrencia, that.dtProximaRecorrencia) &&
                Objects.equals(dadosRecorrencia, that.dadosRecorrencia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), numeroAutorizacao, tid, codAutorizacao, identificadorPedido, codRetorno, mensagemRetorno, dtRetorno, statusTransaction, dtProximaRecorrencia, dadosRecorrencia);
    }

    @Override
    public String toString() {
        return "PagamentoRecorrencia{" +
                "numeroAutorizacao='" + numeroAutorizacao + '\'' +
                ", tid='" + tid + '\'' +
                ", codAutorizacao='" + codAutorizacao + '\'' +
                ", identificadorPedido='" + identificadorPedido + '\'' +
                ", codRetorno='" + codRetorno + '\'' +
                ", mensagemRetorno='" + mensagemRetorno + '\'' +
                ", dtRetorno=" + dtRetorno +
                ", statusTransaction=" + statusTransaction +
                ", dtProximaRecorrencia=" + dtProximaRecorrencia +
                ", dadosRecorrencia=" + dadosRecorrencia +
                '}';
    }
}
