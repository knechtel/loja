/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.bean.admin;

import br.com.acertsis.loja.dto.ResumoAcertsisDTO;
import br.com.acertsis.loja.service.ResumoAcertsisService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Named
@ViewScoped
public class ResumoAcertsisMb implements Serializable {

    @Getter
    @Setter
    private List<ResumoAcertsisDTO> resumos;

    @Inject
    private ResumoAcertsisService resumoAcertsisService;

    @PostConstruct
    private void init() {
        this.resumos = resumoAcertsisService.listar();
    }
}
