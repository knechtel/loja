/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.bean.converter;

import br.com.acertsis.loja.dao.BancoDAO;
import br.com.acertsis.loja.entity.Banco;
import java.io.Serializable;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.stereotype.Component;

@Component
@Named
@RequestScoped
public class BancoConverter implements Converter<Banco>, Serializable {

    @Inject
    private BancoDAO dao;


    @Override
    public Banco getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (!Objects.isNull(string)) {
            if (string.matches("[0-9]+")) {
                return dao.findById(string).get();
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Banco t) {
        if (Objects.isNull(t) || Objects.isNull(t.getCodigo())) {
            return null;
        }
        return t.getCodigo();
    }

}
