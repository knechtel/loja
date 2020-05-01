package br.com.acertsis.loja;

import br.com.acertsis.loja.service.ApiBoleto;
import java.io.*;
import java.util.HashMap;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Test {


    public static void main(String[] args)  {

        Retrofit boleto = new Retrofit.Builder().baseUrl("https://mpag.bb.com.br/site/mpag/pagamento/")
                .build();
        ApiBoleto webServiceApi = boleto.create(ApiBoleto.class);

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("idConv", "314435");
        // map.put("refTran", "29459790010010110");
        map.put("refTran", "29459790000000021");
        map.put("dtVenc", "30082019");
        map.put("valor", "11");
        map.put("tpPagamento", "1");
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
        webServiceApi.doBoleto(map).enqueue(new Callback<
                ResponseBody>() {
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
            File futureStudioIconFile = new File("/home/moacir/ultimoTest.pdf");

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
                    = new FileOutputStream(new File("/home/moacir/output13.pdf"));

            // Starts writing the bytes in it
            os.write(bytes);
            System.out.println("Successfully"
                    + " byte inserted");

            // Close the file
            os.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
//
//        /*Retrofit boleto = new Builder().baseUrl("https://mpag.bb.com.br/site/mpag/")
//                .build();
//        ApiBoleto webServiceApi = boleto.create(ApiBoleto.class);
//
//        HashMap<String ,String> map = new HashMap<String,String>();
//        map.put("idConv", "314435");
//        map.put("refTran", "12345671111111111");
//        map.put("dtVenc","30/08/2019");
//
//        map.put("tpPagamento","2");
//        map.put("cpfCnpj","01637948042");
//        map.put("indicadorPessoa", "1");
//        map.put("tpDuplicata", "DS");
//        map.put("urlRetorno", "");
//        map.put("nome", "acertsis");
//        map.put("endereco", "Alameda 24 de outubro 39");
//        map.put("cidade", "tramadai");
//        map.put("uf", "RS");
//        map.put("cep", "95590000");
//        map.put("msgLoja", "ssssss");
//        map.put("Formato","2");
//        webServiceApi.doBoleto(map).enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                System.out.println("ok");
//                try {
//                    System.out.println(response.body().string());
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });*/
//
//        // ...
//        // Configure seu merchant
//        Merchant merchant = new Merchant("0ad0f0b3-5f26-49fd-8378-614fbad14ce4", "coSbF3fUUun7RjWdlDFljb01gfHr4jCjn9V3FSYm");
//
//        // Crie uma instância de Sale informando o ID do pagamento
//        // Limite de 50 caracteres
//        Sale sale = new Sale("99999999999");
//
//        // Crie uma instância de Customer informando o nome do cliente(Pessoa nome)
//        // Limite de 255 caracteres
//        Customer customer = sale.customer("FERNANDO MACIEL LOGRADO");
//
//        // Crie uma instância de Payment informando o valor da mensalidade(Em centavos)
//        // Limite de 15 casa decimais
//        Payment payment = sale.payment(1000);
//        payment.setType(Payment.Type.CreditCard);
//        //Se for propria o valor ser 1
//        //Se for programada o valor será o número de parcerlas
//        // Limite de 2 casa decimais
//        payment.setInstallments(1);
//        //Poder ser o código do produto.
//        // Limite de 13 caracteres
//        payment.setSoftDescriptor("ACNF");
//
//        //Se for recorrência propria
//        //payment.recurrentPayment(true);
//
//        //Se for recorrência programada
//        //RecurrentPayment recurrentPayment = new RecurrentPayment(true);
//        payment.setRecurrentPayment(new RecurrentPayment(true));
//        // Limite de 10 casa caracteres
//        payment.getRecurrentPayment().setStartDate("2019-09-02");
//        // Limite de 10 casa caracteres
//        payment.getRecurrentPayment().setEndDate("2020-08-02");
//        payment.getRecurrentPayment().setInterval(RecurrentPayment.Interval.Annual);
//
//        payment.creditCard("738", BrandCardEnum.Master.toString()).setExpirationDate("02/2027")
//                .setCardNumber("5502093772136567")
//                .setHolder("FERNANDO M LOGRADO")
//                .setSaveCard(true);
//
//        // Crie o pagamento na Cielo
//       // try {
//            // Configure o SDK com seu merchant e o ambiente apropriado para criar a venda
//            //Não temos o ambiente SANDBOX somente o produção
//            //sale = new CieloEcommerce(merchant, Environment.PRODUCTION).createSale(sale);
//
//            // Com a venda criada na Cielo, já temos o ID do pagamento, TID e demais
//            // dados retornados pela Cielo
//            //String paymentId = sale.getPayment().getPaymentId();
//
//            // Com o ID do pagamento, podemos fazer sua captura, se ela não tiver sido capturada ainda
//            // SaleResponse saleResponse = new CieloEcommerce(merchant, Environment.PRODUCTION).captureSale(paymentId, 15700, 0);
//
//            // E também podemos fazer seu cancelamento, se for o caso
//            //saleResponse = new CieloEcommerce(merchant, Environment.PRODUCTION).cancelSale(paymentId, 15700);
//     //   } catch (CieloRequestException e) {
//            // Em caso de erros de integração, podemos tratar o erro aqui.
//            // os códigos de erro estão todos disponíveis no manual de integração.
//            //        CieloError error = e.getError();
//        //} catch (IOException e) {
//            //          e.printStackTrace();
//     //   }
//        // ...
//
//      //  System.out.print(sale.toString());
//
//
//        try {
//
//            ArquivoRetornoCNAB240 teste = new ArquivoRetornoCNAB240();
//            teste.LerArquivoRetorno(new FileReader("/home/fernando/Downloads/Boletos_09082019.ret"));
//            BufferedReader br = new BufferedReader(new FileReader("/home/fernando/Downloads/Boletos_09082019.ret"));
//            String linha = null;
//            /*while ((linha = br.readLine()) != null) {
//                if(!linha.isEmpty()){
//                    System.out.println(linha);
//                }
//
//            }*/
//            //HeaderDeArquivoCNAB240 header = new HeaderDeArquivoCNAB240();
//            //header.LerHeaderDeArquivoCNAB240(br.toString());
//           // header.toString();
//            br.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    //@org.junit.Test
//    void teste() {
//        // Configure seu merchant
//        Merchant merchant = new Merchant("0ad0f0b3-5f26-49fd-8378-614fbad14ce4", "coSbF3fUUun7RjWdlDFljb01gfHr4jCjn9V3FSYm");
//
//        // Crie uma instância de Sale informando o ID do pagamento
//        // Limite de 50 caracteres
//        Sale sale = new Sale("99999999999");
//
//        // Crie uma instância de Customer informando o nome do cliente(Pessoa nome)
//        // Limite de 255 caracteres
//        Customer customer = sale.customer("FERNANDO MACIEL LOGRADO");
//
//        // Crie uma instância de Payment informando o valor da mensalidade(Em centavos)
//        // Limite de 15 casa decimais
//        Payment payment = sale.payment(1000);
//        payment.setType(Payment.Type.CreditCard);
//        //Se for propria o valor ser 1
//        //Se for programada o valor será o número de parcerlas
//        // Limite de 2 casa decimais
//        payment.setInstallments(2);
//        //Poder ser o código do produto.
//        // Limite de 13 caracteres
//        payment.setSoftDescriptor("ACNF");
//
//        //Se for recorrência propria
//        //payment.recurrentPayment(true);
//
//        //Se for recorrência programada
//        //RecurrentPayment recurrentPayment = new RecurrentPayment(true);
//        payment.setRecurrentPayment(new RecurrentPayment(true));
//        // Limite de 10 casa caracteres
//        payment.getRecurrentPayment().setStartDate("2019-09-02");
//        // Limite de 10 casa caracteres
//        payment.getRecurrentPayment().setEndDate("2020-09-02");
//        payment.getRecurrentPayment().setInterval(RecurrentPayment.Interval.Annual);
//
//        // Crie  uma instância de Credit Card utilizando os dados do cartão de crédito do cliente
//        payment.creditCard("738", BrandCardEnum.Master.toString()).setExpirationDate("02/2027")
//                .setCardNumber("5502093772136567")
//                .setHolder("FERNANDO M LOGRADO")
//                .setSaveCard(true);
//
//        // Crie o pagamento na Cielo
//        try {
//            // Configure o SDK com seu merchant e o ambiente apropriado para criar a venda
//            //Não temos o ambiente SANDBOX somente o produção
//            sale = new CieloEcommerce(merchant, Environment.SANDBOX).createSale(sale);
//
//            // Com a venda criada na Cielo, já temos o ID do pagamento, TID e demais
//            // dados retornados pela Cielo
//            //String paymentId = sale.getPayment().getPaymentId();
//
//            // Com o ID do pagamento, podemos fazer sua captura, se ela não tiver sido capturada ainda
//           // SaleResponse saleResponse = new CieloEcommerce(merchant, Environment.PRODUCTION).captureSale(paymentId, 15700, 0);
//
//            // E também podemos fazer seu cancelamento, se for o caso
//            //saleResponse = new CieloEcommerce(merchant, Environment.PRODUCTION).cancelSale(paymentId, 15700);
//        } catch (CieloRequestException e) {
//            // Em caso de erros de integração, podemos tratar o erro aqui.
//            // os códigos de erro estão todos disponíveis no manual de integração.
//    //        CieloError error = e.getError();
//        } catch (IOException e) {
//  //          e.printStackTrace();
//        }
//        // ...
//
//        System.out.print(sale.toString());

    }

}
