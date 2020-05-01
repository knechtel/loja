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
import javax.validation.constraints.Email;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pessoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pessoa")
    private Long idPessoa;
    @Email(message = "Digite um email válido.")
    protected String email;
    @Column(name = "dt_criacao")
    private LocalDate dtCriacao;
    @Column(name = "dt_ataulizacao")
    private LocalDate dtAtualizacao;
    @Enumerated(EnumType.STRING)
    private PessoaEnum tipoPessoa;
    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "pessoa")
    private List<Endereco> endereco;
    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "pessoa")
    private List<Telefone> telefones;

    public Long getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Long idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public PessoaEnum getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(PessoaEnum tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public List<Endereco> getEndereco() {
        return endereco;
    }

    public void setEndereco(List<Endereco> endereco) {
        this.endereco = endereco;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }

    @PrePersist
    private void salvarSetDtCriacao() {
        this.setDtCriacao(LocalDate.now());
    }

    @PreUpdate
    private void salvarDtAtualizacao() {
        this.setDtAtualizacao(LocalDate.now());
    }

    public abstract String getNome();

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.idPessoa);
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
        final Pessoa other = (Pessoa) obj;
        if (!Objects.equals(this.idPessoa, other.idPessoa)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pessoa{"
                + "idPessoa=" + idPessoa
                + ", email='" + email + '\''
                + ", dtCriacao=" + dtCriacao
                + ", dtAtualizacao=" + dtAtualizacao
                + ", tipoPessoa=" + tipoPessoa
                + '}';
    }
}
