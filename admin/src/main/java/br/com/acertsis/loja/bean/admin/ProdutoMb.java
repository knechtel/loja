/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.bean.admin;

import br.com.acertsis.loja.dao.ProdutoDAO;
import br.com.acertsis.loja.entity.Produto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
@Named
@ViewScoped
public class ProdutoMb extends AbstractCrudMbAntiga<Produto> {

    @Inject
    private ProdutoDAO dao;

    @Setter
    @Getter
    private List<Produto> lista;

    @PostConstruct
    private void init() {
        setModel(new Produto());
        lista = new ArrayList<>();
        this.list();
    }

    @Override
    protected CrudRepository getRepository() {
        return dao;
    }

    @Override
    public void edit(Long id) {
        super.edit(id);
    }

    @Override
    public void list() {
        this.lista.clear();
        Optional.ofNullable(dao.findAll()).orElse(new ArrayList<>()).forEach((it) -> lista.add(it));
        super.list();
    }

    @Override
    protected void clearModel() {
        setModel(new Produto());
        getModel().setHabilitado(true);
    }
}
