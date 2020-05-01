package br.com.acertsis.loja;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement()
public class AcnfLojaApplication {

    public static void main(String[] args) {
        //System.setProperty("server.servlet.context-path", "/loja");
        SpringApplication.run(AcnfCoreApplication.class, args);
    }
}
