/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

@Entity
public class Parceiro implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parceiro")
    private Long idParceiro;

    @OneToOne(cascade = { CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "id_pessoa", foreignKey = @ForeignKey(name = "fk_pessoaparceiro"))
    private Pessoa pessoa;

    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "id_responsavel", foreignKey = @ForeignKey(name = "fk_responsavelparceiro"))
    private PessoaFisica responsavel;

    @Column(name = "dt_solicitacao")
    private LocalDate dtSolicitacao;

    @Column(name = "dt_liberacao")
    private LocalDate dtLiberacao;

    @Column(name = "dt_atualizacao")
    private LocalDate dtAtualizacao;

    @Enumerated(EnumType.STRING)
    private StatusParceiroEnum status;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pessoa")
    private PessoaEnum tipoPessoa;

    @Column(name = "sku")
    private Integer sku;

    private boolean habilitado;

    @OneToMany(mappedBy = "parceiro")
    private List<RegraComissao> regrasComissaos;

    private boolean raiz;

    private String dominio;

    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "referencia_bancaria_id", foreignKey = @ForeignKey(name = "fk_referenciabancaria"))
    private ReferenciaBancaria referenciaBancaria;

    @Column(name = "obsevacao_solicitacao_parceria")
    private String obsevacaoSolicitacaoParceria;

    private String ufCrc;

    private String crc;

    public Long getIdParceiro() {
        return idParceiro;
    }

    public void setIdParceiro(Long idParceiro) {
        this.idParceiro = idParceiro;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public PessoaFisica getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(PessoaFisica responsavel) {
        this.responsavel = responsavel;
    }

    public LocalDate getDtSolicitacao() {
        return dtSolicitacao;
    }

    public void setDtSolicitacao(LocalDate dtSolicitacao) {
        this.dtSolicitacao = dtSolicitacao;
    }

    public LocalDate getDtLiberacao() {
        return dtLiberacao;
    }

    public void setDtLiberacao(LocalDate dtLiberacao) {
        this.dtLiberacao = dtLiberacao;
    }

    public LocalDate getDtAtualizacao() {
        return dtAtualizacao;
    }

    public void setDtAtualizacao(LocalDate dtAtualizacao) {
        this.dtAtualizacao = dtAtualizacao;
    }

    public Integer getSku() {
        return sku;
    }

    public void setSku(Integer sku) {
        this.sku = sku;
    }

    public StatusParceiroEnum getStatus() {
        return status;
    }

    public void setStatus(StatusParceiroEnum status) {
        this.status = status;
    }

    public PessoaEnum getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(PessoaEnum tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public List<RegraComissao> getRegrasComissaos() {
        return regrasComissaos;
    }

    public void setRegrasComissaos(List<RegraComissao> regrasComissaos) {
        this.regrasComissaos = regrasComissaos;
    }

    public boolean isRaiz() {
        return raiz;
    }

    public void setRaiz(boolean raiz) {
        this.raiz = raiz;
    }

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    public ReferenciaBancaria getReferenciaBancaria() {
        return referenciaBancaria;
    }

    public void setReferenciaBancaria(ReferenciaBancaria referenciaBancaria) {
        this.referenciaBancaria = referenciaBancaria;
    }

    public String getObsevacaoSolicitacaoParceria() {
        return obsevacaoSolicitacaoParceria;
    }

    public void setObsevacaoSolicitacaoParceria(String obsevacaoSolicitacaoParceria) {
        this.obsevacaoSolicitacaoParceria = obsevacaoSolicitacaoParceria;
    }

    public String getCrc() {
        return crc;
    }

    public void setCrc(String crc) {
        this.crc = crc;
    }

    public String getUfCrc() {
        return ufCrc;
    }

    public void setUfCrc(String ufCrc) {
        this.ufCrc = ufCrc;
    }


    @PrePersist
    private void salvarSetDtSolicitacao() {
        this.setDtSolicitacao(LocalDate.now());
    }

    @PreUpdate
    private void salvarDtAtualizacao() {
        this.setDtAtualizacao(LocalDate.now());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.idParceiro);
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
        final Parceiro other = (Parceiro) obj;
        if (!Objects.equals(this.idParceiro, other.idParceiro)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Parceiro{"
                + "idParceiro=" + idParceiro
                + ", pessoa=" + pessoa
                + ", responsavel=" + responsavel
                + ", dtSolicitacao=" + dtSolicitacao
                + ", dtLiberacao=" + dtLiberacao
                + ", dtAtualizacao=" + dtAtualizacao
                + ", status=" + status
                + ", tipoPessoa=" + tipoPessoa
                + ", sku=" + sku
                + ", habilitado=" + habilitado
                + ", regrasComissaos=" + regrasComissaos
                + ", raiz=" + raiz
                + ", dominio='" + dominio + '\''
                + '}';
    }
}
