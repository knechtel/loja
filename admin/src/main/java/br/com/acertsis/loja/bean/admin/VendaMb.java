/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.bean.admin;

import br.com.acertsis.loja.bean.AbstractManagedBean;
import br.com.acertsis.loja.bean.SessionMB;
import br.com.acertsis.loja.bean.admin.venda.DadosVendaDTO;
import br.com.acertsis.loja.dto.CartaoCreditoDTO;
import br.com.acertsis.loja.entity.Cliente;
import br.com.acertsis.loja.entity.Contrato;
import br.com.acertsis.loja.entity.MeioPagamentoEnum;
import br.com.acertsis.loja.entity.Parceiro;
import br.com.acertsis.loja.entity.PessoaFisica;
import br.com.acertsis.loja.entity.PessoaJuridica;
import br.com.acertsis.loja.entity.Plano;
import br.com.acertsis.loja.entity.RegraComissao;
import br.com.acertsis.loja.exception.BusinessException;
import br.com.acertsis.loja.service.ClienteService;
import br.com.acertsis.loja.service.RegraComissaoService;
import br.com.acertsis.loja.service.VendaService;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Messages;
import org.primefaces.PrimeFaces;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.component.wizard.Wizard;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.stereotype.Component;

@Component
@Named
@ViewScoped
public class VendaMb extends AbstractManagedBean implements Serializable {

    @Inject
    private SessionMB sessionMB;
    @Inject
    private VendaService vendaDashBoardService;
    @Inject
    private ClienteService clienteService;
    @Inject
    private RegraComissaoService regraComissaoService;
    @Getter
    @Setter
    private DadosVendaDTO dadosVendaDTO;
    @Getter
    @Setter
    private List<Plano> planos;
    @Getter
    @Setter
    private Parceiro parceiro;
    @Getter
    @Setter
    private PessoaFisica pessoaFisicaCliente;
    @Getter
    @Setter
    private PessoaJuridica pessoaJuridicaCliente;
    @Getter
    @Setter
    private Contrato contrato;
    @Getter
    private StreamedContent downloadBoleto;
    @Getter
    @Setter
    private boolean vendaFinalizadaComSucesso;

    private RegraComissao regraComissaoAtualParceiro;


    @PostConstruct
    private void init() {
        this.parceiro = this.sessionMB.getUsuarioLogado().getParceiro();
        Optional<RegraComissao> opt = this.regraComissaoService.buscarComissaoAtiva(parceiro, LocalDate.now());
        if (opt.isPresent()) {
            this.regraComissaoAtualParceiro = opt.get();
        }
        addNovaVenda();
    }

    public void addNovaVenda() {
        this.vendaFinalizadaComSucesso = false;
        this.dadosVendaDTO = new DadosVendaDTO();
        this.dadosVendaDTO.setCartaoCreditoDTO(new CartaoCreditoDTO());
        this.dadosVendaDTO.setNumParcelas(1);
        this.downloadBoleto = null;
        this.contrato = null;
        Wizard bindWizardVenda = (Wizard) FacesContext.getCurrentInstance().getViewRoot().findComponent("formVenda:wizardVenda");
        bindWizardVenda.setStep("clienteTab");
    }

    public String onFlowProcess(FlowEvent event) {
        if ("finalizarTab".equals(event.getNewStep())) {
            this.gerarContrato();
        }
        if ("finalizarTab".equals(event.getOldStep())) {
            this.dadosVendaDTO.setAceito(false);
        }
        return event.getNewStep();
    }

    public List<Cliente> completeTextNomeCliente(String query) {

        List<Cliente> results = this.clienteService.buscarClienteAutoComplete(query, this.parceiro);
        return results;
    }

    public void onChangeCliente() {
        if (Objects.nonNull(this.dadosVendaDTO.getClienteSelecionado())) {
            switch (this.dadosVendaDTO.getClienteSelecionado().getPessoa().getTipoPessoa()) {
                case FISICA:
                    this.pessoaFisicaCliente = (PessoaFisica) this.dadosVendaDTO.getClienteSelecionado().getPessoa();
                    break;
                case JURIDICA:
                    this.pessoaJuridicaCliente = (PessoaJuridica) this.dadosVendaDTO.getClienteSelecionado().getPessoa();
            }
        }
    }

