/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.bean.admin.venda;

import br.com.acertsis.loja.dto.CartaoCreditoDTO;
import br.com.acertsis.loja.entity.Cliente;
import br.com.acertsis.loja.entity.MeioPagamentoEnum;
import br.com.acertsis.loja.entity.Plano;
import br.com.acertsis.loja.entity.Produto;
import br.com.acertsis.loja.entity.RegraComissao;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;


public class DadosVendaDTO implements Serializable {

    @Getter
    @Setter
    private boolean aceito;
    @Getter
    @Setter
    private Cliente clienteSelecionado;
    @Getter
    @Setter
    private Plano planoSelecionado;
    @Getter
    @Setter
    private Produto produtoSelecionado;
    @Setter
    @Getter
    private MeioPagamentoEnum pagamentoEnum;
    @Getter
    @Setter
    private RegraComissao regraComissao;
    @Getter
    @Setter
    private CartaoCreditoDTO cartaoCreditoDTO;
    @Getter
    @Setter
    private int numParcelas;


}
