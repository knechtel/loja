/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.bean.admin;

import br.com.acertsis.loja.dao.ParceiroDAO;
import br.com.acertsis.loja.entity.Parceiro;
import br.com.acertsis.loja.entity.PessoaEnum;
import br.com.acertsis.loja.entity.PessoaFisica;
import br.com.acertsis.loja.entity.PessoaJuridica;
import br.com.acertsis.loja.service.CrudService;
import br.com.acertsis.loja.service.ParceiroCrudService;
import br.com.acertsis.loja.service.PesquisarCepService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.component.tabview.TabView;
import org.springframework.stereotype.Component;

@Component
@Named
@ViewScoped
public class CrudParceiroMb extends AbstractCrudMb<Parceiro> {

    @Inject
    private PesquisarCepService pesquisarCepService;
    @Inject
    private ParceiroDAO dao;
    @Inject
    private ParceiroCrudService parceiroService;
    @Setter
    @Getter
    private List<Parceiro> lista;
    @Setter
    @Getter
    private PessoaFisica pessoaFisica;
    @Setter
    @Getter
    private PessoaJuridica pessoaJuridica;

    private Parceiro parceiroPadrao;
    @Setter
    @Getter
    private PessoaEnum tipoPessoa;
    @Setter
    @Getter
    private EnderecoModel enderecoModel;
    @Setter
    @Getter
    private TelefoneModel formContatoModel;

    @Getter
    @Setter
    private ReferenciaBancariaModel referenciaBancariaModel;

    @Setter
    @Getter
    private TabView bindTabView1;


    public CrudParceiroMb() {
        this.bindTabView1 = new TabView();
    }

    @PostConstruct
    private void init() {
        setModel(new Parceiro());
        lista = new ArrayList<>();
        pessoaFisica = new PessoaFisica();
        pessoaJuridica = new PessoaJuridica();
        parceiroPadrao = dao.findByRaiz(true);

        this.enderecoModel = new EnderecoModel();
        this.enderecoModel.init();
        this.enderecoModel.setMultiplosEnderecos(false);

        //telefones
        this.formContatoModel = new TelefoneModel();
        this.formContatoModel.init();

        this.referenciaBancariaModel = new ReferenciaBancariaModel();
        this.referenciaBancariaModel.init();

        this.list();
    }

    @Override
    public void save() {
        try {
            if (getModel().getResponsavel() != null) {
                if (getModel().getResponsavel().getNome() == null || getModel().getResponsavel().getNome().equals("")) {
                    getModel().setResponsavel(null);
                }
            }
            defineTipoPessoa();
            if (getModel().getPessoa().getEndereco() == null) {
                getModel().getPessoa().setEndereco(new ArrayList<>());
            }
            getModel().getPessoa().getEndereco().clear();
            getModel().getPessoa().getEndereco().add(this.enderecoModel.getEndereco());
            getModel().getPessoa().setTelefones(this.formContatoModel.getContatos());

            getModel().setReferenciaBancaria(getReferenciaBancariaModel().getReferenciaBancaria());
            parceiroService.save(getModel(), formContatoModel.getContatosParaRemover());
            addMensagemSucesso(getSaveSuccessMessage());
            list();
        } catch (Exception ex) {
            Logger.getLogger(CrudParceiroMb.class.getName()).log(Level.SEVERE, null, ex);
            addMensagemErro(getSaveErrorMessage());
        }
    }

    @Override
    protected CrudService getService() {
        return parceiroService;
    }


    @Override
    public void edit(Long id) {
        super.edit(id);
        if (Objects.isNull(getModel().getResponsavel())) {
            getModel().setResponsavel(new PessoaFisica());
        }
        switch (getModel().getTipoPessoa()) {
            case JURIDICA:
                this.pessoaJuridica = (PessoaJuridica) getModel().getPessoa();
                break;
            default:
                this.pessoaFisica = (PessoaFisica) getModel().getPessoa();
        }
        if (this.getModel().getPessoa().getEndereco() != null && !this.getModel().getPessoa().getEndereco().isEmpty()) {
            this.enderecoModel.setEndereco(this.getModel().getPessoa().getEndereco().get(0));
        }
        if (this.getModel().getPessoa().getTelefones() != null) {
            this.formContatoModel.setContatos(this.getModel().getPessoa().getTelefones());
        }

        if (getModel().getReferenciaBancaria() != null) {
            this.referenciaBancariaModel.setReferenciaBancaria(getModel().getReferenciaBancaria());
        }

        setTitle(getTitle().concat(" Parceiro"));

    }

    @Override
    public void create() {
        super.create();
        clearModel();
        defineTipoPessoa();
        setTitle(getTitle().concat(" Parceiro"));
    }

    @Override
    public void list() {
        bindTabView1.setActiveIndex(0);
        this.lista.clear();
        Optional.ofNullable(dao.findAll()).orElse(new ArrayList<>()).forEach((it) -> lista.add(it));
        super.list();
        setTitle(getTitle().concat(" Parceiros"));
    }

    @Override
    protected void clearModel() {
        setModel(new Parceiro());
        this.pessoaFisica = new PessoaFisica();
        this.pessoaJuridica = new PessoaJuridica();
        getModel().setHabilitado(true);
        getModel().setTipoPessoa(PessoaEnum.FISICA);
        getModel().setResponsavel(new PessoaFisica());
        defineTipoPessoa();
        this.formContatoModel.clear();
        this.enderecoModel.clear();
        parceiroPadrao = dao.findByRaiz(true);

        this.referenciaBancariaModel.clear();

    }

    private void defineTipoPessoa() {
        switch (getModel().getTipoPessoa()) {
            case JURIDICA:
                getModel().setPessoa(this.pessoaJuridica);
                break;
            default:
                getModel().setPessoa(this.pessoaFisica);
        }
    }

    public boolean isParceiroPodeSerRaiz() {
        if (parceiroPadrao == null) {
            return true;
        }
        if (parceiroPadrao.equals(getModel())) {
            return true;
        }
        return false;
    }

    public void onChangeTipoPessoa() {
        defineTipoPessoa();
    }

    public void aprovarCadastro(Long idParceiro) {
        try {
            this.parceiroService.aprovarCadastroParceiro(idParceiro);
            addMensagemSucesso("Aprovado com sucesso.");
        } catch (Exception e) {
            addMensagemErro("Erro ao atualizar.");
            e.printStackTrace();
        }
    }

    public void preencherEnderecoParceiro() {
        if (enderecoModel.getEndereco().getCep() != null) {
            Map<String, String> maps = pesquisarCepService.pesquisar(enderecoModel.getEndereco().getCep());
            enderecoModel.getEndereco().setBairro(maps.get("bairro"));
            enderecoModel.getEndereco().setCidade(maps.get("localidade"));
            enderecoModel.getEndereco().setEstado(maps.get("uf"));
            enderecoModel.getEndereco().setRua(maps.get("logradouro"));
        }
    }
}
