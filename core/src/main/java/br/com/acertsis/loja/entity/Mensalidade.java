package br.com.acertsis.loja.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.*;

@Entity
public class Mensalidade implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mensalidade")
    private Long idMensalidade;
    private Integer parcela;
    @Column(name = "dt_criacao")
    private LocalDate dtCriacao;
    @Column(name = "dt_atualizacao")
    private LocalDate dtAtualizacao;
    @Column(name = "valor")
    private double valor;
    @Column(length = 30)
    @Enumerated(EnumType.STRING)
    private StatusPagamentoEnum status;
    @Column(name = "dt_vencimento")
    private LocalDate dtVencimento;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_Contrato", foreignKey = @ForeignKey(name = "fk_contratomensalidade"))
    private Contrato contrato;
    @Enumerated(EnumType.STRING)
    private MeioPagamentoEnum meioPagamento;

    @OneToOne()
    @JoinColumn(name = "pagamento_id")
    private Pagamento pagamento;

    public Mensalidade() {
    }

    public Mensalidade(Integer parcela, double valor, StatusPagamentoEnum status, LocalDate dtVencimento, Contrato contrato, MeioPagamentoEnum meioPagamento) {
        this.parcela = parcela;
        this.valor = valor;
        this.status = status;
        this.dtVencimento = dtVencimento;
        this.contrato = contrato;
        this.meioPagamento = meioPagamento;
    }

    public Long getIdMensalidade() {
        return idMensalidade;
    }

    public void setIdMensalidade(Long idMensalidade) {
        this.idMensalidade = idMensalidade;
    }

    public Integer getParcela() {
        return parcela;
    }

    public void setParcela(Integer parcela) {
        this.parcela = parcela;
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

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public StatusPagamentoEnum getStatus() {
        return status;
    }

    public void setStatus(StatusPagamentoEnum status) {
        this.status = status;
    }

    public LocalDate getDtVencimento() {
        return dtVencimento;
    }

    public void setDtVencimento(LocalDate dtVencimento) {
        this.dtVencimento = dtVencimento;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public MeioPagamentoEnum getMeioPagamento() {
        return meioPagamento;
    }

    public void setMeioPagamento(MeioPagamentoEnum meioPagamento) {
        this.meioPagamento = meioPagamento;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }


    @PrePersist
    private void salvarSetDtCriacao() {
        this.setDtCriacao(LocalDate.now());
    }

    @PreUpdate
    private void salvarDtAtualizacao() {
        this.setDtAtualizacao(LocalDate.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mensalidade that = (Mensalidade) o;
        return Double.compare(that.valor, valor) == 0 &&
                Objects.equals(idMensalidade, that.idMensalidade) &&
                Objects.equals(parcela, that.parcela) &&
                Objects.equals(dtCriacao, that.dtCriacao) &&
                Objects.equals(dtAtualizacao, that.dtAtualizacao) &&
                status == that.status &&
                Objects.equals(dtVencimento, that.dtVencimento) &&
                Objects.equals(contrato, that.contrato) &&
                meioPagamento == that.meioPagamento;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMensalidade, parcela, dtCriacao, dtAtualizacao, valor, status, dtVencimento, contrato, meioPagamento);
    }

    @Override
    public String toString() {
        return "Mensalidade{" +
                "idMensalidade=" + idMensalidade +
                ", parcela=" + parcela +
                ", dtCriacao=" + dtCriacao +
                ", dtAtualizacao=" + dtAtualizacao +
                ", valor=" + valor +
                ", status=" + status +
                ", dtVencimento=" + dtVencimento +
                ", meioPagameno=" + meioPagamento +
                '}';
    }
}
