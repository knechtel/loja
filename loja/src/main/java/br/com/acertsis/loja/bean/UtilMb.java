/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.bean;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.springframework.stereotype.Component;

@Component
@Named
@RequestScoped
public class UtilMb {

    private static final DecimalFormat MONEY_FORMAT = new DecimalFormat("#,##0.00");

    public String formatLocalDate(LocalDate date, String pattern) {
        if (Objects.isNull(date)) {
            return null;
        }
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public String formatMoney(Double number, boolean showSimbol) {
        if (Objects.isNull(number)) {
            return null;
        }
        if (showSimbol) {
            return "R$".concat(MONEY_FORMAT.format(number));
        }
        return MONEY_FORMAT.format(number);
    }
}
