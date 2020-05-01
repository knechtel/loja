package br.com.acertsis.loja.acesso.nubo.request;

import br.com.acertsis.loja.acesso.nubo.Account;
import br.com.acertsis.loja.acesso.nubo.Credentials;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.util.Base64;

public class AccountRequest extends AbstractRequest<Account, Account> {
    public static final String ACCOUNTS = "accounts";
    private final Credentials credentials;
    private final String enviroment;

    public AccountRequest(String enviroment, Credentials credentials) {
        super(enviroment, credentials);
        this.enviroment = enviroment;
        this.credentials=credentials;
    }

    @Override
    public Account execute(Account param) throws IOException, RequestException {
        String url = enviroment + ACCOUNTS;

        String authString = credentials.getToken() + ": ";
        String authStringEnc = Base64.getEncoder().encodeToString(authString.getBytes());

/*        String test = "{ \"company_name\": \" "+ param.getCompanyName()+ " \"," +
                      "\"subdomain\": \""+param.getSubdomain()+"\","+
                      " \"username\": \""+ param.getUsername()+"\","+
                      "  \"password\": \""+ param.getPassword()+"\", "+
                      "  \"licenses\": ["+
                                          " { " +
                                              " \"license_package\": { "+
                                              "  \"id\": "+ param.getLicenses().get(0).getLicensePackage().getId().toString()+", " +
                                              "  \"license_service_id\": "+ param.getLicenses().get(0).getLicensePackage().getLicenseServiceId().toString()+", " +
                                              "  \"visible\": "+ param.getLicenses().get(0).getLicensePackage().isVisible()+" " +
                                               " } "+
                                           " } "+
                                        " ]"+

                                        "    }";*/

        HttpPost request = new HttpPost(url);
//        request.addHeader("Accept", "application/json");
        request.addHeader("Content-Type", "application/json");
        request.addHeader("Authorization", "Basic " + authStringEnc);
        request.setEntity(new StringEntity(new GsonBuilder().setPrettyPrinting().create().toJson(param)));
        System.out.println(request.getEntity().toString());

        //String teste2 =new GsonBuilder().setPrettyPrinting().create().toJson(test);
        String teste = new GsonBuilder().setPrettyPrinting().create().toJson(param);
        System.out.println(teste);
        //System.out.println(teste2);

        HttpResponse response = sendRequest(request);


        return readResponse(response, Account.class);
    }

}

