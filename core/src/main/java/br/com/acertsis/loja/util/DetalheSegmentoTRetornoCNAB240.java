package br.com.acertsis.loja.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DetalheSegmentoTRetornoCNAB240 {

    private Integer codigoBanco;
    private Integer idCodigoMovimento;
    private Integer agencia;
    private String digitoAgencia;
    private Long conta;
    private String digitoConta;
    private Integer dacAgConta;
    private String nossoNumero; //identificação do título no banco
    private Integer codigoCarteira;
    private String numeroDocumento; //número utilizado pelo cliente para a identificação do título
    private LocalDate dataVencimento;
    private Double valorTitulo;
    private String identificacaoTituloEmpresa;
    private Integer tipoInscricao;
    private String numeroInscricao;
    private String nomeSacado;
    private Double valorTarifas;
    private String codigoRejeicao;
    private String registro;
    private String usoFebraban;

    private List<DetalheSegmentoTRetornoCNAB240> listaDetalhe = new ArrayList<DetalheSegmentoTRetornoCNAB240>();

    public void LerDetalheSegmentoTRetornoCNAB240(String registro) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try {
            registro = registro;

            if (!registro.substring(13, 14).equalsIgnoreCase("T")) {
                throw new Exception("Registro inválido. O detalhe não possuí as características do segmento T.");
            }

            this.codigoBanco = Integer.parseInt(registro.substring(0, 3));
            this.idCodigoMovimento = Integer.parseInt(registro.substring(16, 17));
            this.agencia = Integer.parseInt(registro.substring(17, 21));
            this.digitoAgencia = registro.substring(21, 22);
            this.conta = Long.parseLong(registro.substring(23, 35));
            this.digitoConta = registro.substring(35, 36);

            this.nossoNumero = registro.substring(37, 57);
            this.codigoCarteira = Integer.parseInt(registro.substring(57, 58));
            this.numeroDocumento = registro.substring(58, 73);
            String dataVencimento = Util.formataData(registro.substring(73, 81));
            this.dataVencimento = LocalDate.parse(dataVencimento,formatter);
            Double valorTitulo = Double.parseDouble(registro.substring(81, 96));
            this.valorTitulo = valorTitulo / 100;
            this.identificacaoTituloEmpresa = registro.substring(105, 130);
            this.tipoInscricao = Integer.parseInt(registro.substring(132, 133));
            this.numeroInscricao = registro.substring(133, 148);
            this.nomeSacado = registro.substring(148, 188);
            Double valorTarifas = Double.parseDouble(registro.substring(198, 213));
            this.valorTarifas = valorTarifas / 100;
            this.codigoRejeicao = registro.substring(213, 223);
            this.usoFebraban = registro.substring(224, 240);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
