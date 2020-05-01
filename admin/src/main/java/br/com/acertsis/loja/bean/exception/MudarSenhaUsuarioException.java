package br.com.acertsis.loja.bean.exception;

import lombok.Getter;

public class MudarSenhaUsuarioException extends Exception {

    @Getter
    private final TipoExcecao tipo;

    public MudarSenhaUsuarioException(TipoExcecao tipo, String msg) {
        super(msg);
        this.tipo = tipo;
    }

    public static enum TipoExcecao {
        SENHA_ATUAL_INVALIDA,
        SENHA_CONFIRMACAO_INVALIDA
    }

}
