/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 *
 *
 */
@Entity
@PrimaryKeyJoinColumn(name="id_pessoa", foreignKey = @ForeignKey(name = "fk_pessoafisica"))
public class PessoaFisica extends Pessoa {

    @Column(unique = true, name = "cpf")
    private String cpf;

    protected String nome;

    @Column(name = "dt_nascimento")
    private LocalDate dtNascimento;

    public String getCpf() {return cpf;}

    public void setCpf(String cpf) {this.cpf = cpf;}

    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}

    public LocalDate getDtNascimento() {return dtNascimento;}

    public void setDtNascimento(LocalDate dtNascimento) {

        this.dtNascimento = dtNascimento;
    }

    @Override
    public String toString() {
        return "PessoaFisica{" + "cpf=" + cpf + ", nome=" + nome + ", dtNascimento=" + dtNascimento + '}';
    }
}
