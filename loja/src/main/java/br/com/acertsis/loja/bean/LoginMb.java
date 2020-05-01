package br.com.acertsis.loja.bean;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Named
@ViewScoped
public class LoginMb extends AbstractManagedBean implements Serializable {

    private Logger logger = Logger.getGlobal();

    @Setter
    @Getter
    private String usuario;
    @Setter
    @Getter
    private String senha;


    @PostConstruct
    private void init() {
        usuario = "";
        senha = "";
    }

    public String login() {
        if (usuario != null && senha != null) {
            if (usuario.equals("admin") && senha.equals("admin")) {
                return "admin/index.xhtml";
            }
        }

        addMensagemErroFlash("Usuario ou Senha inválidos.");
        return null;
    }

}
