package br.com.acertsis.loja.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PessoaDTO implements Serializable {

    protected Long id;
    protected String nome;
    protected String email;

}
