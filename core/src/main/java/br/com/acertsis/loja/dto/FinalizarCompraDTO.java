/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.dto;

import br.com.acertsis.loja.entity.MeioPagamentoEnum;
import br.com.acertsis.loja.entity.Parceiro;
import br.com.acertsis.loja.entity.PessoaEnum;
import br.com.acertsis.loja.entity.Plano;
import java.time.LocalDate;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class FinalizarCompraDTO {

    private MeioPagamentoEnum meioPagamento;

    //detalhes do plano
    private Plano planoSelecionado;

    private PessoaEnum pessoaEnum;

    private Parceiro parceiro;

    private Double valor;

    private LocalDate dataCompra;

    private boolean parceiroAcertsis;

    private String usuarioParceiro;

    private String senhaParceiro;
    //fim detalhes do plano

    private String nome;

    private String telefone;
    @Email(message = "Digite um email válido.")
    private String email;

    private LocalDate datanasc;

    private String cpf;

    private String cnpj;

    private String razaoSocial;

    private String cep;

    private String endereco;

    private String numero;

    private String complemento;

    private String bairro;

    private String estado;

    private String cidade;

    private String bandeira;

    private String numeroCartao;

    private String titularCartao;

    private Integer codigoSegCartao;

    private Integer mesVencimentoCartao;

    private Integer anoVencimentoCartao;

    private Integer parcelas;

    private String disclaimer;

    private boolean disclaimerAceito;

    private LocalDate dataVencimento;

    public Plano getPlanoSelecionado() {
        return planoSelecionado;
    }

}
