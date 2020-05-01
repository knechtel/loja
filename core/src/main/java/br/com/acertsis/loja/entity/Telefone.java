package br.com.acertsis.loja.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

@Entity
public class Telefone implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_telefone")
    private Long idTelefone;
    @ManyToOne
    @JoinColumn(name = "id_pessoa", foreignKey = @ForeignKey(name = "fk_pessoatelefone"))
    private Pessoa pessoa;
    @Column(name = "tipo_telefone", length = 20)
    @Enumerated(EnumType.STRING)
    private TelefoneEnum tipoTelefone;
    private String numero;

    public Telefone(TelefoneEnum tipoTelefone, String numero) {
        this.tipoTelefone = tipoTelefone;
        this.numero = numero;
    }

    public Telefone() {
    }

    public Long getIdTelefone() {
        return idTelefone;
    }

    public void setIdTelefone(Long idTelefone) {
        this.idTelefone = idTelefone;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public TelefoneEnum getTipoTelefone() {
        return tipoTelefone;
    }

    public void setTipoTelefone(TelefoneEnum tipoTelefone) {
        this.tipoTelefone = tipoTelefone;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Telefone telefone = (Telefone) o;
        return Objects.equals(idTelefone, telefone.idTelefone)
                &&                Objects.equals(pessoa, telefone.pessoa) &&
                tipoTelefone == telefone.tipoTelefone &&
                Objects.equals(numero, telefone.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTelefone, pessoa, tipoTelefone, numero);
    }

    @Override
    public String toString() {
        return "Telefone{" +
                "idTelefone=" + idTelefone +
                ", pessoa=" + pessoa +
                ", tipoTelefone=" + tipoTelefone +
                ", numero='" + numero + '\'' +
                '}';
    }
}
