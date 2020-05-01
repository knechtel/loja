package br.com.acertsis.loja.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import com.google.common.base.Strings;

public class DetalheSegmentoURetornoCNAB240 {

    Double ServicoCodigoMovimentoRetorno;
    Double jurosMultaEncargos;
    Double valorDescontoConcedido;
    Double valorAbatimentoConcedido;
    Double valorIOFRecolhido;
    Double valorPagoPeloSacado;
    Double valorLiquidoASerCreditado;
    Double valorOutrasDespesas;
    Double valorOutrosCreditos;
    LocalDate dataOcorrencia;
    LocalDate dataCredito;
    String codigoOcorrencia;
    String codigoOcorrenciaSacado;
    LocalDate dataOcorrenciaSacado;
    Double valorOcorrenciaSacado;
    String registro;

    private List<DetalheSegmentoURetornoCNAB240> listaDetalhe = new ArrayList<DetalheSegmentoURetornoCNAB240>();

    public DetalheSegmentoURetornoCNAB240(String registro) {
        this.registro = registro;
    }

    public DetalheSegmentoURetornoCNAB240() {
    }

    public Double getServicoCodigoMovimentoRetorno() {
        return ServicoCodigoMovimentoRetorno;
    }

    public void setServicoCodigoMovimentoRetorno(Double servicoCodigoMovimentoRetorno) {
        ServicoCodigoMovimentoRetorno = servicoCodigoMovimentoRetorno;
    }

    public Double getJurosMultaEncargos() {
        return jurosMultaEncargos;
    }

    public void setJurosMultaEncargos(Double jurosMultaEncargos) {
        this.jurosMultaEncargos = jurosMultaEncargos;
    }

    public Double getValorDescontoConcedido() {
        return valorDescontoConcedido;
    }

    public void setValorDescontoConcedido(Double valorDescontoConcedido) {
        this.valorDescontoConcedido = valorDescontoConcedido;
    }

    public Double getValorAbatimentoConcedido() {
        return valorAbatimentoConcedido;
    }

    public void setValorAbatimentoConcedido(Double valorAbatimentoConcedido) {
        this.valorAbatimentoConcedido = valorAbatimentoConcedido;
    }

    public Double getValorIOFRecolhido() {
        return valorIOFRecolhido;
    }

    public void setValorIOFRecolhido(Double valorIOFRecolhido) {
        this.valorIOFRecolhido = valorIOFRecolhido;
    }

    public Double getValorPagoPeloSacado() {
        return valorPagoPeloSacado;
    }

    public void setValorPagoPeloSacado(Double valorPagoPeloSacado) {
        this.valorPagoPeloSacado = valorPagoPeloSacado;
    }

    public Double getValorLiquidoASerCreditado() {
        return valorLiquidoASerCreditado;
    }

    public void setValorLiquidoASerCreditado(Double valorLiquidoASerCreditado) {
        this.valorLiquidoASerCreditado = valorLiquidoASerCreditado;
    }

    public Double getValorOutrasDespesas() {
        return valorOutrasDespesas;
    }

    public void setValorOutrasDespesas(Double valorOutrasDespesas) {
        this.valorOutrasDespesas = valorOutrasDespesas;
    }

    public Double getValorOutrosCreditos() {
        return valorOutrosCreditos;
    }

    public void setValorOutrosCreditos(Double valorOutrosCreditos) {
        this.valorOutrosCreditos = valorOutrosCreditos;
    }

    public LocalDate getDataOcorrencia() {
        return dataOcorrencia;
    }

    public void setDataOcorrencia(LocalDate dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
    }

    public LocalDate getDataCredito() {
        return dataCredito;
    }

    public void setDataCredito(LocalDate dataCredito) {
        this.dataCredito = dataCredito;
    }

    public String getCodigoOcorrencia() {
        return codigoOcorrencia;
    }

    public void setCodigoOcorrencia(String codigoOcorrencia) {
        this.codigoOcorrencia = codigoOcorrencia;
    }

    public String getCodigoOcorrenciaSacado() {
        return codigoOcorrenciaSacado;
    }

