package br.com.acertsis.loja.service;

import br.com.acertsis.loja.acesso.nubo.Account;
import br.com.acertsis.loja.acesso.nubo.License;
import br.com.acertsis.loja.acesso.nubo.LicensePackage;
import br.com.acertsis.loja.acesso.nubo.request.RequestException;
import br.com.acertsis.loja.dao.PagamentoRecorrenciaDAO;
import br.com.acertsis.loja.dto.CartaoCreditoDTO;
import br.com.acertsis.loja.entity.*;
import br.com.acertsis.loja.exception.BusinessException;
import br.com.acertsis.loja.util.Util;
import cieloecommerce.sdk.Merchant;
import cieloecommerce.sdk.ecommerce.*;
import cieloecommerce.sdk.ecommerce.request.CieloRequestException;
import com.google.common.base.Strings;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PagamentoRecorrenciaService {

    @Autowired
    PagamentoRecorrenciaDAO prDAO;

    @Autowired
    ContratoService contratoService;

    @Autowired
    MensalidadeService mensalidadeService;

    @Autowired
    ClienteService clienteService;

    @Autowired
    ContaPlataformaService contaPlataformaService;

    private static final String SOFTDESCRIPTOR = "ACNF";
    private static final int INSTALLMENTS = 1;
    private static final String MERCHANTID = "0ad0f0b3-5f26-49fd-8378-614fbad14ce4";
    private static final String MERCHANTKEY = "coSbF3fUUun7RjWdlDFljb01gfHr4jCjn9V3FSYm";

    public PagamentoRecorrencia manterPagamento(PagamentoRecorrencia pr){
        return prDAO.save(pr);
    }

    public void gerarRecorrencia(Mensalidade mensalidade, Contrato contrato, Cliente cliente, CartaoCreditoDTO cartao) throws BusinessException {
        if (cliente.getIdCliente() == null || mensalidade.getIdMensalidade() == null || contrato.getPlano().getSku() == null) {
            throw new BusinessException("Erro ao gerar pagamento. Verifique as informações!");
        }

        Integer valor = Util.moneyFormatCielo(mensalidade.getValor());
        String vencimento = Util.formatVencimentoCartaoCielo(cartao.getMesVencimento(), cartao.getAnoVencimento());
        LocalDate dtFimRec = LocalDate.now().plusMonths(contrato.getNumParcelas() - 1);
        String customer;
        String dominio;

        Sale sale = new Sale(contrato.getIdContrato().toString());
        if(PessoaEnum.JURIDICA.equals(cliente.getPessoa().getTipoPessoa())){
            PessoaJuridica pessoaJuridica = (PessoaJuridica)cliente.getPessoa();
            customer = pessoaJuridica.getRazaoSocial();
        } else {
            PessoaFisica pessoaFisica = (PessoaFisica)cliente.getPessoa();
            customer = pessoaFisica.getNome();
        }

        dominio = customer.toLowerCase();
        dominio = dominio.replace(" ", "");
        dominio = dominio.replaceAll("[^a-zZ-Z1-9 ]", "");

        sale.setCustomer(new Customer(customer));

        Payment payment = sale.payment(valor);
        payment.setType(Payment.Type.CreditCard);
        payment.setInstallments(INSTALLMENTS);
        payment.setSoftDescriptor(SOFTDESCRIPTOR);
        payment.setRecurrentPayment(new RecurrentPayment(true)
                                        .setEndDate(dtFimRec.toString())
                                        .setInterval(RecurrentPayment.Interval.Monthly));
        payment.setCreditCard(new CreditCard(cartao.getCodSeguranca(),cartao.getBandeira().toString())
                                        .setCardNumber(cartao.getNumero())
                                        .setHolder(cartao.getTitular())
                                        .setExpirationDate(vencimento)
                                        .setSaveCard(true));

        Merchant merchant = new Merchant(MERCHANTID, MERCHANTKEY);

        try {
            sale = new CieloEcommerce(merchant, Environment.PRODUCTION).createSale(sale);
            Logger.getLogger(PagamentoRecorrencia.class.getName()).info("Cliente: " + cliente.getPessoa().getNome()
                    + " RecurrentPaymentId: " + sale.getPayment().getRecurrentPayment().getRecurrentPaymentId());
            DadosRecorrencia recorrencia = new DadosRecorrencia();
            recorrencia.setContrato(contrato);
            recorrencia.setBandeira(cartao.getBandeira());
            recorrencia.setToken(sale.getPayment().getCreditCard().getCardToken());
            recorrencia.setDtInicio(LocalDate.now());
            recorrencia.setDtFim(Util.converteData(sale.getPayment().getRecurrentPayment().getEndDate()));
            recorrencia.setReasonCode(sale.getPayment().getRecurrentPayment().getReasonCode());
            recorrencia.setReasonMessage(sale.getPayment().getRecurrentPayment().getReasonMessage());
            recorrencia.setRecurrentPaymentId(sale.getPayment().getRecurrentPayment().getRecurrentPaymentId());
            recorrencia.setStatus(sale.getPayment().getStatus());

            PagamentoRecorrencia pagamentoRecorrencia  = new PagamentoRecorrencia();
            pagamentoRecorrencia.setMensalidade(mensalidade);
            pagamentoRecorrencia.setDadosRecorrencia(recorrencia);
            pagamentoRecorrencia.setNumeroAutorizacao(sale.getPayment().getProofOfSale());
            pagamentoRecorrencia.setTid(sale.getPayment().getTid());
            pagamentoRecorrencia.setCodAutorizacao(sale.getPayment().getAuthorizationCode());
            pagamentoRecorrencia.setIdentificadorPedido(sale.getPayment().getPaymentId());
            pagamentoRecorrencia.setStatusTransaction(sale.getPayment().getStatus());
            pagamentoRecorrencia.setCodRetorno(sale.getPayment().getReturnCode());
            pagamentoRecorrencia.setMensagemRetorno(sale.getPayment().getReturnMessage());
            pagamentoRecorrencia.setDtRetorno(Util.converteDataHora(sale.getPayment().getReceivedDate()));
            if(!Strings.isNullOrEmpty(sale.getPayment().getRecurrentPayment().getNextRecurrency())){
                pagamentoRecorrencia.setDtProximaRecorrencia(Util.converteData(sale.getPayment().getRecurrentPayment().getNextRecurrency()));
            }

            pagamentoRecorrencia = prDAO.save(pagamentoRecorrencia);

            if(StatusTransactionEnum.Authorized.getcodigo() == sale.getPayment().getStatus()){
                contrato.setStatus(StatusContratoEnum.PENDENTEATV);
                mensalidade.setStatus(StatusPagamentoEnum.QUITADO);
                mensalidade = mensalidadeService.manterMensalidade(mensalidade);
                contrato = contratoService.manterContato(contrato);

            } else {
                throw new BusinessException("Erro: ->("+ pagamentoRecorrencia.getMensagemRetorno()+").");
            }

        } catch (CieloRequestException e) {
            throw new BusinessException("Erro: Cielo ->("+ e.getError().getCode()+"). Contate o suporte");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*public List<PagamentoRecorrencia> verficaPagamento(){
        return prDAO.findPagamentoAutorizados();
    }*/
}