/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.bean;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Named
@RequestScoped
public class PerfilMb implements Serializable {

    private static final String AGRO = "agro";
    private static final String ACNF = "acnf";

    @Value("${loja.perfil}")
    @Getter
    private String perfilAtual;

}
