package br.com.acertsis.loja.acesso.nubo.request;

import br.com.acertsis.loja.acesso.nubo.Credentials;
import br.com.acertsis.loja.acesso.nubo.Login;
import com.google.gson.Gson;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;



public abstract class AbstractRequest<Request, Response>{
    final String enviroment;
    private final Credentials credentials;
    private HttpClient httpClient;

    AbstractRequest(String enviroment, Credentials credentials) {
        this.enviroment = enviroment;
        this.credentials = credentials;
    }

    public abstract Response execute(Request param) throws IOException, RequestException;

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    HttpResponse sendRequest(HttpUriRequest request) throws IOException {
        if (httpClient == null) {
            httpClient = HttpClientBuilder.create().build();
        }

        return httpClient.execute(request);
    }

    Response readResponse(HttpResponse response, Class<Response> responseClassOf)
            throws IOException, RequestException {
        HttpEntity responseEntity = response.getEntity();
        InputStream responseEntityContent = responseEntity.getContent();

        Header contentEncoding = response.getFirstHeader("Content-Encoding");

        if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
            responseEntityContent = new GZIPInputStream(responseEntityContent);
        }

        BufferedReader responseReader = new BufferedReader(new InputStreamReader(responseEntityContent));
        StringBuilder responseBuilder = new StringBuilder();
        String line;

        while ((line = responseReader.readLine()) != null) {
            responseBuilder.append(line);
        }

        return parseResponse(response.getStatusLine().getStatusCode(), responseBuilder.toString(), responseClassOf);
    }

    private Response parseResponse(int statusCode, String responseBody, Class<Response> responseClassOf)
            throws RequestException {
        Response response = null;
        Gson gson = new Gson();

        System.out.println(responseBody);

        switch (statusCode) {
            case 200:
            case 201:
                response = gson.fromJson(responseBody, responseClassOf);
                break;
            case 400:
                RequestException exception = null;
                ApiNuboError error = gson.fromJson(responseBody, ApiNuboError.class);
                System.out.printf("%s: %s", "Api Nubo Error [" + error.getCode() + "]", error.getMessage());
                exception = new RequestException(error.getMessage(), error, exception);

                throw exception;
            case 403:
                throw new RequestException("Not found", new ApiNuboError(403, "unconfirmed account"), null);
            case 404:
                throw new RequestException("Not found", new ApiNuboError(404, "Not found"), null);
            default:
                System.out.printf("%s: %s", "Api Nubo Error", "Unknown status: " + statusCode);
        }

        return response;
    }
}