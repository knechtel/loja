/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.bean.admin;

import br.com.acertsis.loja.dao.AuthoritiesDAO;
import br.com.acertsis.loja.dao.UsuarioDAO;
import br.com.acertsis.loja.entity.Authorities;
import br.com.acertsis.loja.entity.Parceiro;
import br.com.acertsis.loja.entity.Usuario;
import br.com.acertsis.loja.security.MyBCryptPasswordEncoder;
import br.com.acertsis.loja.security.RolesSecurityEnum;
import br.com.acertsis.loja.service.AdminUsuarioService;
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
public class UsuarioMb extends AbstractCrudMbAntiga<Usuario> {

    @Inject
    private UsuarioDAO dao;
    @Inject
    private AdminUsuarioService usuarioService;
    @Inject
    private AuthoritiesDAO authoritiesDAO;
    @Inject
    private MyBCryptPasswordEncoder myBCryptPasswordEncoder;
    @Setter
    @Getter
    private List<Usuario> lista;

    @Getter
    @Setter
    private RolesSecurityEnum permissao;

    @PostConstruct
    private void init() {
        setModel(new Usuario());
        this.getModel().setAuthorities(new ArrayList<>());
        lista = new ArrayList<>();
        this.list();
        //FacesContext.getCurrentInstance().getViewRoot().findComponent("form")
    }

    @Override
    protected CrudRepository getRepository() {
        return dao;
    }

    @Override
    public void list() {
        this.lista.clear();
        Optional.ofNullable(dao.findAll()).orElse(new ArrayList<>()).forEach((it) -> lista.add(it));
        super.list();
    }

    @Override
    protected void clearModel() {
        setModel(new Usuario());
        this.getModel().setAuthorities(new ArrayList<>());
        getModel().setEnabled(true);
        this.permissao = null;
    }

    public RolesSecurityEnum getRolesSecurityEnum(String role) {
        return RolesSecurityEnum.valueOf(role);
    }

    @Override
    public void edit(Long id) {
        super.edit(id);
        List<Authorities> auths = authoritiesDAO.findByUsernameAndUsuario(getModel().getUsername(), getModel());
        getModel().setAuthorities(auths);
        if (auths != null && !auths.isEmpty()) {
            this.permissao = RolesSecurityEnum.valueOf(auths.get(0).getAuthority());
        }
        this.getModel().setPasswordTransient(getModel().getPassword());
    }

    @Override
    public void save() {
        if (usuarioService.canSaveUsuario(getModel())) {
            if (getModel().getIdUsuario() != null) {
                this.getModel().getAuthorities().get(0).setAuthority(this.permissao.name());

                if (this.getModel().getPasswordTransient().equals(getModel().getPassword())) {
                    super.save();
                } else {
                    this.getModel().setPassword(myBCryptPasswordEncoder.encode(getModel().getPassword()));
                    super.save();
                }
            } else {
                Authorities a = new Authorities();
                a.setUsuario(getModel());
                a.setUsername(getModel().getUsername());
                a.setAuthority(this.permissao.name());
                this.getModel().getAuthorities().add(a);
                this.getModel().setPassword(myBCryptPasswordEncoder.encode(getModel().getPassword()));
                super.save();
            }
        } else {
            marcarElementoComoInvalido("crudForm:usuario");
            addMensagemErro("Já existe um usuario com o mesmo nome de usuario.");
        }
    }

    public List<Parceiro> completeText(String query) {
        List<Parceiro> results = this.usuarioService.findParceiroPorNome(query);
        return results;
    }
}
