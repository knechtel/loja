package br.com.acertsis.loja.dto;

import br.com.acertsis.loja.entity.PessoaJuridica;
import lombok.Data;

import java.io.Serializable;

public class PessoaJuridicaDTO extends PessoaDTO implements Serializable {
    private String cnpj;

    public PessoaJuridica build(){
        PessoaJuridica p = new PessoaJuridica();
        p.setCnpj(cnpj);
        p.setIdPessoa(id);
        p.setEmail(email);
        return p;
    }
}
