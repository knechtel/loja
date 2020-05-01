package br.com.acertsis.loja.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import com.google.common.base.Strings;

public class ArquivoRetornoCNAB240 {

    private FileReader arquivo;// = new BufferedReader(new FileReader("/home/fernando/Downloads/Boletos09082019.ret"));

    private List<DetalheRetornoCNAB240> listaDetalhes = new ArrayList<DetalheRetornoCNAB240>();

    private final List<LinhaDeArquivoLidaArgs> linhaDeArquivoLida = new ArrayList<>();

    public FileReader getArquivo() {
        return arquivo;
    }

    public void setArquivo(FileReader arquivo) {
        this.arquivo = arquivo;
    }

    public List<DetalheRetornoCNAB240> getlistaDetalhes() {
        if(this.listaDetalhes.isEmpty() || this.listaDetalhes == null){
            this.listaDetalhes = new ArrayList<>();
        }

        return listaDetalhes;
    }

    public void setlistaDetalhes(List<DetalheRetornoCNAB240> listaDetalhes) {
        this.listaDetalhes = listaDetalhes;
    }

    public ArquivoRetornoCNAB240() {
    }

    public ArquivoRetornoCNAB240(FileReader brArquivo) {
        this.arquivo = brArquivo;
    }

    public void LerArquivoRetorno() {
        LerArquivoRetorno(this.arquivo);
    }

    //@Override
    public void LerArquivoRetorno(FileReader arquivo) {


        try {
            BufferedReader bufferedReader = new BufferedReader(arquivo);
            String linha = "";


            String teste = null;
            while ((linha = bufferedReader.readLine()) != null) {
                if (!Strings.isNullOrEmpty(linha)) {

                    DetalheRetornoCNAB240 detalheRetorno = new DetalheRetornoCNAB240();


                    switch (linha.substring(7, 8)) {
                        case "0": //Header de arquivo
                            detalheRetorno.getHeaderArquivo().LerHeaderDeArquivoCNAB240(linha);
                            //listaDetalhes.add(detalheRetorno);
                            break;
                        case "1": //Header de lote
                            //OnLinhaLida(null, linha, EnumTipodeLinhaLida.HeaderDeLote);
                            break;
                        case "3": //Detalhe
                            teste = linha.substring(13, 14);
                            if (linha.substring(13, 14).equalsIgnoreCase( "W"))
                            {
                              //  OnLinhaLida(detalheRetorno, linha, EnumTipodeLinhaLida.DetalheSegmentoW);
                                detalheRetorno.getSegmentoW().LerDetalheSegmentoWRetornoCNAB240(linha);
                            }
                            /*else if (linha.substring(13, 1) == "E")
                            {
                                OnLinhaLida(detalheRetorno, linha, EnumTipodeLinhaLida.DetalheSegmentoE);
                                detalheRetorno.SegmentoE = new DetalheSegmentoERetornoCNAB240();
                                detalheRetorno.SegmentoE.LerDetalheSegmentoERetornoCNAB240(linha);
                            }*/
                            else if (linha.substring(13, 14).equalsIgnoreCase("T")) {
                                //Irá ler o Segmento T e em sequencia o Segmento U
                                detalheRetorno.getSegmentoT().LerDetalheSegmentoTRetornoCNAB240(linha);
                                linha = bufferedReader.readLine();
                                detalheRetorno.getSegmentoU().LerDetalheSegmentoURetornoCNAB240(linha);

                                //OnLinhaLida(detalheRetorno, linha, EnumTipodeLinhaLida.DetalheSegmentoU);

                            }
                            listaDetalhes.add(detalheRetorno);
                            break;
                    }
                }

            }
            bufferedReader.close();
        }
        catch (Exception ex) {
           ex.printStackTrace();
        }
    }
}
