/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.bean.admin;

import br.com.acertsis.loja.dao.PlanoDAO;
import br.com.acertsis.loja.entity.Modulo;
import br.com.acertsis.loja.entity.PeriodoEnum;
import br.com.acertsis.loja.entity.Plano;
import br.com.acertsis.loja.entity.Produto;
import br.com.acertsis.loja.service.PlanoService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.DualListModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
@Named
@ViewScoped
public class CrudPlanoMb extends AbstractCrudMbAntiga<Plano> {

    @Inject
    private PlanoDAO dao;
    @Inject
    private PlanoService planoService;
    @Setter
    @Getter
    private List<Plano> lista;
    @Setter
    @Getter
    private DualListModel<Produto> produtos;
    @Setter
    @Getter
    private DualListModel<Modulo> modulos;

    @PostConstruct
    private void init() {
        setModel(new Plano());
        lista = new ArrayList<>();
        produtos = new DualListModel<>(planoService.listProdutos(), new ArrayList<>());
        modulos = new DualListModel<>(planoService.listModulos(), new ArrayList<>());
        this.list();
    }

    @Override
    protected CrudRepository getRepository() {
        return dao;
    }

    @Override
    public void save() {
        if (this.getModel().getValidade() != null) {
            this.getModel().setValidade(PeriodoEnum.ANUAL);
        }
        this.getModel().setModulos(new HashSet(this.modulos.getTarget()));
        this.getModel().setListProduto(new HashSet(this.produtos.getTarget()));
        super.save();
    }

    @Override
    public void create() {
        super.create();
        setTitle(getTitle().concat(" Plano"));

    }


    @Override
    public void edit(Long id) {
        super.edit(id);
        if (Objects.nonNull(produtos)) {
            if (Objects.nonNull(getModel().getListProduto())) {
                produtos.getTarget().addAll(getModel().getListProduto());
                produtos.getSource().removeAll(getModel().getListProduto());
            }
        }
        if (Objects.nonNull(modulos)) {
            if (Objects.nonNull(getModel().getModulos())) {
                modulos.getTarget().addAll(getModel().getModulos());
                modulos.getSource().removeAll(getModel().getModulos());
            }
        }
        setTitle(getTitle().concat(" Plano"));
    }

    @Override
    public void list() {
        this.lista.clear();
        dao.findAllFetchProdutosEModulos().orElse(new ArrayList<>()).forEach((it) -> lista.add(it));
        super.list();
        setTitle(getTitle().concat(" Planos"));
    }

    @Override
    protected void clearModel() {
        setModel(new Plano());
        getModel().setHabilitado(true);
        this.modulos.getTarget().clear();
        this.modulos.getSource().clear();
        this.produtos.getTarget().clear();
        this.produtos.getSource().clear();
        this.produtos.getSource().addAll(this.planoService.listProdutos());
        this.modulos.getSource().addAll(this.planoService.listModulos());
    }
}
