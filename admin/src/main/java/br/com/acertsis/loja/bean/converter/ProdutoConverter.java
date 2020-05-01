/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.bean.converter;

import br.com.acertsis.loja.dao.ProdutoDAO;
import br.com.acertsis.loja.entity.Produto;
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
public class ProdutoConverter implements Converter<Produto>, Serializable {

    @Inject
    private ProdutoDAO dao;

    @Override
    public Produto getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (!Objects.isNull(string)) {
            if (string.matches("[1-9]+")) {
                return dao.findById(Long.valueOf(string)).get();
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Produto t) {
        if (Objects.isNull(t) || Objects.isNull(t.getIdProduto())) {
            return null;
        }
        return t.getIdProduto().toString();
    }

}
