/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.bean;

import br.com.acertsis.loja.dto.FormContatoLojaDto;
import br.com.acertsis.loja.service.email.EmailsPadraoService;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@Named
@RequestScope
public class ContatoMb extends AbstractManagedBean implements Serializable {

    @Getter
    @Setter
    private FormContatoLojaDto contatoLojaDto;
    @Inject
    private EmailsPadraoService emailsPadraoService;
    @Getter
    @Setter
    private List<FormContatoLojaDto.EnumTipoMensagem> tipoContatos;

    @PostConstruct
    private void init() {
        this.contatoLojaDto = new FormContatoLojaDto();
        this.contatoLojaDto.setTipoMensagem(FormContatoLojaDto.EnumTipoMensagem.DUVIDA);
        this.tipoContatos = Arrays.asList(FormContatoLojaDto.EnumTipoMensagem.values());
    }

    public void enviarEmailContrato() {
        try {
            this.emailsPadraoService.enviarEmailContato(contatoLojaDto);
            PrimeFaces.current().executeScript("abrirModalContatoEnviadoComSucesso()");
        } catch (Exception ex) {
            addMensagemErro("Erro ao enviar email.");
            PrimeFaces.current().executeScript("abrirErro()");
            Logger.getLogger(ContatoMb.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.contatoLojaDto = new FormContatoLojaDto();
        this.contatoLojaDto.setTipoMensagem(FormContatoLojaDto.EnumTipoMensagem.DUVIDA);
    }
}
