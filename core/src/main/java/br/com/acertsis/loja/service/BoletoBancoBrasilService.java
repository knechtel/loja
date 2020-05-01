/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.service;

import br.com.acertsis.loja.dto.BoletoBancoDoBrasilDTO;
import br.com.acertsis.loja.service.BoletoBancoBrasilService.RetornoBoleto;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Data;
import lombok.Getter;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;

@Service
@Data
public class BoletoBancoBrasilService implements BoletoService<RetornoBoleto, BoletoBancoDoBrasilDTO> {

    private static final String ERRO_BOLETO_EMITIDO = "Titulo ja incluido anteriormente. (C008-000)";

    @Value("${boleto.bb.desabilitar}")
    private boolean desabilitar;

    @Value("${boleto.bb.numeroConvenio}")
    private String numeroConvenio;

    @Value("${boleto.bb.numeroRefTran}")
    private String numeroRefTran;

    @Value("${boleto.bb.numeroRefTranInicial}")
    private Long numeroRefTranInicial;

    @Value("${boleto.bb.beneficiario.nome}")
    private String beneficiario;

    @Value("${boleto.bb.beneficiario.endereco}")
    private String endereco;

    @Value("${boleto.bb.beneficiario.endereco.bairro}")
    private String bairro;

    @Value("${boleto.bb.beneficiario.endereco.cidade}")
    private String cidade;

    @Value("${boleto.bb.beneficiario.endereco.UF}")
    private String uf;

    @Value("${boleto.bb.beneficiario.endereco.cep}")
    private String cep;

    @Value("${boleto.bb.beneficiario.endereco.pais}")
    private String pais;

    @Value("${boleto.bb.beneficiario.cnpj}")
    private String cnpj;

    @Value("${boleto.bb.msgLoja}")
    private String msgLoja;

    public RetornoBoleto emitirBoleto(BoletoBancoDoBrasilDTO boletoBancoDoBrasilDTO) {
        byte[] bytes = null;
        Retrofit boleto = new Retrofit.Builder().baseUrl("https://mpag.bb.com.br/site/mpag/")
                .build();
        ApiBoleto webServiceApi = boleto.create(ApiBoleto.class);

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("idConv", numeroConvenio);
        map.put("refTran", boletoBancoDoBrasilDTO.getRefTran());
        map.put("dtVenc", boletoBancoDoBrasilDTO.getDtVenc().format(DateTimeFormatter.ofPattern("ddMMyyyy")));
        map.put("tpPagamento", boletoBancoDoBrasilDTO.getTpPagamento());
        map.put("cpfCnpj", boletoBancoDoBrasilDTO.getCpfCnpj());
        map.put("indicadorPessoa", boletoBancoDoBrasilDTO.getIndicadorPessoa());
        map.put("tpDuplicata", boletoBancoDoBrasilDTO.getTpDuplicata());
        map.put("urlRetorno", boletoBancoDoBrasilDTO.getUrlRetorno());
        map.put("nome", boletoBancoDoBrasilDTO.getNome());
        map.put("endereco", boletoBancoDoBrasilDTO.getEndereco());
        map.put("cidade", boletoBancoDoBrasilDTO.getCidade());
        map.put("uf", boletoBancoDoBrasilDTO.getUf());
        map.put("cep", boletoBancoDoBrasilDTO.getCep());
        map.put("msgLoja", boletoBancoDoBrasilDTO.getMsgLoja());
        map.put("Formato", boletoBancoDoBrasilDTO.getFormato());
        map.put("valor", boletoBancoDoBrasilDTO.getValor());
        map.put("telefone", boletoBancoDoBrasilDTO.getTelefone());

        try {
            if (!desabilitar) {
                Response<ResponseBody> response = webServiceApi.doBoleto(map).execute();
                ResponseBody responseBody = response.body();

                bytes = responseBody.bytes();
                if (responseBody.contentType().toString().contains("text/html")) {
                    String html = new String(bytes);
                    if (html.contains(ERRO_BOLETO_EMITIDO)) {
                        String refTRan = boletoBancoDoBrasilDTO.getRefTran();
                        long numero = BoletoBancoDoBrasilDTO.REVERTER_REF_TRAN(numeroRefTran, refTRan) + 1;
                        boletoBancoDoBrasilDTO.setRefTran(BoletoBancoDoBrasilDTO.MONTAR_REF_TRAN(numeroRefTran, numero));
                        return emitirBoleto(boletoBancoDoBrasilDTO);
                    }
                }

            } else {
                InputStream inputStream = getClass().getResourceAsStream("boletoFalso.pdf");
                bytes = new byte[inputStream.available()];
                inputStream.read(bytes);
                inputStream.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(BoletoBancoBrasilService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(BoletoBancoBrasilService.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println(map.values().toString());
        RetornoBoleto retornoBoleto = new RetornoBoleto();
        retornoBoleto.dados = bytes;
        retornoBoleto.refTran = boletoBancoDoBrasilDTO.getRefTran();
        return retornoBoleto;
    }

    public static final class RetornoBoleto {

        @Getter
        private byte[] dados;
        @Getter
        private String refTran;

    }

}
