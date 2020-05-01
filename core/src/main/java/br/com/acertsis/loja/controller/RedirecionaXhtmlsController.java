/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.controller;

import org.springframework.web.servlet.ModelAndView;

/**
 *
 *
 */
//@Controller
public class RedirecionaXhtmlsController {

    //  @GetMapping(value = "/")
    public void index(ModelAndView modelAndView) {
        modelAndView.setViewName("index.xhtml");
    }

    //@GetMapping(value = "admin/")
    public void admin(ModelAndView modelAndView) {
        System.out.println("sdsadsadas32432432");
        modelAndView.setViewName("/admin/index.xhtml");
    }
}
