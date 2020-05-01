package br.com.acertsis.loja.dto;

import br.com.acertsis.loja.entity.Plano;

public class PlanoDTO {

    private Long idPlano;
    private Integer qtdNotas;
    private Double valorPlano;
    private String descricao;
    private String descricaoCurta;
    private String obs;

    public Plano doBuild() {
        Plano p = new Plano();
        if (valorPlano != null)
            p.setValorPlano(valorPlano);
        p.setDescricao(descricao);
        p.setObs(obs);

        return p;
    }

    public Long getIdPlano() {
        return idPlano;
    }

    public void setIdPlano(Long idPlano) {
        this.idPlano = idPlano;
    }

    public Integer getQtdNotas() {
        return qtdNotas;
    }

    public void setQtdNotas(Integer qtdNotas) {
        this.qtdNotas = qtdNotas;
    }


    public Double getValorPlano() {
        return valorPlano;
    }

    public void setValorPlano(Double valorPlano) {
        this.valorPlano = valorPlano;
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

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }
}
