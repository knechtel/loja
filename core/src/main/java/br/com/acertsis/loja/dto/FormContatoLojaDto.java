/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

public class FormContatoLojaDto implements Serializable {

    public enum EnumTipoMensagem {
        DUVIDA("Dúvida"),
        SUGESTAO("Sugestão"),
        COMENTARIO("Comentário"),
        OUTRO("Outro");
        @Getter
        private String label;

        private EnumTipoMensagem(String label) {
            this.label = label;
        }
    }
    @Setter
    @Getter
    private String nome;
    @Setter
    @Getter
    private String email;
    @Setter
    @Getter
    private EnumTipoMensagem tipoMensagem;
    @Setter
    @Getter
    private String mensagem;

}
