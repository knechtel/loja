/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.bean.admin;

import br.com.acertsis.loja.entity.Endereco;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class EnderecoModel implements Serializable {

    @Getter
    @Setter
    private Endereco endereco;

    @Getter
    @Setter
    private List<Endereco> enderecos;

    @Getter
    @Setter
    private List<Endereco> enderecosParaRemover;
    @Getter
    @Setter
    private boolean multiplosEnderecos;

    public void init() {
        multiplosEnderecos = true;
        this.endereco = new Endereco();
        this.enderecos = new ArrayList<>();
        this.enderecosParaRemover = new ArrayList<>();
    }

    public void clear() {
        this.endereco = new Endereco();
        this.enderecos = new ArrayList<>();
    }

    public void addEndereco() {
        if (!containsCep(endereco.getCep())) {
            this.enderecos.add(endereco);
        }

        endereco = new Endereco();
    }

    private boolean containsCep(final String cep) {
        return this.enderecos.stream().anyMatch((it) -> cep.equals(it.getCep()));
    }

    public void addRemover(Endereco endereco, int indexLinha) {
        if (endereco.getId() == null) {
            this.enderecos.remove(indexLinha);
        } else {
            this.enderecos.remove(endereco);
            this.enderecosParaRemover.add(endereco);
        }
    }

    public void addEditar(Endereco end, int indexLinha) {
        if (endereco != null) {
            this.endereco = enderecos.get(indexLinha);
            this.enderecos.remove(indexLinha);
        }
    }

}
