/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.bean.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;
import org.primefaces.component.calendar.Calendar;
import org.springframework.stereotype.Component;

@Component
@Named
@RequestScoped
public class PrimefacesCalendarLocalDateConverter implements Converter<LocalDate> {

    @Override
    public LocalDate getAsObject(FacesContext fc, UIComponent uic, String string) {

        if (Objects.isNull(string)) {
            return null;
        }
        if (!(uic instanceof Calendar)) {
            throw new RuntimeException("primefacesCalendarLocalDateConverter, funciona apenas com o Calendar do Primefaces.");
        }
        Calendar c = (Calendar) uic;
        return LocalDate.parse(string, DateTimeFormatter.ofPattern(c.calculatePattern()));

    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, LocalDate t) {
        if (Objects.isNull(t)) {
            return null;
        }
        if (!(uic instanceof Calendar)) {
            throw new RuntimeException("primefacesCalendarLocalDateConverter, funciona apenas com o Calendar do Primefaces.");
        }
        Calendar c = (Calendar) uic;
        return t.format(DateTimeFormatter.ofPattern(c.getPattern()));
    }

}
