package br.com.acertsis.loja.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;

@Entity
public class Produto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    private Long idProduto;
    private String nome;
    @Lob
    private String descricao;
    @Column(name = "descricao_curta")
    private String descricaoCurta;
    @ManyToOne
    @JoinColumn(name = "id_categoria", foreignKey = @ForeignKey(name = "fk_categoriaproduto"))
    private Categoria categoria;
    private boolean habilitado;
    @Column(name = "dt_criacao")
    private LocalDate dtCriacao;
    @Column(name = "dt_atualizacao")
    private LocalDate dtAtualizacao;

    @ManyToMany
    @JoinTable(name = "produto_plano",
            joinColumns = @JoinColumn(name = "id_produto", foreignKey = @ForeignKey(name = "fk_planoproduto")),
            inverseJoinColumns = @JoinColumn(name = "id_plano"))
    private Set<Plano> planos;

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
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

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
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

    public Set<Plano> getPlanos() {
        return planos;
    }

    public void setPlanos(Set<Plano> planos) {
        this.planos = planos;
    }

    @PrePersist
    private void salvarSetDtCriacao() {
        this.setDtCriacao(LocalDate.now());
    }

    @PreUpdate
    private void salvarDtAtualizacao() {
        this.setDtAtualizacao(LocalDate.now());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.idProduto);
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
        final Produto other = (Produto) obj;
        if (!Objects.equals(this.idProduto, other.idProduto)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Produto{"
                + "idProduto=" + idProduto
                + ", nome='" + nome + '\''
                + ", descricao='" + descricao + '\''
                + ", descricaoCurta='" + descricaoCurta + '\''
                + ", categoria=" + categoria
                + ", habilitado=" + habilitado
                + ", dtCriacao=" + dtCriacao
                + ", dtAtualizacao=" + dtAtualizacao
                + ", planos=" + planos
                + '}';
    }
}
