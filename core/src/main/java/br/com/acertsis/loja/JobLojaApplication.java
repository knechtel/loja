package br.com.acertsis.loja;

import br.com.acertsis.loja.service.ContaPlataformaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobLojaApplication {
    @Autowired
    ContaPlataformaService plataformaService;

    private static final String TIME_ZONE = "America/Sao_Paulo";
    private final long SEGUNDO = 1000;
    private final long MINUTO = SEGUNDO * 60;
    private final long HORA = MINUTO * 60;

    @Scheduled(fixedDelay = MINUTO *10)
    public void criarContasPendentes() {
        plataformaService.generateNewAccount();
    }
}
