package br.com.acertsis.loja.util;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {
    private static final DecimalFormat FORMAT_MONEY = new DecimalFormat("#,##0.00");
    private static final DecimalFormat FORMAT_MONEY_SIMBOL = new DecimalFormat("R$ #,##0.00");


    public static String formataData(String data){
        return data.substring(0,2)+"-"+data.substring(2,4)+"-"+data.substring(4,8);
    }

    public static String formataHora(String data){
        return data.substring(0,2)+":"+data.substring(2,4)+":"+data.substring(4,6);
    }

    public static LocalDateTime converteDataHora(String data){
        return LocalDateTime.parse(data, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static LocalDate converteData(String data){
        return LocalDate.parse(data, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /**
     * Formata um double em formato dinheiro do Brasil.
     *
     *
     * @param valor Valor que se deseja formatar.
     * @param includeSimbol Se true inclui o simbolo "R$", seguido de um espaço
     * e o valor formatado ex.: R$ 45,00
     * @return O valor formatado.
     *
     * double valor = 50.0; Util.moneyFormat(valor,true); // retorna R$ 50,00
     */
    public static String moneyFormat(double valor, boolean includeSimbol) {
        if (includeSimbol) {
            return FORMAT_MONEY_SIMBOL.format(valor);
        } else {
            return FORMAT_MONEY.format(valor);
        }
    }

    public static Integer moneyFormatCielo(double valor) {
        String f = FORMAT_MONEY.format(valor);
        return Integer.valueOf(f.replace(",", ""));
    }

    public static String formatVencimentoCartaoCielo(String mes, String ano) {

        return String.format("%02d", Integer.valueOf(mes)).concat("/").concat(ano);

    }

}
