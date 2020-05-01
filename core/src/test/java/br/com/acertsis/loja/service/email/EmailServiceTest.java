/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.service.email;

import freemarker.template.TemplateException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private FreemarkerService freemarkerService;

    @Test
    public void assertRemetenteIsNotNull() {
        Assert.assertNotNull(emailService.getRemetenteEmail());
    }

    @Test
    public void assertJavaMailSenderIsNotNull() {
        Assert.assertNotNull(javaMailSender);
    }

    @Test
    public void assertFreemarkerServiceIsNotNull() {
        Assert.assertNotNull(freemarkerService);
    }

    @Test
    public void assertTemplateEmailIsLoading() {
        try {
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("teste", "OK");
            String conteudoHtml = this.freemarkerService.parse("/templateEmailTest.html", dataModel);
            Assert.assertNotNull(conteudoHtml);
            Assert.assertTrue(conteudoHtml.contains("<div>OK</div>"));

        } catch (IOException ex) {
            Logger.getLogger(EmailServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TemplateException ex) {
            Logger.getLogger(EmailServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void sendEmail() {
        try {
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("teste", "OK");
            String conteudoHtml = this.freemarkerService.parse("/templateEmailTest.html", dataModel);
            this.emailService.enviar("Assunto Teste", "moacir.flores@acertsis.com.br", conteudoHtml, true);
        } catch (IOException ex) {
            Logger.getLogger(EmailServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TemplateException ex) {
            Logger.getLogger(EmailServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MailException ex) {
            Logger.getLogger(EmailServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(EmailServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
