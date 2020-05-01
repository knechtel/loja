/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.dto;

import br.com.acertsis.loja.entity.Parceiro;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResumoAcertsisDTO implements Serializable {

    private Parceiro parceiro;

    private long vendaPrevista;

    private long vendaRealizada;

    private long renovacoesPrevistas;

    private long renovacoesRelizadas;

    private long inadimplentes;

}
