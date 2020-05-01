package br.com.acertsis.loja.util;

public class LinhaDeArquivoLidaArgs {
    private String linha;
    private Object detalhe;
    private EnumTipodeLinhaLida tipoLinha;

    public LinhaDeArquivoLidaArgs(Object detalhe, String linha) {
        try {
            linha = linha;
            detalhe = detalhe;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public LinhaDeArquivoLidaArgs(Object detalhe, String linha, EnumTipodeLinhaLida tipoLinha) {
        try {
            linha = linha;
            detalhe = detalhe;
            tipoLinha = tipoLinha;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getLinha() {
        return linha;
    }

    public void setLinha(String linha) {
        this.linha = linha;
    }

    public Object getDetalhe() {
        return detalhe;
    }

    public void setDetalhe(Object detalhe) {
        this.detalhe = detalhe;
    }

}
