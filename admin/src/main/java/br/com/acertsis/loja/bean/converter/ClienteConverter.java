/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.bean.converter;

import br.com.acertsis.loja.dao.ClienteDAO;
import br.com.acertsis.loja.entity.Cliente;
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
public class ClienteConverter implements Converter<Cliente>, Serializable {

    @Inject
    private ClienteDAO dao;

    @Override
    public Cliente getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (!Objects.isNull(string)) {
            if (string.matches("[1-9]+")) {
                return dao.findById(Long.valueOf(string)).get();
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Cliente t) {
        if (Objects.isNull(t) || Objects.isNull(t.getIdCliente())) {
            return null;
        }
        return t.getIdCliente().toString();
    }

}
