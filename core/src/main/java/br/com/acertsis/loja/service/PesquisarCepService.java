/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

@Service
public class PesquisarCepService {
    private static final String URL = "https://viacep.com.br/ws/";

    private Retrofit.Builder builder;

    public Map<String, String> pesquisar(String cep) {

        this.builder = new Retrofit.Builder();
        Retrofit retrofit = this.builder.addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).build();
        CepService cepService = retrofit.create(CepService.class);
        Map<String, String> map = null;
        try {
            Response<Map<String, String>> response = cepService.pesquisar(cep).execute();
            if (response != null) {
                if (response.isSuccessful()) {
                    map = response.body();
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(PesquisarCepService.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (map == null) {
            map = new HashMap<>();
        }
        return map;
    }

    public interface CepService {

        @GET("{cep}/json")
        public Call<Map<String, String>> pesquisar(@Path("cep") String cep);
    }
}
