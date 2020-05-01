package br.com.acertsis.loja;

import br.com.acertsis.loja.acesso.nubo.request.RequestException;
import br.com.acertsis.loja.dto.FinalizarCompraDTO;
import br.com.acertsis.loja.entity.*;
import br.com.acertsis.loja.service.*;
import br.com.acertsis.loja.util.Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestGeraNumContrato {


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
    
    @Autowired
    TelefoneService telefoneService;

    @Autowired
    ContaPlataformaService plataformaService;


    @Test
    public void testGerarNumContrato() {

        /*PessoaJuridica pjParceiro1 = new PessoaJuridica();
        pjParceiro1.setIdPessoa(1L);
        pjParceiro1.setCnpj("25.135.171/0001-59");
        pjParceiro1.setNomeFantasia("Acertsis");
        pjParceiro1.setEmail("fabrica@acertsis.com.br");
        pjParceiro1.setRazaoSocial("Acertsis Desenvolvimento De Sistemas Ltda");
        pjParceiro1.setTipoPessoa(PessoaEnum.JURIDICA);
        pjParceiro1 = pessoaJuridicaService.incluirPessoa(pjParceiro1);
        
        Telefone tel1 = new Telefone();
        tel1.setNumero("99999999");
        tel1.setTipoTelefone(TelefoneEnum.CELULAR);
        tel1.setPessoa(pjParceiro1);
        tel1 = telefoneService.manterTelefone(tel1);

        PessoaJuridica pjParceiro2 = new PessoaJuridica();
        pjParceiro2.setIdPessoa(2L);
        pjParceiro2.setCnpj("24.078.789/0001-61");
        pjParceiro2.setNomeFantasia("AR Poa Certificacao Digital");
        pjParceiro2.setEmail("teste@arpoa.com.br");
        pjParceiro2.setRazaoSocial("AR Poa Certificacao Digital EIRELI");
        pjParceiro2.setTipoPessoa(PessoaEnum.JURIDICA);
        pjParceiro2 = pessoaJuridicaService.incluirPessoa(pjParceiro2);
        
        Telefone tel2 = new Telefone();
        tel2.setNumero("99999999");
        tel2.setTipoTelefone(TelefoneEnum.CELULAR);
        tel2.setPessoa(pjParceiro2);
        tel2 = telefoneService.manterTelefone(tel2);

        PessoaJuridica pjParceiro3 = new PessoaJuridica();
        pjParceiro3.setIdPessoa(3L);
        pjParceiro3.setCnpj("03.151.200/0001-33");
        pjParceiro3.setNomeFantasia("Ar Contadores");
        pjParceiro3.setEmail("teste@arcontadores.com.br");
        pjParceiro3.setRazaoSocial("Ar Contadores Certificacao Digital Ltda");
        pjParceiro3.setTipoPessoa(PessoaEnum.JURIDICA);
        pjParceiro3 = pessoaJuridicaService.incluirPessoa(pjParceiro3);
        
        Telefone tel3 = new Telefone();
        tel3.setNumero("99999999");
        tel3.setTipoTelefone(TelefoneEnum.CELULAR);
        tel3.setPessoa(pjParceiro3);
        tel3 = telefoneService.manterTelefone(tel3);

        PessoaJuridica pjCliente1 = new PessoaJuridica();
        pjCliente1.setIdPessoa(4L);
        pjCliente1.setCnpj("00.000.000/0000-00");
        pjCliente1.setNomeFantasia("CLIENTE 1");
        pjCliente1.setEmail("cliente1@teste.com.br");
        pjCliente1.setRazaoSocial("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        pjCliente1.setTipoPessoa(PessoaEnum.JURIDICA);
        pjCliente1 = pessoaJuridicaService.incluirPessoa(pjCliente1);
        
        Telefone tel4 = new Telefone();
        tel4.setNumero("99999999");
        tel4.setTipoTelefone(TelefoneEnum.CELULAR);
        tel4.setPessoa(pjCliente1);
        tel4 = telefoneService.manterTelefone(tel4);
        

        PessoaJuridica pjCliente2 = new PessoaJuridica();
        pjCliente2.setIdPessoa(5L);
        pjCliente2.setCnpj("99.999.999/9999-99");
        pjCliente2.setNomeFantasia("CLIENTE 2");
        pjCliente2.setEmail("cliente2@teste.com.br");
        pjCliente2.setRazaoSocial("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        pjCliente2.setTipoPessoa(PessoaEnum.JURIDICA);
        pjCliente2 = pessoaJuridicaService.incluirPessoa(pjCliente2);
        
        Telefone tel5 = new Telefone();
        tel5.setNumero("99999999");
        tel5.setTipoTelefone(TelefoneEnum.CELULAR);
        tel5.setPessoa(pjCliente2);
        tel5 = telefoneService.manterTelefone(tel5);

        PessoaFisica pessoaFisica1 = new PessoaFisica();
        pessoaFisica1.setIdPessoa(6L);
        pessoaFisica1.setNome("Pessoa 1");
        pessoaFisica1.setCpf("000.000.000-00");
        pessoaFisica1.setDtNascimento(Util.converteData("1979-03-08"));
        pessoaFisica1.setEmail("pessoa1@teste.com.br");
        pessoaFisica1.setTipoPessoa(PessoaEnum.FISICA);
        pessoaFisica1 = pessoaFisicaService.incluirPessoa(pessoaFisica1);
        
        Telefone tel6 = new Telefone();
        tel6.setNumero("99999999");
        tel6.setTipoTelefone(TelefoneEnum.CELULAR);
        tel6.setPessoa(pessoaFisica1);
        tel6 = telefoneService.manterTelefone(tel6);

        PessoaFisica pessoaFisica2 = new PessoaFisica();
        pessoaFisica2.setIdPessoa(7L);
        pessoaFisica2.setNome("Pessoa 2");
        pessoaFisica2.setCpf("111.111.111-11");
        pessoaFisica2.setDtNascimento(Util.converteData("1979-03-08"));
        pessoaFisica2.setEmail("pessoa2@teste.com.br");
        pessoaFisica2.setTipoPessoa(PessoaEnum.FISICA);
        pessoaFisica2 = pessoaFisicaService.incluirPessoa(pessoaFisica2);
        
        Telefone tel7 = new Telefone();
        tel7.setNumero("99999999");
        tel7.setTipoTelefone(TelefoneEnum.CELULAR);
        tel7.setPessoa(pessoaFisica2);
        tel7 = telefoneService.manterTelefone(tel7);

        PessoaFisica pessoaFisica3 = new PessoaFisica();
        pessoaFisica3.setIdPessoa(8L);
        pessoaFisica3.setNome("Pessoa 3");
        pessoaFisica3.setCpf("222.222.222-22");
        pessoaFisica3.setDtNascimento(Util.converteData("1979-03-08"));
        pessoaFisica3.setEmail("pessoa3@teste.com.br");
        pessoaFisica3.setTipoPessoa(PessoaEnum.FISICA);
        pessoaFisica3 = pessoaFisicaService.incluirPessoa(pessoaFisica3);
        
        Telefone tel8 = new Telefone();
        tel8.setNumero("99999999");
        tel8.setTipoTelefone(TelefoneEnum.CELULAR);
        tel8.setPessoa(pessoaFisica3);
        tel8 = telefoneService.manterTelefone(tel8);

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

        Parceiro parceiro2 = new Parceiro();
        parceiro2.setIdParceiro(2L);
        parceiro2.setPessoa(pjParceiro2);
        parceiro2.setDominio("teste");
        parceiro2.setDtSolicitacao(LocalDate.now());
        parceiro2.setDtLiberacao(LocalDate.now());
        parceiro2.setRaiz(false);
        parceiro2.setHabilitado(true);
        parceiro2.setResponsavel(pessoaFisica2);
        parceiro2.setTipoPessoa(PessoaEnum.JURIDICA);
        parceiro2 = parceiroService.manterParceiro(parceiro2);

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
        //regra = regraComissaoService.manterRegraComissao(regra);

        Plano plano = new Plano();
        plano.setIdPlano(1L);
        plano.setHabilitado(true);
        plano.setDescricao("TESTE");
        plano.setValorPlano(50);
        plano.setDtCriacao(LocalDate.now());
        plano.setDtInicio(LocalDate.now());
        plano.setValidade(PeriodoEnum.ANUAL);
        plano = planoService.manterPlano(plano);

        FinalizarCompraDTO finalizar = new FinalizarCompraDTO();
        //finalizar.setCnpj("88.888.888/8888-88");
        //finalizar.setRazaoSocial("testando PessoaJuridica");



        finalizar.setCpf("885.014.931-04");
        finalizar.setNome("Fernando Maciel Logrado");
        finalizar.setDatanasc(Util.converteData("1979-02-07"));


        finalizar.setPessoaEnum(PessoaEnum.FISICA);

        finalizar.setCep("40000-000");
        finalizar.setCidade("testado");
        finalizar.setEndereco("testado");
        finalizar.setEstado("testado");
        finalizar.setBairro("testado");
        finalizar.setComplemento("testado");
        finalizar.setNumero("44444444");
        finalizar.setTelefone("4444444444444444444");
        finalizar.setEmail("aff@teste.com.br");
        finalizar.setParceiro(parceiro2);


        RegraComissao r = regraComissaoService.buscarComissaoAtiva(parceiro, LocalDate.now()).orElse(new RegraComissao());

        //Cliente n = clienteService.cadastrarCliente(finalizar);

        //Contrato contrato = contratoService.gerarContratacao(cliente, plano, MeioPagamentoEnum.CARTAO_CREDITO, new CartaoCreditoDTO("5502093772136567", "FERNANDO M LOGRADO", "02", "2027", "738", BrandCardEnum.Master));

       /* Contrato contrato = new Contrato();
        contrato.setIdContrato(1L);
        contrato.setCliente(cliente);
        contrato.setRegraComissao(regra);
        contrato.setPlano(plano);
        contrato.setDtCriacao(LocalDate.now());
        contrato.setNumParcelas(plano.getValidade().getPeriodo());
        contrato.setValorTotal(regra.getTxAdesao() + plano.getValorPlano());
        contrato.setTipoPagamento(MeioPagamentoEnum.BOLETO);
        contrato.setTeste(Integer.toString(Integer.parseInt(LocalDate.now().getYear() + contrato.getIdContrato().toString())));
        contrato = contratoService.manterContato(contrato);
        */


        plataformaService.generateNewAccount();


        System.out.println("teste");

    }


}
