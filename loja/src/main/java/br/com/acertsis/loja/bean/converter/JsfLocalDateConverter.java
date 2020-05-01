/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.bean.converter;

import com.google.common.base.Strings;
import org.springframework.stereotype.Component;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


@Component
@Named
@RequestScoped
public class JsfLocalDateConverter implements Converter<LocalDate> {

    @Override
    public LocalDate getAsObject(FacesContext fc, UIComponent uic, String string) {

        if (Objects.isNull(string)) {
            return null;
        }

        if (Strings.isNullOrEmpty(string)) {
            return null;
        }

        if (!uic.getAttributes().containsKey("pattern")) {
            throw new RuntimeException("Adicione o atributo pattern, ao componente " + uic.getId());
        }
        return LocalDate.parse(string, DateTimeFormatter.ofPattern((String) uic.getAttributes().get("pattern")));

    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, LocalDate t) {
        if (Objects.isNull(t)) {
            return null;
        }
        if (!uic.getAttributes().containsKey("pattern")) {
            throw new RuntimeException("Adicione o atributo pattern, ao componente " + uic.getId());
        }
        return t.format(DateTimeFormatter.ofPattern((String) uic.getAttributes().get("pattern")));
    }
}
