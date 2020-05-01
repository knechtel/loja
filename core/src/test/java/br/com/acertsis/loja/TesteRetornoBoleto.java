package br.com.acertsis.loja;

import br.com.acertsis.loja.entity.*;
import br.com.acertsis.loja.service.*;
import br.com.acertsis.loja.util.ArquivoRetornoCNAB240;
import br.com.acertsis.loja.util.Util;
import cieloecommerce.sdk.ecommerce.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TesteRetornoBoleto {


    @Test
    public void testRecorrencia() {


        FileReader file = null;
        try {
            file = new FileReader("/home/fernando/Downloads/IEDCBR8273008201912621.ret");
            ArquivoRetornoCNAB240 arquivo = new ArquivoRetornoCNAB240();
            arquivo.LerArquivoRetorno(file);
            System.out.println("teste");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



    }

}
