/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.service;

import br.com.acertsis.loja.dto.FinalizarCompraDTO;
import br.com.acertsis.loja.entity.MeioPagamentoEnum;
import br.com.acertsis.loja.entity.PessoaEnum;
import br.com.acertsis.loja.entity.Plano;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import javax.inject.Inject;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PagamentoServiceTest {

    @Inject
    private PagamentoService pagamentoService;

    //@Test
    public void testPagarBoletoNaoParceiro() throws Exception {
        Plano plano = new Plano();
        plano.setIdPlano(1L);
        FinalizarCompraDTO finalizarCompraDTO = new FinalizarCompraDTO();
        finalizarCompraDTO.setPessoaEnum(PessoaEnum.FISICA);
        finalizarCompraDTO.setPlanoSelecionado(plano);
        finalizarCompraDTO.setMeioPagamento(MeioPagamentoEnum.BOLETO);
        finalizarCompraDTO.setNome("Teste Moacir da Roza Flores");
        finalizarCompraDTO.setEmail("moacirrf@gmail.com");
        finalizarCompraDTO.setDataVencimento(LocalDate.of(2019, 9, 9));
        finalizarCompraDTO.setCpf("83413430025");
        finalizarCompraDTO.setTelefone("51997232941");
        //
        finalizarCompraDTO.setEndereco("Mauricio Sirotski Sobrinho");
        finalizarCompraDTO.setCidade("Viamão");
        finalizarCompraDTO.setEstado("RS");
        finalizarCompraDTO.setCep("94430050");

        byte[] bytes = null;//pagamentoService.pagarBoletoNaoParceiro(finalizarCompraDTO);
        Assert.assertNotNull(bytes);
        FileOutputStream fo = new FileOutputStream(new File("/home/moacir/teste.pdf"));

        fo.write(bytes);
        fo.flush();
        fo.close();
        System.out.println(new String(bytes));
    }

}
