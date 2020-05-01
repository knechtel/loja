package br.com.acertsis.loja.acesso.nubo.request;

public class ApiNuboError {
    private final Integer code;
    private final String message;

    public ApiNuboError(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
