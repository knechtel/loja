/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.controller;

import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@RestController
public class PerfilController {

    @RequestMapping(method = RequestMethod.POST, path = "/setdominio")
    public void setDominio(String dominio, HttpSession httpSession) {
        httpSession.setAttribute("dominio", dominio);
    }

}
