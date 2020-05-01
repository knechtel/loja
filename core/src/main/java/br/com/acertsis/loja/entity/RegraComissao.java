package br.com.acertsis.loja.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.*;

@Entity
public class RegraComissao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_regra_comissao")
    private Long idRegraComissao;

    @ManyToOne()
    @JoinColumn(name = "parceiro_id", foreignKey = @ForeignKey(name = "fk_parceirocomissao"))
    private Parceiro parceiro;

    @Column(name = "tx_adesao")
    private double txAdesao;

    private double porcentagem;

    private double valor;

    @Column(name = "dt_inicial")
    private LocalDate dtInicial;

    @Column(name = "dt_fim")
    private LocalDate dtFinal;

    @Column(name = "dt_criacao")
    private LocalDate dtCriacao;

    @Column(name = "dt_atualizacao")
    private LocalDate dtAtualizacao;

    @Column(name = "habilitado")
    private boolean habilitado;

    @Enumerated(EnumType.STRING)
    @Column(name = "modo_comissao_parceiro")
    private ModoComissaoParceiroEnum modoComissaoParceiro;

    public Long getIdRegraComissao() {
        return idRegraComissao;
    }

    public void setIdRegraComissao(Long idRegraComissao) {
        this.idRegraComissao = idRegraComissao;
    }

    public Parceiro getParceiro() {
        return parceiro;
    }

    public void setParceiro(Parceiro parceiro) {
        this.parceiro = parceiro;
    }

    public double getTxAdesao() {
        return txAdesao;
    }

    public void setTxAdesao(double txAdesao) {
        this.txAdesao = txAdesao;
    }

    public double getPorcentagem() {
        return porcentagem;
    }

    public void setPorcentagem(double porcentagem) {
        this.porcentagem = porcentagem;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDate getDtInicial() {
        return dtInicial;
    }

    public void setDtInicial(LocalDate dtInicial) {
        this.dtInicial = dtInicial;
    }

    public LocalDate getDtFinal() {
        return dtFinal;
    }

    public void setDtFinal(LocalDate dtFinal) {
        this.dtFinal = dtFinal;
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

    public ModoComissaoParceiroEnum getModoComissaoParceiro() {
        return modoComissaoParceiro;
    }

    public void setModoComissaoParceiro(ModoComissaoParceiroEnum modoComissaoParceiro) {
        this.modoComissaoParceiro = modoComissaoParceiro;
    }

    @PrePersist
    private void salvarSetDtCriacao() {
        this.setDtCriacao(LocalDate.now());
    }

    @PreUpdate
    private void salvarDtAtualizacao() {
        this.setDtAtualizacao(LocalDate.now());
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.idRegraComissao);
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
        final RegraComissao other = (RegraComissao) obj;
        if (!Objects.equals(this.idRegraComissao, other.idRegraComissao)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RegraComissao{"
                + "idRegraComissao=" + idRegraComissao
                + ", parceiro=" + parceiro
                + ", txAdesao=" + txAdesao
                + ", porcentagem=" + porcentagem
                + ", valor=" + valor
                + ", dtInicial=" + dtInicial
                + ", dtFinal=" + dtFinal
                + ", dtCriacao=" + dtCriacao
                + ", dtAtualizacao=" + dtAtualizacao
                + '}';
    }
}
