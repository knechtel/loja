package br.com.acertsis.loja.acesso.nubo.request;

public class RequestException extends Exception{
    private final ApiNuboError error;

    public RequestException(String msg, ApiNuboError error, Throwable cause) {
        super(msg,cause);
        this.error = error;
    }

    public ApiNuboError getError() {
        return error;
    }
}
