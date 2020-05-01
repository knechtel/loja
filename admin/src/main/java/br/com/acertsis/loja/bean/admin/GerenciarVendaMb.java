/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.bean.admin;

import br.com.acertsis.loja.bean.SessionMB;
import br.com.acertsis.loja.dto.TableContratosDto;
import br.com.acertsis.loja.entity.Cliente;
import br.com.acertsis.loja.entity.Contrato;
import br.com.acertsis.loja.entity.Mensalidade;
import br.com.acertsis.loja.entity.Parceiro;
import br.com.acertsis.loja.service.GerenciarVendaService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import org.springframework.stereotype.Component;

@Component
@Named
@ViewScoped
public class GerenciarVendaMb implements Serializable {

    private Parceiro parceiroLogado;
    @Inject
    private GerenciarVendaService gerenciarVendaService;
    @Inject
    private SessionMB sessionMB;
    @Setter
    @Getter
    private Cliente clienteSelecionado;

    @Getter
    @Setter
    private List<TableContratosDto> contratos;

    @Getter
    @Setter
    private Contrato contrato;
    @Getter
    @Setter
    private List<Contrato> contratoList;

    @PostConstruct
    public void init() {
        this.parceiroLogado = this.sessionMB.getUsuarioLogado().getParceiro();
        this.contratos = new ArrayList<>();
        this.contratoList = new ArrayList<>();
    }

    public void onChangeCliente() {
        this.contratos = this.gerenciarVendaService.listarContratosCliente(this.clienteSelecionado);
        this.contratoList = this.gerenciarVendaService.listarContratos(clienteSelecionado);
    }

    public List<Cliente> buscarClienteAutoComplete(String query) {
        List<Cliente> clientes = this.gerenciarVendaService.buscarClienteAutoComplete(query, this.parceiroLogado);
        return clientes;
    }

    public void exibirDetalhesContrato(Long idContrato) {
        this.contrato = this.gerenciarVendaService.findContrato(idContrato);
        PrimeFaces.current().executeScript("exibirDetalhesPlano()");
    }

    public List<Mensalidade> findMensalidadesByContrato(Contrato contrato) {
        List<Mensalidade> mensalidades = this.gerenciarVendaService.findMensalidadesByContrato(contrato);
        return mensalidades;
    }
}
