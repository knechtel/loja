package br.com.acertsis.loja.dto;

import lombok.Data;

@Data
public class EnderecoDTO {
    private Long id;

    private String rua;

    private String cep;

    private Integer numero;

    private String bairro;

    private String cidade;

    private String estado;
}
