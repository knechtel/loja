package br.com.acertsis.loja.dto;

import br.com.acertsis.loja.entity.Modulo;

public class ModuloDTO {
    private Long id;
    private String descricao;

    public Modulo doBuild(){
        Modulo a = new Modulo();
        a.setDescricao(descricao);
        return  a;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
