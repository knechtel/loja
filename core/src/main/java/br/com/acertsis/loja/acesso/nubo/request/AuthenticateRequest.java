package br.com.acertsis.loja.acesso.nubo.request;

import br.com.acertsis.loja.acesso.nubo.Credentials;
import br.com.acertsis.loja.acesso.nubo.Login;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.util.Base64;

public class AuthenticateRequest extends AbstractRequest<Login, Credentials> {

    public AuthenticateRequest(String enviroment, Credentials credentials) {
        super(enviroment, credentials);
    }

    @Override
    public Credentials execute(Login param) throws IOException, RequestException {
        String url = enviroment + param.getSubdominio();

        String authString = param.getUser() + ":" + param.getPassword();
        String authStringEnc = Base64.getEncoder().encodeToString(authString.getBytes());

        HttpGet request = new HttpGet(url);
        request.addHeader("Accept", "application/json");
        request.addHeader("Content-Type", "application/json");
        request.addHeader("Authorization", "Basic "+ authStringEnc);
        HttpResponse response = sendRequest(request);

        return readResponse(response, Credentials.class);
    }
}
