package br.com.acertsis.loja.dto;

import br.com.acertsis.loja.entity.BrandCardEnum;
import java.io.Serializable;

public class CartaoCreditoDTO implements Serializable {

    private String numero;
    private String titular;
    private String mesVencimento;
    private String anoVencimento;
    private String codSeguranca;
    private BrandCardEnum bandeira;

    public CartaoCreditoDTO() {
    }

    public CartaoCreditoDTO(String numero, String titular, String mesVencimento, String anoVencimento, String codSeguranca, BrandCardEnum bandeira) {
        this.numero = numero;
        this.titular = titular;
        this.mesVencimento = mesVencimento;
        this.anoVencimento = anoVencimento;
        this.codSeguranca = codSeguranca;
        this.bandeira = bandeira;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getMesVencimento() {
        return mesVencimento;
    }

    public void setMesVencimento(String mesVencimento) {
        this.mesVencimento = mesVencimento;
    }

    public String getAnoVencimento() {
        return anoVencimento;
    }

    public void setAnoVencimento(String anoVencimento) {
        this.anoVencimento = anoVencimento;
    }

    public String getCodSeguranca() {
        return codSeguranca;
    }

    public void setCodSeguranca(String codSeguranca) {
        this.codSeguranca = codSeguranca;
    }


    public BrandCardEnum getBandeira() {
        return bandeira;
    }

    public void setBandeira(BrandCardEnum bandeira) {
        this.bandeira = bandeira;
    }
}
