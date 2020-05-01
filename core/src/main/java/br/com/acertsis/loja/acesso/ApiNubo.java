package br.com.acertsis.loja.acesso;

import br.com.acertsis.loja.acesso.nubo.Account;
import br.com.acertsis.loja.acesso.nubo.Credentials;
import br.com.acertsis.loja.acesso.nubo.ListResponse;
import br.com.acertsis.loja.acesso.nubo.Login;
import br.com.acertsis.loja.acesso.nubo.request.AccountRequest;
import br.com.acertsis.loja.acesso.nubo.request.AuthenticateRequest;
import br.com.acertsis.loja.acesso.nubo.request.ListAccountRequest;
import br.com.acertsis.loja.acesso.nubo.request.RequestException;
import org.apache.http.client.HttpClient;

import java.io.IOException;

/**
 * Integração NUBO
 */

public class ApiNubo {
    private HttpClient httpClient;

    private static final String GETAUTHTOKEN = "https://api.nubo.solutions/get-auth-token?target=";
    private static final String CORE = "https://api.nubo.solutions/core/v1/";
    private Credentials credentials;

    public void authenticate(Login login) throws IOException, RequestException {
        AuthenticateRequest authenticateRequest = new AuthenticateRequest(GETAUTHTOKEN, null);
        authenticateRequest.setHttpClient(httpClient);
        Credentials credentials = authenticateRequest.execute(login);
        this.credentials = credentials;
    }

    public ListResponse listAccounts() throws IOException, RequestException {
        ListAccountRequest accountRequest = new ListAccountRequest(CORE, this.credentials);
        accountRequest.setHttpClient(httpClient);
        ListResponse listAccount = accountRequest.execute(credentials);
        return listAccount;
    }

    public Account createAccount(Account newAccount) throws IOException, RequestException {
        AccountRequest accountRequest = new AccountRequest(CORE, this.credentials);
        accountRequest.setHttpClient(httpClient);
        Account account = accountRequest.execute(newAccount);
        return account;
    }
}
