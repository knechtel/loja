package br.com.acertsis.loja;

import br.com.acertsis.loja.entity.Endereco;
import br.com.acertsis.loja.entity.PessoaEnum;
import br.com.acertsis.loja.entity.PessoaFisica;
import br.com.acertsis.loja.entity.Telefone;
import br.com.acertsis.loja.service.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPessoa {
    
     @Autowired
    private PessoaJuridicaService pessoaJuridicaService;

    @Autowired
    private PessoaFisicaService pessoaFisicaService;
    
    @Autowired
    PessoaService pessoaService;

    @Autowired
    TelefoneService telefoneService;

    @Autowired
    EnderecoService enderecoService;
    
    @Test
    public void testConsultarPessoa() {
        
        
        PessoaFisica pessoa = (PessoaFisica) pessoaService.buscarPessoa("222.222.222-22", PessoaEnum.FISICA);

       // List<Telefone> telefone = telefoneService.buscarTelefonesPessoa(new PessoaFisica());

       // List<Endereco> endereco = enderecoService.buscarEnderecoPessoa(new PessoaFisica());

    }
    
}
