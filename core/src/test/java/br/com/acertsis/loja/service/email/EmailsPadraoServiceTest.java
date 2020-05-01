/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.service.email;

import br.com.acertsis.loja.dao.ContratoDAO;
import br.com.acertsis.loja.entity.Cliente;
import br.com.acertsis.loja.entity.Contrato;
import br.com.acertsis.loja.entity.Mensalidade;
import br.com.acertsis.loja.entity.Parceiro;
import br.com.acertsis.loja.entity.PessoaFisica;
import br.com.acertsis.loja.entity.Plano;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailsPadraoServiceTest {

    @Inject
    private EmailsPadraoService emailsPadraoService;

    private Cliente cliente;

    private Parceiro parceiro;

    private Contrato contrato;

    @Inject
    private ContratoDAO contratoDAO;

    @Before
    public void setUp() {
        cliente = new Cliente();
        cliente.setPessoa(new PessoaFisica());
        ((PessoaFisica) cliente.getPessoa()).setNome("Moacir da Roza Flores");
        ((PessoaFisica) cliente.getPessoa()).setEmail("moacir.flores@acertsis.com.br");
        //
        parceiro = new Parceiro();
        parceiro.setPessoa(new PessoaFisica());
        ((PessoaFisica) parceiro.getPessoa()).setNome("Moacir da Roza Flores");
        ((PessoaFisica) parceiro.getPessoa()).setEmail("moacir.flores@acertsis.com.br");

        //
        contrato = new Contrato();
        contrato.setCliente(cliente);
        contrato.setPlano(new Plano());
        contrato.getPlano().setNome("Plano Test");


    }

    @Test
    public void testnovaCompraClienteBoleto() throws Exception {
        Contrato contrao = contratoDAO.findById(20190000050L).get();
        this.emailsPadraoService.novaCompraCliente(contrao.getCliente(), contrato);
    }

    @Test
    public void testSolicitacaoParceria() throws Exception {
        emailsPadraoService.solicitacaoParceria(parceiro);
    }

    @Test
    public void testConfirmacaoParceria() throws Exception {
        emailsPadraoService.confirmacaoParceria(parceiro, "parceiro", "parceiro");
    }

    @Test
    public void testContratoAprovado() throws Exception {
        emailsPadraoService.contratoAprovado(contrato);
    }

    @Test
    public void testConfirmacaoPagamentoMensalidade() throws Exception {
        Mensalidade m = new Mensalidade();
        m.setContrato(contrato);
        emailsPadraoService.confirmacaoPagamentoMensalidade(cliente, m);
    }

    @Test
    public void testMensalidadeNaoPaga() throws Exception {
    }

}
