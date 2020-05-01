package br.com.acertsis.loja.bean;

import br.com.acertsis.loja.dao.*;
import br.com.acertsis.loja.dto.CartaoCreditoDTO;
import br.com.acertsis.loja.dto.FinalizarCompraDTO;
import br.com.acertsis.loja.entity.*;
import br.com.acertsis.loja.exception.BusinessException;
import br.com.acertsis.loja.service.ClienteService;
import br.com.acertsis.loja.service.ContratoService;
import br.com.acertsis.loja.service.PagamentoRecorrenciaService;
import br.com.acertsis.loja.service.PagamentoService;
import br.com.acertsis.loja.service.PesquisarCepService;
import br.com.acertsis.loja.service.VendaService;
import br.com.acertsis.loja.service.email.EmailService;
import br.com.acertsis.loja.service.email.FreemarkerService;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolationException;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.stereotype.Component;

@Component
@Named
@ViewScoped
public class PlanoMb extends AbstractManagedBean implements Serializable {

    private Logger logger = Logger.getGlobal();
    @Inject
    private PlanoDAO planoDAO;
    @Getter
    @Setter
    private List<Plano> listPlano;
    @Getter
    @Setter
    private Long idPlano;
    @Getter
    @Setter
    private Plano plano;
    @Inject
    private PagamentoService pagamentoService;
    @Setter
    @Getter
    private boolean planoAceito;
    @Getter
    @Setter
    private PessoaEnum tipoPessoa;
    @Getter
    @Setter
    private FinalizarCompraDTO finalizarCompraDTO;
    @Getter
    @Setter
    private StreamedContent downloadBoleto;
    @Getter
    @Setter
    private Produto produtoSelecionado;
    @Inject
    private ContratoService contratoService;
    @Inject
    private ClienteService clienteService;
    @Inject
    private PagamentoRecorrenciaService pagamentoRecorrenciaService;
    @Inject
    private VendaService vendaService;
    @Inject
    private PesquisarCepService pesquisarCepService;

    @Inject
    private EmailService emailService;
    @Inject
    private FreemarkerService freemarkerService;

    @PostConstruct
    private void init() {
        this.limparPlanoSelecionado();
        this.listPlano = new ArrayList<Plano>();
        this.tipoPessoa = PessoaEnum.JURIDICA;
        this.finalizarCompraDTO = new FinalizarCompraDTO();
        this.finalizarCompraDTO.setPlanoSelecionado(new Plano());
    }

    public void doItemPlano() {
        if (idPlano != null) {
            this.plano = planoDAO.findById(idPlano).orElse(new Plano());
        }
        if (plano.getListProduto() == null) {
            plano.setListProduto(new HashSet<>());
        }
        if (plano.getModulos() == null) {
            plano.setModulos(new HashSet<>());
        }
    }

    public void abrirModalFinalizarPagamento() {
        finalizarCompraDTO.setPlanoSelecionado(this.plano);
        finalizarCompraDTO.setDataCompra(LocalDate.now());
        finalizarCompraDTO.setMeioPagamento(MeioPagamentoEnum.BOLETO);
        finalizarCompraDTO.setPessoaEnum(this.tipoPessoa);
        PrimeFaces.current().executeScript("comprar();");
    }

    public void onSelectProduto() {
        this.limparPlanoSelecionado();
        if (this.produtoSelecionado != null) {
            this.listPlano = this.planoDAO.findPlanosHabilitadosByProduto(this.produtoSelecionado).orElse(new ArrayList<>());
        }
    }

    public void finalizarPagamento() {

        try {
            Cliente cliente = clienteService.cadastrarCliente(finalizarCompraDTO);

            if (finalizarCompraDTO.getMeioPagamento().equals(MeioPagamentoEnum.CARTAO_CREDITO)) {
                CartaoCreditoDTO cartao = new CartaoCreditoDTO();
                cartao.setTitular(finalizarCompraDTO.getTitularCartao());
                cartao.setBandeira(BrandCardEnum.valueOf(finalizarCompraDTO.getBandeira()));
                cartao.setCodSeguranca(finalizarCompraDTO.getCodigoSegCartao().toString());
                cartao.setAnoVencimento(finalizarCompraDTO.getAnoVencimentoCartao().toString());
                cartao.setMesVencimento(finalizarCompraDTO.getMesVencimentoCartao().toString());
                cartao.setNumero(finalizarCompraDTO.getNumeroCartao());
                vendaService.pagarCartaoCredito(cartao, cliente, plano, MeioPagamentoEnum.CARTAO_CREDITO, finalizarCompraDTO.getParcelas());
                PrimeFaces.current().executeScript("startSucessoCartao();");
            } else {
                //FileInputStream f = new FileInputStream(new File("/opt/boleto.pdf"));
                //byte[] bytes = this.pagamentoService.gerarBoleto(contrato.getPlano(), cliente, contrato.mensalidades.get(1), finalizarCompraDTO);
                byte[] bytes = vendaService.pagarBoleto(cliente, finalizarCompraDTO.getPlanoSelecionado(), MeioPagamentoEnum.BOLETO, finalizarCompraDTO.getParcelas());
                ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                this.downloadBoleto = new DefaultStreamedContent(bais, "application/pdf", "boletoAcnf.pdf");
                PrimeFaces.current().executeScript("imprimirBoleto();");
            }

        } catch (BusinessException e) {
            PrimeFaces.current().executeScript("erroPagamento();");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
            Logger.getLogger(PlanoMb.class.getName()).log(Level.SEVERE, null, e);
        } catch (ConstraintViolationException ev) {
            PrimeFaces.current().executeScript("erroPagamento();");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ev.getCause().getMessage()));
            Logger.getLogger(PlanoMb.class.getName()).log(Level.SEVERE, null, ev);
        } catch (Exception ex) {
            PrimeFaces.current().executeScript("erroPagamento();");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage()));
            Logger.getLogger(PlanoMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addTipoPagamento(String pagamento) {
        this.finalizarCompraDTO.setMeioPagamento(MeioPagamentoEnum.valueOf(pagamento));
    }

    private void limparPlanoSelecionado() {
        plano = new Plano();
        plano.setListProduto(new HashSet<>());
        plano.setModulos(new HashSet<>());
        plano.setValorPlano(0.00);
    }

    public void buscarEnderecoPorCep(AjaxBehaviorEvent event) {
        if (this.finalizarCompraDTO != null && this.finalizarCompraDTO.getCep() != null) {
            Map<String, String> maps = this.pesquisarCepService.pesquisar(this.finalizarCompraDTO.getCep());
            finalizarCompraDTO.setBairro(maps.get("bairro"));
            finalizarCompraDTO.setCidade(maps.get("localidade"));
            finalizarCompraDTO.setEstado(maps.get("uf"));
            finalizarCompraDTO.setEndereco(maps.get("logradouro"));
        }
    }

}
