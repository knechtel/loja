package br.com.acertsis.loja.util;

public enum EnumTipodeLinhaLida {
    HeaderDeArquivo(1),
    HeaderDeLote(2),
    DetalheSegmentoT(3),
    DetalheSegmentoU(4),
    TraillerDeLote(5),
    TraillerDeArquivo(6),
    DetalheSegmentoW(7),
    DetalheSegmentoE(8);

    private int tipo;

    private EnumTipodeLinhaLida(int tipo) {this.tipo = tipo;}

}
