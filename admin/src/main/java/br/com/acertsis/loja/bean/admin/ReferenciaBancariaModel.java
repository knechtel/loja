/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.bean.admin;

import br.com.acertsis.loja.entity.ReferenciaBancaria;
import br.com.acertsis.loja.entity.TipoContaBancariaEnum;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

public class ReferenciaBancariaModel implements Serializable {

    @Getter
    @Setter
    private ReferenciaBancaria referenciaBancaria;

    public void init() {
        this.clear();
    }

    public void clear() {
        this.referenciaBancaria = new ReferenciaBancaria();
        this.referenciaBancaria.setTipoContaBancariaEnum(TipoContaBancariaEnum.FISICA);
    }
}
