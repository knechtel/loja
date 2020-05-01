package br.com.acertsis.loja;

import br.com.acertsis.loja.service.ApiBoleto;
import java.io.*;
import java.util.HashMap;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TestBoleto {

    public static void main(String[] args) {
        Retrofit boleto = new Retrofit.Builder().baseUrl("https://mpag.bb.com.br/site/mpag/pagamento/")
                .build();
        ApiBoleto webServiceApi = boleto.create(ApiBoleto.class);

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("idConv", "314435");
        map.put("refTran", "29459790010010110");
        map.put("dtVenc", "30082019");
        map.put("valor", "11");
        map.put("tpPagamento", "21");
        map.put("cpfCnpj", "01637948042");
        map.put("indicadorPessoa", "1");
        map.put("tpDuplicata", "DS");
        map.put("urlRetorno", "sucesso.jsp");
        map.put("nome", "acertsis");
        map.put("endereco", "Alameda 24 de outubro 39");
        map.put("cidade", "tramadai");
        map.put("uf", "RS");
        map.put("cep", "95590000");
        map.put("msgLoja", "ssssss");
        map.put("Formato", "2");
        webServiceApi.doBoleto(map).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.out.println("ok");

                try {

                    writeByte(response.body().bytes());
                 } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private static boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File("/home/moacir/meuArquivo.pdf");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                System.out.println("meudeusAqui = "+fileSize);
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                  }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
    static void writeByte(byte[] bytes) {
        try {


            OutputStream
                    os
                    = new FileOutputStream(new File("/home/moacir/output.pdf"));

            // Starts writing the bytes in it
            os.write(bytes);
            System.out.println("Successfully"
                    + " byte inserted");

            // Close the file
            os.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }




}
