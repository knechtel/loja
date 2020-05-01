/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.*;

@Entity
public class ReferenciaBancaria implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_referenia_bancaria")
    private Long idReferenciaBancaria;

    @OneToOne
    @JoinColumn(name="id_pessoa", foreignKey = @ForeignKey(name = "fk_pessoareferencia"))
    private Pessoa pessoa;

    private String conta;

    private String agencia;

    private String titular;

    private String cpfCnpj;

    private String operacao;
    @Column(name="dt_criacao")

    private LocalDate dtCriacao;

    @Column(name = "dt_atualizacao")
    private LocalDate dtAtualizacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_conta_bancaria_enum")
    private TipoContaBancariaEnum tipoContaBancariaEnum;

    @ManyToOne
    @JoinColumn(name = "banco")
    private Banco banco;


    public Long getIdReferenciaBancaria() {

        return idReferenciaBancaria;
    }

    public void setIdReferenciaBancaria(Long idReferenciaBancaria) {
        this.idReferenciaBancaria = idReferenciaBancaria;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
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

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public TipoContaBancariaEnum getTipoContaBancariaEnum() {
        return tipoContaBancariaEnum;
    }

    public void setTipoContaBancariaEnum(TipoContaBancariaEnum tipoContaBancariaEnum) {
        this.tipoContaBancariaEnum = tipoContaBancariaEnum;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
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
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.idReferenciaBancaria);
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
        final ReferenciaBancaria other = (ReferenciaBancaria) obj;
        if (!Objects.equals(this.idReferenciaBancaria, other.idReferenciaBancaria)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ReferenciaBancaria{" +
                "idReferenciaBancaria=" + idReferenciaBancaria +
                ", pessoa=" + pessoa +
                ", banco='" + banco + '\'' +
                ", conta='" + conta + '\'' +
                ", agencia='" + agencia + '\'' +
                ", titular='" + titular + '\'' +
                ", operacao='" + operacao + '\'' +
                ", dtCriacao=" + dtCriacao +
                ", dtAtualizacao=" + dtAtualizacao +
                '}';
    }
}
