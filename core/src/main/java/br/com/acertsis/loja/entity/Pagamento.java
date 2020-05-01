package br.com.acertsis.loja.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pagamento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pagamento")
    private Long idPagamento;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_mensalidade", foreignKey = @ForeignKey(name = "fk_mensalidadepagamento"))
    private Mensalidade mensalidade;

    @Column(name = "valor_pago")
    private double valorPago;

    @Column(name = "dt_criacao")
    private LocalDate dtCriacao;

    @Column(name = "dt_atualizacao")
    private LocalDate dtAtualizacao;

    @Enumerated(EnumType.STRING)
    private MeioPagamentoEnum meioPagamentoEnum;

    public Long getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(Long idPagamento) {
        this.idPagamento = idPagamento;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public LocalDate getDtCriacao() {
        return dtCriacao;
    }

    public void setDtCriacao(LocalDate dtCriacao) {
        this.dtCriacao = dtCriacao;
    }

    public LocalDate getDtAtualizacao() {
        return dtAtualizacao;
    }

    public void setDtAtualizacao(LocalDate dtAtualizacao) {
        this.dtAtualizacao = dtAtualizacao;
    }

    public MeioPagamentoEnum getMeioPagamentoEnum() {
        return meioPagamentoEnum;
    }

    public void setMeioPagamentoEnum(MeioPagamentoEnum meioPagamentoEnum) {
        this.meioPagamentoEnum = meioPagamentoEnum;
    }

    public Mensalidade getMensalidade() {
        return mensalidade;
    }

    public void setMensalidade(Mensalidade mensalidade) {
        this.mensalidade = mensalidade;
    }

    @PrePersist
    private void prePersist() {
        this.setDtCriacao(LocalDate.now());
    }

    @PreUpdate
    private void preUpdate() {
        this.setDtAtualizacao(LocalDate.now());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.idPagamento);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pagamento other = (Pagamento) obj;
        if (!Objects.equals(this.idPagamento, other.idPagamento)) {
            return false;
        }
        return true;
    }


//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Pagamento pagamento = (Pagamento) o;
//        return Double.compare(pagamento.valorPago, valorPago) == 0 &&
//                Objects.equals(idPagamento, pagamento.idPagamento) &&
//                Objects.equals(mensalidade, pagamento.mensalidade) &&
//                Objects.equals(dtCriacao, pagamento.dtCriacao) &&
//                Objects.equals(dtAtualizacao, pagamento.dtAtualizacao) &&
//                meioPagamentoEnum == pagamento.meioPagamentoEnum;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(idPagamento, mensalidade, valorPago, dtCriacao, dtAtualizacao, meioPagamentoEnum);
//    }

    @Override
    public String toString() {
        return "Pagamento{" +
                "idPagamento=" + idPagamento +
                ", mensalidade=" + mensalidade +
                ", valorPago=" + valorPago +
                ", dtCriacao=" + dtCriacao +
                ", dtAtualizacao=" + dtAtualizacao +
                ", meioPagamentoEnum=" + meioPagamentoEnum +
                '}';
    }
}
