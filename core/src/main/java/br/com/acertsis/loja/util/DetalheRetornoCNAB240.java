package br.com.acertsis.loja.util;

public class DetalheRetornoCNAB240 {
    private DetalheSegmentoTRetornoCNAB240 segmentoT = new DetalheSegmentoTRetornoCNAB240();
    private DetalheSegmentoURetornoCNAB240 segmentoU = new DetalheSegmentoURetornoCNAB240();
    private DetalheSegmentoWRetornoCNAB240 segmentoW = new DetalheSegmentoWRetornoCNAB240();
    private HeaderDeArquivoCNAB240 headerArquivo = new HeaderDeArquivoCNAB240();

    public DetalheRetornoCNAB240() {
    }

    public DetalheRetornoCNAB240(DetalheSegmentoTRetornoCNAB240 segmentoT) {
        this.segmentoT = segmentoT;
    }

    public DetalheRetornoCNAB240(DetalheSegmentoURetornoCNAB240 segmentoU) {
        this.segmentoU = segmentoU;
    }

    public DetalheRetornoCNAB240(DetalheSegmentoWRetornoCNAB240 segmentoW) {
        this.segmentoW = segmentoW;
    }

    public DetalheRetornoCNAB240(DetalheSegmentoTRetornoCNAB240 segmentoT, DetalheSegmentoURetornoCNAB240 segmentoU) {
        this.segmentoT = segmentoT;
        this.segmentoU = segmentoU;
    }

    public DetalheSegmentoTRetornoCNAB240 getSegmentoT() {
        return segmentoT;
    }

    public void setSegmentoT(DetalheSegmentoTRetornoCNAB240 segmentoT) {
        this.segmentoT = segmentoT;
    }

    public DetalheSegmentoURetornoCNAB240 getSegmentoU() {
        return segmentoU;
    }

    public void setSegmentoU(DetalheSegmentoURetornoCNAB240 segmentoU) {
        this.segmentoU = segmentoU;
    }

    public DetalheSegmentoWRetornoCNAB240 getSegmentoW() {
        return segmentoW;
    }

    public void setSegmentoW(DetalheSegmentoWRetornoCNAB240 segmentoW) {
        this.segmentoW = segmentoW;
    }

    public HeaderDeArquivoCNAB240 getHeaderArquivo() {
        return headerArquivo;
    }

    public void setHeaderArquivo(HeaderDeArquivoCNAB240 headerArquivo) {
        this.headerArquivo = headerArquivo;
    }


}
