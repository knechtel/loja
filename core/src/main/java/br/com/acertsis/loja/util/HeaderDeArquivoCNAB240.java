package br.com.acertsis.loja.util;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class HeaderDeArquivoCNAB240 {
    private String mensagemRemessa;
    private String numeroRemessa;
    private LocalDate dataRemessa;
    private LocalTime horaRemessa;

    public HeaderDeArquivoCNAB240() {
    }

    public String getMensagemRemessa() {
        return mensagemRemessa;
    }

    public void setMensagemRemessa(String mensagemRemessa) {
        this.mensagemRemessa = mensagemRemessa;
    }

    public String getNumeroRemessa() {
        return numeroRemessa;
    }

    public void setNumeroRemessa(String numeroRemessa) {
        this.numeroRemessa = numeroRemessa;
    }

    public LocalDate getDataRemessa() {
        return dataRemessa;
    }

    public void setDataRemessa(LocalDate dataRemessa) {
        this.dataRemessa = dataRemessa;
    }

    public LocalTime getHoraRemessa() {
        return horaRemessa;
    }

    public void setHoraRemessa(LocalTime horaRemessa) {
        this.horaRemessa = horaRemessa;
    }

    public void LerHeaderDeArquivoCNAB240(String registro) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");
        try {
            if (!registro.substring(7, 8).equalsIgnoreCase("0")) {
                throw new Exception("registro inválido. O detalhe não possuí as características de Header de Arquivo.");
            }

            mensagemRemessa = registro.substring(171, 191).trim();
            numeroRemessa = registro.substring(157, 163).trim();
            dataRemessa = LocalDate.parse(Util.formataData(registro.substring(143, 151)), formatter);
            horaRemessa = LocalTime.parse(Util.formataHora(registro.substring(151, 157)), fmt);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "HeaderDeArquivoCNAB240{" +
                "mensagemRemessa='" + mensagemRemessa + '\'' +
                ", numeroRemessa='" + numeroRemessa + '\'' +
                ", dataRemessa='" + dataRemessa + '\'' +
                ", horaRemessa='" + horaRemessa + '\'' +
                '}';
    }
}