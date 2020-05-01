package br.com.acertsis.loja.service;

import java.util.HashMap;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiBoleto {
    @Streaming
    @POST("https://mpag.bb.com.br/site/mpag/pagamento")
    public Call<ResponseBody> doBoleto(@QueryMap HashMap<String, Object> arguments);

}
