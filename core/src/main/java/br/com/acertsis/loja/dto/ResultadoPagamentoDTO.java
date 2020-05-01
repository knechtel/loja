/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.dto;

import br.com.acertsis.loja.entity.MeioPagamentoEnum;
import lombok.Data;

@Data
public class ResultadoPagamentoDTO {

    public static final String ERROR = "ERROR";

    public static final String SUCCESS = "SUCCESS";

    private String tipo;

    private MeioPagamentoEnum meioPagamentoEnum;

    private String mensagem;
}
