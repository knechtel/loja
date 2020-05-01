/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.service;

import br.com.acertsis.loja.entity.Contrato;
import br.com.acertsis.loja.entity.PagamentoBoleto;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    //@Value("${boleto.diretorio}")
    private String uploadsDir;

    public void salvarBoletoEmDisco(Contrato contrato, PagamentoBoleto pagamento, byte[] bytes) throws IOException {
        File boletosDir = new File(uploadsDir, "boletos");
        if (boletosDir.exists()) {
            boletosDir.mkdir();
        }
        String nomeCliente = contrato.getCliente().getPessoa().getNome();
        Long idCliente = contrato.getCliente().getIdCliente();
        String nomeBoleto = idCliente.toString().concat("_").concat(nomeCliente).concat(".pdf");
        File boleto = new File(boletosDir, nomeBoleto);
        Files.write(bytes, boleto);
    }

}
