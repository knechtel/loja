package br.com.acertsis.loja.dto;

import br.com.acertsis.loja.entity.Categoria;
import br.com.acertsis.loja.entity.Produto;

public class ProdutoDTO {
    private Long id;
    private String nome;
    private String descricao;
    private String descricaoCurta;
    private Categoria categoria;
    private Boolean habilitado;
    private Long sku;
    private PlanoDTO planoDTO;

    public Produto doBuild(){
        Produto p = new Produto();
        p.setNome(nome);
        p.setDescricao(descricao);
        p.setDescricaoCurta(descricaoCurta);

        return p;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricaoCurta() {
        return descricaoCurta;
    }

    public void setDescricaoCurta(String descricaoCurta) {
        this.descricaoCurta = descricaoCurta;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

    public Long getSku() {
        return sku;
    }

    public void setSku(Long sku) {
        this.sku = sku;
    }

    public PlanoDTO getPlanoDTO() {
        return planoDTO;
    }

    public void setPlanoDTO(PlanoDTO planoDTO) {
        this.planoDTO = planoDTO;
    }
}
