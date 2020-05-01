package br.com.acertsis.loja.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;

@Entity
public class Plano implements Serializable {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plano")
    private Long idPlano;
    private String nome;
    @Column(name = "valor_plano")
    private double valorPlano;

    @Column(name = "max_parcela")
    private Integer maxParcela;

    @Lob
    private String descricao;
    private String obs;
    /**
     * @TODO - Nubo
     */
    @Column(name = "sku")
    private Long sku;
    @ManyToMany
    @JoinTable(name = "produto_plano",
            joinColumns = @JoinColumn(name = "id_plano", foreignKey = @ForeignKey(name = "fk_produtoplano")),
            inverseJoinColumns = @JoinColumn(name = "id_produto"))
    private Set<Produto> listProduto;

    @ManyToMany
    @JoinTable(name = "modulo_plano",
            joinColumns = @JoinColumn(name = "id_plano", foreignKey = @ForeignKey(name = "fk_moduloplano")),
            inverseJoinColumns = @JoinColumn(name = "id_modulo"))
    private Set<Modulo> modulos;

    @Column(name = "dt_criacao")
    private LocalDate dtCriacao;
    @Column(name = "id_atualizacao")
    private LocalDate dtAtualizacao;
    @Column(length = 30)
    @Enumerated(EnumType.STRING)
    private PeriodoEnum validade;
    @Column(name = "dt_inicio")
    private LocalDate dtInicio;
    @Column(name = "dt_fim")
    private LocalDate dtFim;
    private boolean habilitado;

    public Long getIdPlano() {
        return idPlano;
    }

    public void setIdPlano(Long idPlano) {
        this.idPlano = idPlano;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValorPlano() {
        return valorPlano;
    }

    public void setValorPlano(double valorPlano) {
        this.valorPlano = valorPlano;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Long getSku() {
        return sku;
    }

    public void setSku(Long sku) {
        this.sku = sku;
    }

    public LocalDate getDtCriacao() {
        return dtCriacao;
    }

    public void setDtCriacao(LocalDate dtCriacao) {
        this.dtCriacao = dtCriacao;
    }

    public LocalDate getDtAtualizacao() {
        return dtAtualizacao;
    }

    public void setDtAtualizacao(LocalDate dtAtualizacao) {
        this.dtAtualizacao = dtAtualizacao;
    }

    public Set<Produto> getListProduto() {
        return listProduto;
    }

    public void setListProduto(Set<Produto> listProduto) {
        this.listProduto = listProduto;
    }

    public Set<Modulo> getModulos() {
        return modulos;
    }

    public void setModulos(Set<Modulo> modulos) {
        this.modulos = modulos;
    }

    public PeriodoEnum getValidade() {
        return validade;
    }

    public void setValidade(PeriodoEnum validade) {
        this.validade = validade;
    }

    public LocalDate getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(LocalDate dtInicio) {
        this.dtInicio = dtInicio;
    }

    public LocalDate getDtFim() {
        return dtFim;
    }

    public void setDtFim(LocalDate dtFim) {
        this.dtFim = dtFim;
    }

    @PrePersist
    private void salvarSetDtCriacao() {
        this.setDtCriacao(LocalDate.now());
    }

    @PreUpdate
    private void salvarDtAtualizacao() {
        this.setDtAtualizacao(LocalDate.now());
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public int getMaxParcela() {
        return maxParcela;
    }

    public void setMaxParcela(int maxParcela) {
        this.maxParcela = maxParcela;
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.idPlano);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Plano other = (Plano) obj;
        if (!Objects.equals(this.idPlano, other.idPlano)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Plano{"
                + "idPlano=" + idPlano
                + ", nome='" + nome + '\''
                + ", valorPlano=" + valorPlano
                + ", descricao='" + descricao + '\''
                + ", obs='" + obs + '\''
                + ", sku=" + sku
                + ", dtCriacao=" + dtCriacao
                + ", dtAtualizacao=" + dtAtualizacao
                + ", validade=" + validade
                + ", dtInicio=" + dtInicio
                + ", dtFim=" + dtFim
                + ", habilitado=" + habilitado
                + '}';
    }
}
