package br.com.acertsis.loja.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
//@IdClass(ContratoId.class)
public class Contrato implements Serializable {

    @Id
    @GenericGenerator(name = "sequence_id_contrato", strategy = "br.com.acertsis.loja.entity.GeradorNumContrato")
    @GeneratedValue(generator = "sequence_id_contrato")
    @Column(name = "id_contrato")
    private Long idContrato;
    @ManyToOne
    @JoinColumn(name = "id_regra_comissao", foreignKey = @ForeignKey(name = "fk_regracomissaocontrato"))
    private RegraComissao regraComissao;
    @ManyToOne
    @JoinColumn(name = "id_plano", foreignKey = @ForeignKey(name = "fk_planocontrato"))
    private Plano plano;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_cliente", foreignKey = @ForeignKey(name = "fk_clientecontrato"))
    private Cliente cliente;
    @Column(name = "dt_inicio")
    private LocalDate dtInicio;
    @Column(name = "dt_fim")
    private LocalDate dtFim;
    @Column(name = "dt_criacao")
    private LocalDate dtCriacao;
    @Column(name = "dt_atualizacao")
    private LocalDate dtAtualizacao;
    @Column(name = "valor_total")
    private double valorTotal;
    @Column(name = "num_parcelas")
    private Integer numParcelas;
    @Column(name = "tipo_pagamento")
    @Enumerated(EnumType.STRING)
    private MeioPagamentoEnum tipoPagamento;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "contrato")
    public List<Mensalidade> mensalidades;
    @Enumerated(EnumType.STRING)
    private StatusContratoEnum status;
    @OneToOne
    @JoinColumn(name = "dadosRecorrencia_id", foreignKey = @ForeignKey(name = "fk_dadosRecorrencia_contrato"))
    private DadosRecorrencia dadosRecorrencia;

    public Long getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Long idContrato) {
        this.idContrato = idContrato;
    }

    public RegraComissao getRegraComissao() {
        return regraComissao;
    }

    public void setRegraComissao(RegraComissao regraComissao) {
        this.regraComissao = regraComissao;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(LocalDate dtInicio) {
        this.dtInicio = dtInicio;
    }

    public LocalDate getDtFim() {
        return dtFim;
    }

    public void setDtFim(LocalDate dtFim) {
        this.dtFim = dtFim;
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

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Integer getNumParcelas() {
        return numParcelas;
    }

    public void setNumParcelas(Integer numParcelas) {
        this.numParcelas = numParcelas;
    }

    public MeioPagamentoEnum getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(MeioPagamentoEnum tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public List<Mensalidade> getMensalidades() {
        return mensalidades;
    }

    public void setMensalidades(List<Mensalidade> mensalidades) {
        this.mensalidades = mensalidades;
    }

    public StatusContratoEnum getStatus() {
        return status;
    }

    public void setStatus(StatusContratoEnum status) {
        this.status = status;
    }

    public DadosRecorrencia getDadosRecorrencia() {
        return dadosRecorrencia;
    }

    public void setDadosRecorrencia(DadosRecorrencia dadosRecorrencia) {
        this.dadosRecorrencia = dadosRecorrencia;
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
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.idContrato);
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
        final Contrato other = (Contrato) obj;
        if (!Objects.equals(this.idContrato, other.idContrato)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Contrato{"
                + "idContrato=" + idContrato
                + ", regraComissao=" + regraComissao
                + ", plano=" + plano
                + ", cliente=" + cliente
                + ", dtInicio=" + dtInicio
                + ", dtFim=" + dtFim
                + ", dtCriacao=" + dtCriacao
                + ", dtAtualizacao=" + dtAtualizacao
                + ", valorTotal=" + valorTotal
                + ", numParcelas=" + numParcelas
                + ", tipoPagamento=" + tipoPagamento
                + ", mensalidades=" + mensalidades
                + ", status=" + status
                + '}';
    }
}
