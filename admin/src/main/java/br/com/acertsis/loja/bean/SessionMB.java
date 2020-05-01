package br.com.acertsis.loja.bean;

import br.com.acertsis.loja.bean.exception.MudarSenhaUsuarioException;
import br.com.acertsis.loja.entity.Usuario;
import br.com.acertsis.loja.security.RolesSecurityEnum;
import br.com.acertsis.loja.service.AdminUsuarioService;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import org.springframework.stereotype.Component;

@Component
@Named
@SessionScoped
public class SessionMB implements Serializable {

    @Inject
    private AdminUsuarioService usuarioService;
    @Getter
    private Usuario usuarioLogado;
    @Setter
    @Getter
    private String senhaAtual;
    @Setter
    @Getter
    private String senha;
    @Setter
    @Getter
    private String senhaConfirmacao;

    @PostConstruct
    public void init() {
        String currentUser = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
        if (currentUser != null) {
            Usuario usuario = usuarioService.loadUsuarioWithAuthoritiesByUsername(currentUser);
            usuarioLogado = new Usuario();
            usuarioLogado.setEnabled(usuario.isEnabled());
            usuarioLogado.setIdUsuario(usuario.getIdUsuario());
            usuarioLogado.setUsername(usuario.getUsername());
            usuarioLogado.setNome(usuario.getNome());
            usuarioLogado.setParceiro(usuario.getParceiro());
            usuarioLogado.setAuthorities(usuario.getAuthorities());
        }
    }

    public boolean isUserParceiro() {
        return usuarioLogado.getAuthorities().stream().findFirst().get().getAuthority().equals(RolesSecurityEnum.ROLE_PARCEIRO.name());
        //return FacesContext.getCurrentInstance().getExternalContext().isUserInRole(RolesSecurityEnum.ROLE_PARCEIRO.name());
    }

    public boolean isUserAdmin() {
        return usuarioLogado.getAuthorities().stream().findFirst().get().getAuthority().equals(RolesSecurityEnum.ROLE_ADMIN.name());
        //return FacesContext.getCurrentInstance().getExternalContext().isUserInRole(RolesSecurityEnum.ROLE_ADMIN.name());

    }

    public void mudarSenha() {
        try {
            usuarioService.mudarSenha(senhaAtual, senha, senhaConfirmacao, usuarioLogado.getIdUsuario());
            this.senhaAtual = null;
            this.senha = null;
            this.senhaConfirmacao = null;
            String mensagemSucesso = "Senha alterada com sucesso.";
            PrimeFaces.current().executeScript("PF('dialogMudarSenha').hide()");
            FacesContext.getCurrentInstance().addMessage("formMudarSenha", new FacesMessage(FacesMessage.SEVERITY_INFO, mensagemSucesso, null));
        } catch (MudarSenhaUsuarioException ex) {
            FacesContext.getCurrentInstance().validationFailed();
            FacesContext.getCurrentInstance().addMessage("formMudarSenha", new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage()));
        }
    }
}
