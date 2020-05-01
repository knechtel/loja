package br.com.acertsis.loja;

import br.com.acertsis.loja.dao.PessoaDAO;
import br.com.acertsis.loja.entity.Endereco;
import javax.inject.Inject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AcnfLojaApplicationTests {
    @Inject
    private PessoaDAO pessoaDAO;
	@Test
    public void contextLoads() {
            Iterable<Endereco> l = pessoaDAO.findEnderecosByIdPessoa(6L);
            System.out.println(l.toString());
    }

}
