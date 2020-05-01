/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.dto;

import br.com.acertsis.loja.entity.Contrato;
import br.com.acertsis.loja.entity.Mensalidade;
import lombok.Getter;
import lombok.Setter;

public class TableContratosDto implements Comparable<TableContratosDto> {

    public static TableContratosDto create(Contrato contrato, Mensalidade mensalidade) {
        return new TableContratosDto(contrato, mensalidade);
    }

    @Setter
    @Getter
    private Long numeroContrato;

    @Setter
    @Getter
    private Contrato contrato;

    @Setter
    @Getter
    private Mensalidade mensalidade;

    private TableContratosDto(Contrato contrato, Mensalidade mensalidade) {
        this.contrato = contrato;
        this.mensalidade = mensalidade;
        this.numeroContrato = contrato.getIdContrato();
    }

    @Override
    public int compareTo(TableContratosDto o) {
        return contrato.getIdContrato().compareTo(contrato.getIdContrato());
    }
}
