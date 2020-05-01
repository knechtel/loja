/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.service;

import br.com.acertsis.loja.service.email.EmailService;
import br.com.acertsis.loja.service.email.FreemarkerService;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service
public class EmailsLojaService {
    @Inject
    private EmailService emailService;
    @Inject
    private FreemarkerService freemarkerService;

}
