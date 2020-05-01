package br.com.acertsis.loja.service;

import br.com.acertsis.loja.entity.PessoaEnum;
import br.com.acertsis.loja.entity.PessoaFisica;
import br.com.acertsis.loja.entity.PessoaJuridica;
import br.com.acertsis.loja.util.Util;
import cieloecommerce.sdk.ecommerce.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TesteRecorrencia {

    @Inject
    private PessoaJuridicaService pjs;

    @Inject
    private PessoaFisicaService pfs;


    @Test
    public void testRecorrencia() {

        PessoaJuridica pjParceiro1 = new PessoaJuridica();
        pjParceiro1.setCnpj("25.135.171/0001-59");
        pjParceiro1.setNomeFantasia("Acertsis");
        pjParceiro1.setEmail("fabrica@acertsis.com.br");
        pjParceiro1.setRazaoSocial("Acertsis Desenvolvimento De Sistemas Ltda");
        pjParceiro1.setTipoPessoa(PessoaEnum.JURIDICA);
        pjParceiro1 = pjs.incluirPessoa(pjParceiro1);

        PessoaJuridica pjParceiro2 = new PessoaJuridica();
        pjParceiro2.setCnpj("24.078.789/0001-61");
        pjParceiro2.setNomeFantasia("AR Poa Certificacao Digital");
        pjParceiro2.setEmail("teste@arpoa.com.br");
        pjParceiro2.setRazaoSocial("AR Poa Certificacao Digital EIRELI");
        pjParceiro2.setTipoPessoa(PessoaEnum.JURIDICA);
        pjParceiro2 = pjs.incluirPessoa(pjParceiro2);

        PessoaJuridica pjParceiro3 = new PessoaJuridica();
        pjParceiro3.setCnpj("03.151.200/0001-33");
        pjParceiro3.setNomeFantasia("Ar Contadores");
        pjParceiro3.setEmail("teste@arcontadores.com.br");
        pjParceiro3.setRazaoSocial("Ar Contadores Certificacao Digital Ltda");
        pjParceiro3.setTipoPessoa(PessoaEnum.JURIDICA);
        pjParceiro2 = pjs.incluirPessoa(pjParceiro3);

        PessoaJuridica pjCliente1 = new PessoaJuridica();
        pjCliente1.setCnpj("00.000.000/0000-00");
        pjCliente1.setNomeFantasia("CLIENTE 1");
        pjCliente1.setEmail("cliente1@teste.com.br");
        pjCliente1.setRazaoSocial("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        pjCliente1.setTipoPessoa(PessoaEnum.JURIDICA);
        pjCliente1 = pjs.incluirPessoa(pjCliente1);

        PessoaJuridica pjCliente2 = new PessoaJuridica();
        pjCliente2.setCnpj("99.999.999/9999-99");
        pjCliente2.setNomeFantasia("CLIENTE 2");
        pjCliente2.setEmail("cliente2@teste.com.br");
        pjCliente2.setRazaoSocial("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        pjCliente2.setTipoPessoa(PessoaEnum.JURIDICA);
        pjCliente1 = pjs.incluirPessoa(pjCliente2);

        PessoaFisica pessoaFisica1 = new PessoaFisica();
        pessoaFisica1.setNome("Pessoa 1");
        pessoaFisica1.setCpf("999.999.999-99");
        pessoaFisica1.setDtNascimento(Util.converteData("1979-02-08"));
        pessoaFisica1.setEmail("pessoa1@teste.com.br");
        pessoaFisica1.setTipoPessoa(PessoaEnum.FISICA);
        pessoaFisica1 = pfs.incluirPessoa(pessoaFisica1);

        PessoaFisica pessoaFisica2 = new PessoaFisica();
        pessoaFisica2.setNome("Pessoa 2");
        pessoaFisica2.setCpf("999.999.999-99");
        pessoaFisica2.setDtNascimento(Util.converteData("1979-02-08"));
        pessoaFisica2.setEmail("pessoa2@teste.com.br");
        pessoaFisica2.setTipoPessoa(PessoaEnum.FISICA);
        pessoaFisica2 = pfs.incluirPessoa(pessoaFisica2);

        PessoaFisica pessoaFisica3 = new PessoaFisica();
        pessoaFisica3.setNome("Pessoa 3");
        pessoaFisica3.setCpf("999.999.999-99");
        pessoaFisica3.setDtNascimento(Util.converteData("1979-02-08"));
        pessoaFisica3.setEmail("pessoa3@teste.com.br");
        pessoaFisica3.setTipoPessoa(PessoaEnum.FISICA);
        pessoaFisica3 = pfs.incluirPessoa(pessoaFisica3);


        
        Sale sale = new Sale("999999999");
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

        PagamentoRecorrenciaService prs = new PagamentoRecorrenciaService();

        System.out.println(sale.toString());
        //prs.incluirPagamento("999999999");



    }

}