    public void onChangeProduto() {
        if (Objects.nonNull(this.dadosVendaDTO.getProdutoSelecionado())) {
            this.planos = this.vendaDashBoardService.listPlanosByProduto(this.dadosVendaDTO.getProdutoSelecionado());
        }
        this.dadosVendaDTO.setPlanoSelecionado(null);
    }

    public void onChangePlano() {
        if (Objects.nonNull(this.dadosVendaDTO.getPlanoSelecionado())) {
            Plano plano = vendaDashBoardService.getPlanoFetchModulosProdutos(this.dadosVendaDTO.getPlanoSelecionado()).orElse(null);
            this.dadosVendaDTO.setPlanoSelecionado(plano);
        }
    }

    public void onChangeMeioPagamento() {
        if (Objects.nonNull(this.dadosVendaDTO.getPagamentoEnum())) {
            switch (this.dadosVendaDTO.getPagamentoEnum()) {
                case BOLETO:
                    this.dadosVendaDTO.setCartaoCreditoDTO(new CartaoCreditoDTO());

                    break;
                case CARTAO_CREDITO:
                    this.dadosVendaDTO.getCartaoCreditoDTO().setTitular(this.dadosVendaDTO.getClienteSelecionado().getPessoa().getNome());
            }
        }
    }

    public void onChangeNumeroParcelas(AjaxBehaviorEvent event) {
        SelectOneMenu select = (SelectOneMenu) event.getComponent();
        dadosVendaDTO.setNumParcelas(Integer.valueOf(select.getValue().toString()));
    }

    public void finalizarVenda() {
        try {
            Cliente cliente = dadosVendaDTO.getClienteSelecionado();
            Plano planoSelecionado = dadosVendaDTO.getPlanoSelecionado();
            MeioPagamentoEnum meioPagamentoEnum = dadosVendaDTO.getPagamentoEnum();
            int numParcelas = dadosVendaDTO.getNumParcelas();
            CartaoCreditoDTO cartaoCreditoDTO = dadosVendaDTO.getCartaoCreditoDTO();
            switch (this.dadosVendaDTO.getPagamentoEnum()) {
                case BOLETO:
                    byte[] bytes = this.vendaDashBoardService.pagarBoleto(cliente, planoSelecionado, meioPagamentoEnum, numParcelas);
                    createDownloadBoleto(bytes);
                    vendaFinalizadaComSucesso = true;
                    PrimeFaces.current().executeScript("PF('dialogBoleto').show();");
                    break;
                case CARTAO_CREDITO:
                    this.vendaDashBoardService.pagarCartaoCredito(cartaoCreditoDTO, cliente, planoSelecionado, meioPagamentoEnum, numParcelas);
                    vendaFinalizadaComSucesso = true;
            }

        } catch (BusinessException ex) {
            addMensagemErro(ex.getMessage());
            Logger.getLogger(VendaMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            addMensagemErro(ex.getMessage());
            Logger.getLogger(VendaMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createDownloadBoleto(byte[] bytes) {
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        downloadBoleto = new DefaultStreamedContent(stream, "application/pdf", "boleto_" + dadosVendaDTO.getClienteSelecionado().getPessoa().getNome() + ".pdf");
    }

    private void gerarContrato() {
        try {
            Cliente cliente = dadosVendaDTO.getClienteSelecionado();
            Plano planoSelecionado = dadosVendaDTO.getPlanoSelecionado();
            MeioPagamentoEnum meioPagamentoEnum = dadosVendaDTO.getPagamentoEnum();
            int numParcelas = dadosVendaDTO.getNumParcelas();
            this.contrato = this.vendaDashBoardService.gerarContratoNaoSalvar(cliente, planoSelecionado, meioPagamentoEnum, numParcelas);
        } catch (BusinessException ex) {
            Messages.addGlobalError(ex.getMessage());
            Logger.getLogger(VendaMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public double calcularValorPlanoPelaRegraComissao(Plano plano) {
        double valor = plano.getValorPlano() + regraComissaoAtualParceiro.getTxAdesao();
        return valor;
    }
}
