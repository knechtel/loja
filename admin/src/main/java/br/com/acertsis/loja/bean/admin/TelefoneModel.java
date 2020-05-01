/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.bean.admin;

import br.com.acertsis.loja.entity.Telefone;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class TelefoneModel implements Serializable {

    @Getter
    @Setter
    private Telefone contato;

    @Getter
    @Setter
    private List<Telefone> contatos;

    @Getter
    @Setter
    private List<Telefone> contatosParaRemover;


    public void init() {
        this.contato = new Telefone();
        this.contatos = new ArrayList<>();
        this.contatosParaRemover = new ArrayList<>();
    }

    public void clear() {
        this.contato = new Telefone();
        this.contatos = new ArrayList<>();
        this.contatosParaRemover = new ArrayList<>();
    }

    public void addContato() {
        if (!containsTelefone(contato.getNumero())) {
            this.contatos.add(contato);
        }

        contato = new Telefone();
    }

    private boolean containsTelefone(final String telefone) {
        return this.contatos.stream().anyMatch((it) -> telefone.equals(it.getNumero()));
    }

    public void addRemover(Telefone contato, int indexLinha) {
        if (contato.getIdTelefone() == null) {
            this.contatos.remove(indexLinha);
        } else {
            this.contatos.remove(contato);
            this.contatosParaRemover.add(contato);
        }
    }

    public void addEditar(Telefone cont, int indexLinha) {
        if (cont != null) {
            this.contato = contatos.get(indexLinha);
            this.contatos.remove(indexLinha);
        }
    }

}
