/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.bean.admin;

import br.com.acertsis.loja.dao.RegraComissaoDAO;
import br.com.acertsis.loja.entity.Parceiro;
import br.com.acertsis.loja.entity.RegraComissao;
import br.com.acertsis.loja.service.RegraComissaoService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;
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
public class RegraComissaoCrudMb extends AbstractOnePageCrud<RegraComissao> {

    @Inject
    private RegraComissaoDAO dao;
    @Getter
    @Setter
    private RegraComissao model;
    @Getter
    @Setter
    private List<RegraComissao> lista;
    @Inject
    private RegraComissaoService regraComissaoService;

    @PostConstruct
    @Override
    protected void init() {
        this.model = new RegraComissao();
        this.lista = new ArrayList<>();
        list();
    }

    @Override
    protected CrudRepository getRepository() {
        return dao;
    }

    public void adicionarNovaRegra() {
        model.setHabilitado(true);
        model.setDtInicial(LocalDate.now());
        this.save(model);
        this.clearModel();
        this.list();
    }

    public List<Parceiro> completeText(String query) {
        List<Parceiro> results = this.regraComissaoService.findParceiroPorNome(query);
        return results;
    }

    @Override
    public void delete(Long id) {
        super.delete(id);
        clearModel();
        list();
    }

    @Override
    protected void clearModel() {
        this.model = new RegraComissao();
        this.model.setParceiro(new Parceiro());
    }

    private void list() {
        this.lista = dao.listAll().orElse(new ArrayList<>());
    }

    public void onChangeParceiro(ValueChangeEvent e) {
        System.out.println(this.model.getParceiro().getIdParceiro());
    }
    
    public void updateRegra(String campo, RegraComissao regraComissao) {
        this.save(regraComissao);
        list();
    }

    public void desabilitarHabilitar(RegraComissao regraComissao) {
        regraComissao.setHabilitado(!regraComissao.isHabilitado());
        this.save(regraComissao);
        list();
    }


}
