package br.com.acertsis.loja;

import br.com.acertsis.loja.entity.*;
import br.com.acertsis.loja.service.*;
import br.com.acertsis.loja.util.Util;
import cieloecommerce.sdk.ecommerce.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRecorrencia {

    @Autowired
    private PessoaJuridicaService pessoaJuridicaService;

    @Autowired
    private PessoaFisicaService pessoaFisicaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ParceiroService parceiroService;

    @Autowired
    private ContratoService contratoService;

    @Autowired
    private MensalidadeService mensalidadeService;

    @Autowired
    PagamentoRecorrenciaService pagamentoRecorrenciaService;

    @Autowired
    RegraComissaoService regraComissaoService;

    @Autowired
    PlanoService planoService;

    @Test
    public void testRecorrencia() {

        PessoaJuridica pjParceiro1 = new PessoaJuridica();
        pjParceiro1.setIdPessoa(1L);
        pjParceiro1.setCnpj("25.135.171/0001-59");
        pjParceiro1.setNomeFantasia("Acertsis");
        pjParceiro1.setEmail("fabrica@acertsis.com.br");
        pjParceiro1.setRazaoSocial("Acertsis Desenvolvimento De Sistemas Ltda");
        pjParceiro1.setTipoPessoa(PessoaEnum.JURIDICA);
        pjParceiro1 = pessoaJuridicaService.incluirPessoa(pjParceiro1);

        PessoaJuridica pjParceiro2 = new PessoaJuridica();
        pjParceiro2.setIdPessoa(2L);
        pjParceiro2.setCnpj("24.078.789/0001-61");
        pjParceiro2.setNomeFantasia("AR Poa Certificacao Digital");
        pjParceiro2.setEmail("teste@arpoa.com.br");
        pjParceiro2.setRazaoSocial("AR Poa Certificacao Digital EIRELI");
        pjParceiro2.setTipoPessoa(PessoaEnum.JURIDICA);
        pjParceiro2 = pessoaJuridicaService.incluirPessoa(pjParceiro2);

        PessoaJuridica pjParceiro3 = new PessoaJuridica();
        pjParceiro3.setIdPessoa(3L);
        pjParceiro3.setCnpj("03.151.200/0001-33");
        pjParceiro3.setNomeFantasia("Ar Contadores");
        pjParceiro3.setEmail("teste@arcontadores.com.br");
        pjParceiro3.setRazaoSocial("Ar Contadores Certificacao Digital Ltda");
        pjParceiro3.setTipoPessoa(PessoaEnum.JURIDICA);
        pjParceiro2 = pessoaJuridicaService.incluirPessoa(pjParceiro3);

        PessoaJuridica pjCliente1 = new PessoaJuridica();
        pjCliente1.setIdPessoa(4L);
        pjCliente1.setCnpj("00.000.000/0000-00");
        pjCliente1.setNomeFantasia("CLIENTE 1");
        pjCliente1.setEmail("cliente1@teste.com.br");
        pjCliente1.setRazaoSocial("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        pjCliente1.setTipoPessoa(PessoaEnum.JURIDICA);
        pjCliente1 = pessoaJuridicaService.incluirPessoa(pjCliente1);

        PessoaJuridica pjCliente2 = new PessoaJuridica();
        pjCliente2.setIdPessoa(5L);
        pjCliente2.setCnpj("99.999.999/9999-99");
        pjCliente2.setNomeFantasia("CLIENTE 2");
        pjCliente2.setEmail("cliente2@teste.com.br");
        pjCliente2.setRazaoSocial("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        pjCliente2.setTipoPessoa(PessoaEnum.JURIDICA);
        pjCliente1 = pessoaJuridicaService.incluirPessoa(pjCliente2);

        PessoaFisica pessoaFisica1 = new PessoaFisica();
        pessoaFisica1.setIdPessoa(6L);
        pessoaFisica1.setNome("Pessoa 1");
        pessoaFisica1.setCpf("000.000.000-00");
        pessoaFisica1.setDtNascimento(Util.converteData("1979-02-08"));
        pessoaFisica1.setEmail("pessoa1@teste.com.br");
        pessoaFisica1.setTipoPessoa(PessoaEnum.FISICA);
        pessoaFisica1 = pessoaFisicaService.incluirPessoa(pessoaFisica1);

        PessoaFisica pessoaFisica2 = new PessoaFisica();
        pessoaFisica2.setIdPessoa(7L);
        pessoaFisica2.setNome("Pessoa 2");
        pessoaFisica2.setCpf("111.111.111-11");
        pessoaFisica2.setDtNascimento(Util.converteData("1979-02-08"));
        pessoaFisica2.setEmail("pessoa2@teste.com.br");
        pessoaFisica2.setTipoPessoa(PessoaEnum.FISICA);
        pessoaFisica2 = pessoaFisicaService.incluirPessoa(pessoaFisica2);

        PessoaFisica pessoaFisica3 = new PessoaFisica();
        pessoaFisica3.setIdPessoa(8L);
        pessoaFisica3.setNome("Pessoa 3");
        pessoaFisica3.setCpf("222.222.222-22");
        pessoaFisica3.setDtNascimento(Util.converteData("1979-02-08"));
        pessoaFisica3.setEmail("pessoa3@teste.com.br");
        pessoaFisica3.setTipoPessoa(PessoaEnum.FISICA);
        pessoaFisica3 = pessoaFisicaService.incluirPessoa(pessoaFisica3);

        Parceiro parceiro = new Parceiro();
        parceiro.setIdParceiro(1L);
        parceiro.setPessoa(pjParceiro1);
        parceiro.setDominio("acertsis");
        parceiro.setDtSolicitacao(LocalDate.now());
        parceiro.setDtLiberacao(LocalDate.now());
        parceiro.setRaiz(true);
        parceiro.setHabilitado(true);
        parceiro.setResponsavel(pessoaFisica2);
        parceiro.setTipoPessoa(PessoaEnum.JURIDICA);
        parceiro = parceiroService.manterParceiro(parceiro);

        Cliente cliente = new Cliente();
        cliente.setIdCliente(1L);
        cliente.setParceiro(parceiro);
        cliente.setPessoa(pjCliente2);
        cliente.setResponsavel(pessoaFisica3);
        cliente = clienteService.manterCliente(cliente);

        RegraComissao regra = new RegraComissao();
        regra.setIdRegraComissao(1L);
        regra.setParceiro(parceiro);
        regra.setDtInicial(LocalDate.now());
        regra.setHabilitado(true);
        regra.setPorcentagem(10.0);
        regra.setTxAdesao(50.0);
        regra.setValor(0.0);
        regra.setModoComissaoParceiro(ModoComissaoParceiroEnum.SOBRE_TODAS_AS_VENDAS);
        regra = regraComissaoService.manterRegraComissao(regra);

        Plano plano = new Plano();
        plano.setIdPlano(1L);
        plano.setHabilitado(true);
        plano.setDescricao("TESTE");
        plano.setValorPlano(50);
        plano.setDtCriacao(LocalDate.now());
        plano.setDtInicio(LocalDate.now());
        plano.setValidade(PeriodoEnum.ANUAL);
        plano = planoService.manterPlano(plano);

        Contrato contrato = new Contrato();
        //contrato.setIdContrato(1L);
        contrato.setCliente(cliente);
        contrato.setRegraComissao(regra);
        contrato.setPlano(plano);
        contrato.setDtCriacao(LocalDate.now());
        contrato.setNumParcelas(plano.getValidade().getPeriodo());
        contrato.setValorTotal(regra.getTxAdesao()+plano.getValorPlano());
        contrato.setTipoPagamento(MeioPagamentoEnum.BOLETO);

        //contrato = contratoService.gerarContratacao(contrato);

        //PagamentoRecorrencia recorrencia = pagamentoRecorrenciaService.gerarRecorrencia(new Mensalidade());


      /*  Mensalidade mensalidade = new Mensalidade();
        mensalidade.setIdMensalidade(1L);
        mensalidade.setContrato(contrato);
        mensalidade.setDtCriacao(LocalDate.now());
        mensalidade.setParcela(1);
        mensalidade.setValor(50);
        //mensalidade = mensalidadeService.manterMensalidade(mensalidade);

       /* Sale sale = new Sale("999999999");
        sale.setCustomer(new Customer("FERNANDO MACIEL LOGRADO"));
        Payment payment = sale.payment(1000);
        payment.setServiceTaxAmount(0);
        payment.setInstallments(1);
        payment.setInterest("ByMerchant");
        payment.setCapture(false);
        payment.setAuthenticate(false);
        payment.setRecurrent(false);
        payment.setCreditCard(new CreditCard("","").setCardNumber("123412******1231")
                                              .setHolder("Teste Holder")
                                              .setExpirationDate("12/20130")
                                              .setCardToken("d37bf475-307d-47be-b50a-8dcc38c5056c")
                                              .setSaveCard(true)
                                              .setBrand("Visa"));

        payment.setProofOfSale("3827556");
        payment.setTid("0504043827555");
        payment.setAuthorizationCode("149867");
        payment.setSoftDescriptor("ACNF");
        payment.setPaymentId("737a8d9a-88fe-4f74-931f-acf81149f4a0");
        payment.setType(Payment.Type.CreditCard);
        payment.setCurrency(Payment.Currency.BRL);
        payment.setCountry("BRA");
        payment.setProvider(Payment.Provider.Simulado);
        payment.setStatus(1);
        payment.setReturnCode("4");
        payment.setReturnMessage("Operation Successful");
        payment.setRecurrentPayment(new RecurrentPayment(true)
                                            .setRecurrentPaymentId("61e5bd30-ec11-44b3-ba0a-56fbbc8274c5")
                                            .setNextRecurrency("2015-11-04")
                                            .setEndDate("2020-08-02")
                                            .setInterval(RecurrentPayment.Interval.Annual));

        PagamentoRecorrencia pagamentoRecorrencia = new PagamentoRecorrencia();
        pagamentoRecorrencia.setMensalidade(mensalidade);
        pagamentoRecorrencia.setMeioPagamentoEnum(MeioPagamentoEnum.CARTAO_CREDITO);
        pagamentoRecorrencia.setDtProximaRecorrencia(Util.converteData(sale.getPayment().getRecurrentPayment().getNextRecurrency()));
        pagamentoRecorrencia.setRecurrentPaymentId(sale.getPayment().getRecurrentPayment().getRecurrentPaymentId());
        pagamentoRecorrencia.setDtFim(Util.converteData(sale.getPayment().getRecurrentPayment().getEndDate()));
        pagamentoRecorrencia.setDtCriacao(LocalDate.now());
        pagamentoRecorrencia.setValorPago(sale.getPayment().getAmount());
        pagamentoRecorrencia.setCodAutorizacao(sale.getPayment().getAuthorizationCode());
        pagamentoRecorrencia.setCodRetorno(sale.getPayment().getReturnCode());
        pagamentoRecorrencia.setIdentificadorPedido(sale.getPayment().getPaymentId());
        pagamentoRecorrencia.setMensagemRetorno(sale.getPayment().getReturnMessage());
        pagamentoRecorrencia.setStatus(sale.getPayment().getStatus());
        pagamentoRecorrencia.setNumeroAutorizacao(sale.getPayment().getAuthorizationCode());
        pagamentoRecorrencia.setTid(sale.getPayment().getTid());
        pagamentoRecorrencia = pagamentoRecorrenciaService.manterPagamento(pagamentoRecorrencia);*/

        System.out.println(contrato.toString());

    }

}
