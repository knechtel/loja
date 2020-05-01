/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.dto;

import br.com.acertsis.loja.entity.Cliente;
import br.com.acertsis.loja.entity.Endereco;
import br.com.acertsis.loja.entity.Pagamento;
import br.com.acertsis.loja.entity.PessoaEnum;
import br.com.acertsis.loja.entity.PessoaFisica;
import br.com.acertsis.loja.entity.PessoaJuridica;
import br.com.acertsis.loja.entity.Telefone;
import br.com.acertsis.loja.exception.BusinessException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

public class BoletoBancoDoBrasilDTO {

    private static DecimalFormat DECIMAl_FORMAT = new DecimalFormat("0000000000");

    public static enum BANCO_DO_BRASIL_OPERACOES {
        EMISSAO("2"),
        SEGUNDA_VIA("21");

        @Getter
        private String op;

        BANCO_DO_BRASIL_OPERACOES(String op) {
            this.op = op;
        }
    }

    @Getter
    @Setter
    private String idConv;
    @Getter
    @Setter
    private String refTran;

    @Getter
    @Setter
    private LocalDate dtVenc;

    @Getter
    @Setter
    private String valor;

    @Getter
    @Setter
    private String tpPagamento;

    @Getter
    @Setter
    private String cpfCnpj;

    @Getter
    @Setter
    private String indicadorPessoa;

    @Getter
    @Setter
    private String tpDuplicata = "DS";

    @Getter
    @Setter
    private String urlRetorno;

    @Getter
    @Setter
    private String nome;

    @Getter
    @Setter
    private String endereco;

    @Getter
    @Setter
    private String cidade;

    @Getter
    @Setter
    private String uf;

    @Getter
    @Setter
    private String cep;

    @Getter
    @Setter
    private String msgLoja;

    @Getter
    private String formato = "2";

    @Getter
    @Setter
    private String telefone;

    public static final String MONTAR_REF_TRAN(String numerosIniciais, long idPagamento) {
        return numerosIniciais.concat(DECIMAl_FORMAT.format(idPagamento));
    }

    public static final Long REVERTER_REF_TRAN(String numerosIniciais, String reftran) throws ParseException {
        return DECIMAl_FORMAT.parse(reftran.replace(numerosIniciais, "")).longValue();
    }

    private BoletoBancoDoBrasilDTO() {
    }


    private String formatValor(double d) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(d).replace(",", "");
    }

    private String stripChars(String chars) {
        if (chars != null) {
            return chars.replaceAll("[^\\d]+", "");
        }
        return null;
    }

    private String notNull(String valor) {
        if (valor == null) {
            return "";
        }
        return valor;
    }

    public static BoletoBancoDoBrasilDTO create(Cliente cliente, Pagamento pagamento, String numeroConvenio, String refTran, String msgLoja) throws BusinessException {
        if (Objects.isNull(pagamento)) {
            throw new BusinessException("pagamento é nulo.");
        }

        if (Objects.isNull(pagamento.getValorPago())) {
            throw new BusinessException("ValorPago é nulo.");
        }

        if (Objects.isNull(pagamento.getIdPagamento())) {
            throw new BusinessException("Id pagamento é nulo.");
        }

        if (Objects.isNull(numeroConvenio)) {
            throw new BusinessException("numeroConvenio é nulo.");
        }

        if (Objects.isNull(msgLoja)) {
            throw new BusinessException("msgLoja é nulo.");
        }

        if (Objects.isNull(cliente)) {
            throw new BusinessException("Cliente é nulo.");
        }
        if (Objects.isNull(cliente.getPessoa())) {
            throw new BusinessException("Cliente.pessoa é nulo.");
        }

        List<Endereco> enderecos = cliente.getPessoa().getEndereco();
        if (Objects.isNull(enderecos)) {
            throw new BusinessException("Endereco é nulo.");
        }

        if (enderecos.isEmpty()) {
            throw new BusinessException("Obrigatório ao menos um endereco.");
        }

        Endereco endereco = enderecos.get(0);

        BoletoBancoDoBrasilDTO boletoBB = new BoletoBancoDoBrasilDTO();
        boletoBB.setIdConv(numeroConvenio);

        List<Telefone> telefones = cliente.getPessoa().getTelefones();
        if (Objects.isNull(telefones)) {
            throw new BusinessException("telefones é nulo.");
        }

        if (telefones.isEmpty()) {
            throw new BusinessException("Obrigatório ao menos um telefone.");
        }

        Telefone telefone = telefones.get(0);
        if (Objects.isNull(telefone.getNumero())) {
            throw new BusinessException("telefone é nulo.");
        }
        if (Objects.isNull(endereco.getCep())) {
            throw new BusinessException("cep do endereço é nulo.");
        }
        boletoBB.setDtVenc(pagamento.getMensalidade().getDtVencimento());
        boletoBB.setTpPagamento(BoletoBancoDoBrasilDTO.BANCO_DO_BRASIL_OPERACOES.EMISSAO.getOp());
        boletoBB.setUrlRetorno("sucesso.php");
        boletoBB.setNome(cliente.getPessoa().getNome());
        boletoBB.setEndereco(boletoBB.notNull(endereco.getRua()));
        boletoBB.setCidade(boletoBB.notNull(endereco.getCidade()));
        boletoBB.setUf(boletoBB.notNull(endereco.getEstado()));
        boletoBB.setCep(boletoBB.notNull(boletoBB.stripChars(endereco.getCep())));
        boletoBB.setMsgLoja(msgLoja);
        boletoBB.setTelefone(boletoBB.notNull(boletoBB.stripChars(telefone.getNumero())));
        if (PessoaEnum.FISICA.equals(cliente.getPessoa().getTipoPessoa())) {
            boletoBB.setIndicadorPessoa("1");
            boletoBB.setCpfCnpj(boletoBB.stripChars(((PessoaFisica) cliente.getPessoa()).getCpf()));
        }
        if (PessoaEnum.JURIDICA.equals(cliente.getPessoa().getTipoPessoa())) {
            boletoBB.setIndicadorPessoa("2");
            boletoBB.setCpfCnpj(boletoBB.stripChars(((PessoaJuridica) cliente.getPessoa()).getCnpj()));
        }
        boletoBB.setValor(boletoBB.formatValor(pagamento.getValorPago()));

        boletoBB.setRefTran(refTran);

        return boletoBB;
    }
}
