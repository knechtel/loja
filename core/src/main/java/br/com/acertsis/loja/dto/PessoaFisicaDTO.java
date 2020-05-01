package br.com.acertsis.loja.dto;

import br.com.acertsis.loja.entity.PessoaFisica;
import lombok.Data;

import java.io.Serializable;
@Data
public class PessoaFisicaDTO extends PessoaDTO implements Serializable {

    private String cpf;

    public PessoaFisica doBuild(){
        PessoaFisica pessoaFisica = new PessoaFisica();
        pessoaFisica.setNome(nome);
        pessoaFisica.setCpf(cpf);
        pessoaFisica.setEmail(email);
        return pessoaFisica;
    }
}
