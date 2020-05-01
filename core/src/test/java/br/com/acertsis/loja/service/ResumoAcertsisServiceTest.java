/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.service;

import br.com.acertsis.loja.dao.ContratoDAO;
import br.com.acertsis.loja.entity.Cliente;
import br.com.acertsis.loja.entity.Contrato;
import br.com.acertsis.loja.entity.MeioPagamentoEnum;
import br.com.acertsis.loja.entity.Mensalidade;
import br.com.acertsis.loja.entity.PagamentoBoleto;
import br.com.acertsis.loja.entity.Parceiro;
import br.com.acertsis.loja.entity.StatusContratoEnum;
import br.com.acertsis.loja.entity.StatusPagamentoEnum;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResumoAcertsisServiceTest {

    @Autowired
    private ResumoAcertsisService resumoAcertsisService;

    @MockBean
    private ContratoDAO mockContratoDao;

    private List<Contrato> contratos;

    @Before
    public void initContratos() {
        Contrato contrato = this.createContrato(StatusContratoEnum.ATIVO, 1L);
        contrato.getMensalidades().add(this.createMensalidade(contrato, StatusPagamentoEnum.QUITADO));

        this.contratos = new ArrayList<>();
        this.contratos.add(contrato);
        Mockito.when(mockContratoDao.findByStatus(StatusContratoEnum.ATIVO)).thenReturn(contratos);
    }

    private Contrato createContrato(StatusContratoEnum status, Long idParceiro) {
        Contrato contrato = new Contrato();
        contrato.setCliente(new Cliente());
        contrato.getCliente().setIdCliente(idParceiro + 1);
        contrato.getCliente().setParceiro(createParceiro(idParceiro));
        contrato.setStatus(status);
        contrato.setMensalidades(new ArrayList<>());
        return contrato;
    }

    private Mensalidade createMensalidade(Contrato contrato, StatusPagamentoEnum statusPagamentoEnum) {
        Mensalidade mensalidade = new Mensalidade(1, 200.50, statusPagamentoEnum, LocalDate.MIN, contrato, MeioPagamentoEnum.BOLETO);
        mensalidade.setPagamento(new PagamentoBoleto());
        mensalidade.getPagamento().setMeioPagamentoEnum(MeioPagamentoEnum.BOLETO);
        mensalidade.getPagamento().setMensalidade(mensalidade);
        return mensalidade;
    }

    private Parceiro createParceiro(Long idParceiro) {
        Parceiro p = new Parceiro();
        p.setIdParceiro(idParceiro);
        return p;
    }

    @Test
    public void test_totalVendasRealizadas() {
        Long idParceiro1 = 1L;


        Contrato c1 = createContrato(StatusContratoEnum.ATIVO, idParceiro1);
        c1.getMensalidades().add(createMensalidade(c1, StatusPagamentoEnum.QUITADO));

        Contrato c2 = createContrato(StatusContratoEnum.ATIVO, idParceiro1);
        c2.getMensalidades().add(createMensalidade(c2, StatusPagamentoEnum.QUITADO));

        Contrato c3 = createContrato(StatusContratoEnum.ATIVO, idParceiro1);
        c3.getMensalidades().add(createMensalidade(c3, StatusPagamentoEnum.AGUARDANDO));

        List<Contrato> contratos = java.util.Arrays.asList(c1, c2, c3);
        long t = this.resumoAcertsisService.totalVendasRealizadas(contratos);
        Assert.assertTrue("Esperado 2 mas retornou: " + t, t == 2);
    }

    @Test
    public void test_agruparPorParceiro() {
        Long idParceiro1 = 1L;
        Long idParceiro2 = 2L;
        Long idParceiro3 = 3L;
        Long idParceiro4 = 4L;

        Contrato c1 = createContrato(StatusContratoEnum.ATIVO, idParceiro1);
        Contrato c2 = createContrato(StatusContratoEnum.ATIVO, idParceiro1);
        Contrato c3 = createContrato(StatusContratoEnum.ATIVO, idParceiro2);
        Contrato c4 = createContrato(StatusContratoEnum.ATIVO, idParceiro3);
        Contrato c5 = createContrato(StatusContratoEnum.ATIVO, idParceiro4);

        List<Contrato> contratos = java.util.Arrays.asList(c1, c2, c3, c4, c5);

        Map<Parceiro, List<Contrato>> grouped = this.resumoAcertsisService.agruparPorParceiro(contratos);
        Assert.assertTrue(grouped.get(createParceiro(idParceiro1)).size() == 2);
        Assert.assertTrue(grouped.get(createParceiro(idParceiro2)).size() == 1);
        Assert.assertTrue(grouped.get(createParceiro(idParceiro3)).size() == 1);
        Assert.assertTrue(grouped.get(createParceiro(idParceiro4)).size() == 1);
    }

}