    public void setCodigoOcorrenciaSacado(String codigoOcorrenciaSacado) {
        this.codigoOcorrenciaSacado = codigoOcorrenciaSacado;
    }

    public LocalDate getDataOcorrenciaSacado() {
        return dataOcorrenciaSacado;
    }

    public void setDataOcorrenciaSacado(LocalDate dataOcorrenciaSacado) {
        this.dataOcorrenciaSacado = dataOcorrenciaSacado;
    }

    public Double getValorOcorrenciaSacado() {
        return valorOcorrenciaSacado;
    }

    public void setValorOcorrenciaSacado(Double valorOcorrenciaSacado) {
        this.valorOcorrenciaSacado = valorOcorrenciaSacado;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public List<DetalheSegmentoURetornoCNAB240> getListaDetalhe() {
        if(this.listaDetalhe.isEmpty() || this.listaDetalhe == null){
            listaDetalhe = new ArrayList<>();
        }
        return listaDetalhe;
    }

    public void setListaDetalhe(List<DetalheSegmentoURetornoCNAB240> listaDetalhe) {
        this.listaDetalhe = listaDetalhe;
    }

    public void LerDetalheSegmentoURetornoCNAB240(String registro) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try {
            this.registro = registro;
    
            if (!registro.substring(13, 14).equalsIgnoreCase( "U")) {
                throw new Exception("Registro inválido. O detalhe não possuí as características do segmento U.");
            }
    
            String dataOcorrenciaSacado = "";
            if (!registro.substring(153, 157).equalsIgnoreCase("    ")) {
                dataOcorrenciaSacado = registro.substring(157, 165);
            }
    
            this.codigoOcorrencia = registro.substring(15, 17);
    
            Double jurosMultaEncargos = Double.parseDouble(registro.substring(17, 32));
            this.jurosMultaEncargos = jurosMultaEncargos / 100;
            Double valorDescontoConcedido = Double.parseDouble(registro.substring(32, 47));
            this.valorDescontoConcedido = valorDescontoConcedido / 100;
            Double valorAbatimentoConcedido = Double.parseDouble(registro.substring(47, 62));
            this.valorAbatimentoConcedido = valorAbatimentoConcedido / 100;
            Double valorIOFRecolhido = Double.parseDouble(registro.substring(62, 77));
            this.valorIOFRecolhido = valorIOFRecolhido / 100;
            Double valorPagoPeloSacado = Double.parseDouble(registro.substring(77, 92));
            this.valorPagoPeloSacado = valorPagoPeloSacado / 100;
            Double valorLiquidoASerCreditado = Double.parseDouble(registro.substring(92, 107));
            this.valorLiquidoASerCreditado = valorLiquidoASerCreditado / 100;
            Double valorOutrasDespesas = Double.parseDouble(registro.substring(107, 122));
            this.valorOutrasDespesas = valorOutrasDespesas / 100;
            Double valorOutrosCreditos = Double.parseDouble(registro.substring(122, 137));
            this.valorOutrosCreditos = valorOutrosCreditos / 100;
            String dataOcorrencia = Util.formataData(registro.substring(137, 145));
            this.dataOcorrencia = LocalDate.parse(dataOcorrencia,formatter);
            String dataCredito = Util.formataData(registro.substring(145, 153));
             if (!Strings.isNullOrEmpty(dataCredito)  && (!dataCredito.equalsIgnoreCase("00-00-0000"))) {
                    this.dataCredito = LocalDate.parse(dataCredito,formatter);
            }
    
    
            this.codigoOcorrenciaSacado = registro.substring(153, 157);
            if (!Strings.isNullOrEmpty(dataOcorrenciaSacado) && (!dataOcorrenciaSacado.equalsIgnoreCase("00-00-0000"))) {
                this.dataOcorrenciaSacado = LocalDate.parse(dataOcorrenciaSacado,formatter);
            }
            Double valorOcorrenciaSacado = Double.parseDouble(registro.substring(165, 180));
            this.valorOcorrenciaSacado = valorOcorrenciaSacado / 100;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
