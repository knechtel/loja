/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.bean.admin;

import br.com.acertsis.loja.bean.SessionMB;
import br.com.acertsis.loja.dao.ClienteDAO;
import br.com.acertsis.loja.entity.Cliente;
import br.com.acertsis.loja.entity.Parceiro;
import br.com.acertsis.loja.entity.PessoaEnum;
import br.com.acertsis.loja.entity.PessoaFisica;
import br.com.acertsis.loja.entity.PessoaJuridica;
import br.com.acertsis.loja.entity.Usuario;
import br.com.acertsis.loja.service.ClienteCrudService;
import br.com.acertsis.loja.service.CrudService;
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
public class ClienteMb extends AbstractCrudMb<Cliente> {
    @Inject
    private SessionMB sessionMB;
    @Inject
    private ClienteDAO dao;
    @Inject
    private ClienteCrudService clienteService;
    @Inject
    private PesquisarCepService pesquisarCepService;
    @Setter
    @Getter
    private List<Cliente> lista;
    @Setter
    @Getter
    private PessoaFisica pessoaFisica;
    @Setter
    @Getter
    private PessoaJuridica pessoaJuridica;
    @Setter
    @Getter
    private PessoaEnum tipoPessoa;
    @Setter
    @Getter
    private EnderecoModel enderecoModel;
    @Setter
    @Getter
    private TelefoneModel formContatoModel;
    @Setter
    @Getter
    private Parceiro parceiroLogado;


    @Setter
    @Getter
    private TabView bindTabView1;

    public ClienteMb() {
        this.bindTabView1 = new TabView();
    }

    @PostConstruct
    private void init() {

        setModel(new Cliente());
        getModel().setHabilitado(true);
        getModel().setResponsavel(new PessoaFisica());
        lista = new ArrayList<>();
        this.pessoaFisica = new PessoaFisica();
        this.pessoaFisica.setTipoPessoa(PessoaEnum.FISICA);
        this.pessoaJuridica = new PessoaJuridica();
        this.pessoaJuridica.setTipoPessoa(PessoaEnum.JURIDICA);
        getModel().setPessoa(this.pessoaFisica);
        tipoPessoa = PessoaEnum.FISICA;
        this.enderecoModel = new EnderecoModel();
        this.enderecoModel.init();
        this.enderecoModel.setMultiplosEnderecos(false);
        this.formContatoModel = new TelefoneModel();
        this.formContatoModel.init();
        Usuario usuario = sessionMB.getUsuarioLogado();
        if (usuario != null) {
            parceiroLogado = this.clienteService.findParceiroByUsuario(usuario);
        }
        this.list();
    }

    @Override
    public void list() {
        bindTabView1.setActiveIndex(0);
        this.lista.clear();
        super.list();
        if (sessionMB.isUserParceiro()) {
            clienteService.findAllByParceiro(parceiroLogado).orElse(new ArrayList<>()).forEach((it) -> lista.add(it));
        }
        if (sessionMB.isUserAdmin()) {
            Optional.ofNullable(clienteService.findAll()).orElse(new ArrayList<>()).forEach((it) -> lista.add(it));
        }
        setTitle(getTitle().concat(" Clientes"));

    }

    @Override
    public void create() {
        super.create();
        clearModel();
        defineTipoPessoa();
        this.getModel().setParceiro(parceiroLogado);
        setTitle(getTitle().concat(" Cliente"));
    }

    @Override
    public void edit(Long id) {
        super.edit(id);
        if (Objects.isNull(getModel().getResponsavel())) {
            getModel().setResponsavel(new PessoaFisica());
        }
        tipoPessoa = getModel().getPessoa().getTipoPessoa();
        switch (tipoPessoa) {
            case JURIDICA:
                this.pessoaJuridica = (PessoaJuridica) getModel().getPessoa();
                break;
            default:
                this.pessoaFisica = (PessoaFisica) getModel().getPessoa();
        }
        //buscar enderecos
        if (this.getModel().getPessoa().getEndereco() != null) {
            this.enderecoModel.setEndereco(this.getModel().getPessoa().getEndereco().get(0));
        }
        //buscar telefones
        this.formContatoModel.setContatos(this.getModel().getPessoa().getTelefones());
        setTitle(getTitle().concat(" Cliente"));
    }

    @Override
    public void save() {

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

        try {
            if (getModel().getPessoa().getTelefones() != null && getModel().getPessoa().getTelefones().size() > 0) {
                this.clienteService.save(getModel(), formContatoModel.getContatosParaRemover());
                addMensagemSucesso(getSaveSuccessMessage());
                list();
            } else {
                addMensagemErro("Você deve adicionar ao menos um telefone.");
                bindTabView1.setActiveIndex(2);
            }
        } catch (Exception ex) {
            Logger.getLogger(ClienteMb.class.getName()).log(Level.SEVERE, null, ex);
            addMensagemErro(getSaveErrorMessage());
        }
    }

    public List<Parceiro> autoCompleteParceiro(String query) {
        List<Parceiro> parceiros = new ArrayList<Parceiro>();
        if (this.sessionMB.isUserAdmin()) {
            parceiros = this.clienteService.findParceiroPorNome(query);
        } else {
            parceiros.add(this.parceiroLogado);
        }
        return parceiros;
    }

    @Override
    protected void clearModel() {
        setModel(new Cliente());
        getModel().setHabilitado(true);
        getModel().setResponsavel(new PessoaFisica());
        this.pessoaFisica = new PessoaFisica();
        this.pessoaFisica.setTipoPessoa(PessoaEnum.FISICA);
        this.pessoaJuridica = new PessoaJuridica();
        this.pessoaJuridica.setTipoPessoa(PessoaEnum.JURIDICA);
        defineTipoPessoa();
        this.enderecoModel.clear();
        this.formContatoModel.clear();
    }

    private void defineTipoPessoa() {
        switch (tipoPessoa) {
            case JURIDICA:
                getModel().setPessoa(this.pessoaJuridica);
                break;
            default:
                getModel().setPessoa(this.pessoaFisica);
        }
    }

    public void onChangeTipoPessoa() {
        defineTipoPessoa();
    }

    @Override
    protected CrudService getService() {
        return clienteService;
    }

    public void preencherEnderecoCliente() {
        if (enderecoModel.getEndereco().getCep() != null) {
            Map<String, String> maps = pesquisarCepService.pesquisar(enderecoModel.getEndereco().getCep());
            enderecoModel.getEndereco().setBairro(maps.get("bairro"));
            enderecoModel.getEndereco().setCidade(maps.get("localidade"));
            enderecoModel.getEndereco().setEstado(maps.get("uf"));
            enderecoModel.getEndereco().setRua(maps.get("logradouro"));
        }
    }

}
