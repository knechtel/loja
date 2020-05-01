package br.com.acertsis.loja.acesso.nubo.request;

import br.com.acertsis.loja.acesso.nubo.Credentials;
import br.com.acertsis.loja.acesso.nubo.ListResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.util.Base64;

public class ListAccountRequest extends AbstractRequest<Credentials, ListResponse> {
    public static final String ACCOUNTS = "accounts";

    public ListAccountRequest(String enviroment, Credentials credentials) {
        super(enviroment, credentials);

    }

    @Override
    public ListResponse execute(Credentials param) throws IOException, RequestException {
        String url = enviroment + ACCOUNTS;

        String authString = param.getToken() + ": ";
        String authStringEnc = Base64.getEncoder().encodeToString(authString.getBytes());

        HttpGet request = new HttpGet(url);
        request.addHeader("Accept", "application/json");
        request.addHeader("Content-Type", "application/json");
        request.addHeader("Authorization", "Basic " + authStringEnc);

        HttpResponse response = sendRequest(request);

        return readResponse(response, ListResponse.class);
    }

}
