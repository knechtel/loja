/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.service.email;

import java.io.ByteArrayInputStream;
import java.util.List;
import javax.inject.Inject;
import javax.mail.MessagingException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Inject
    private JavaMailSender javaMailSender;

    @Setter
    @Getter
    @Value("${spring.mail.username}")
    private String remetenteEmail;

    public void enviar(String assunto, String to, String conteudo, boolean conteudoHtml) throws MailException, MessagingException {
        enviar(remetenteEmail, assunto, to, conteudo, conteudoHtml);
    }

    public void enviar(String assunto, String to, String conteudo, boolean conteudoHtml, List<AnexoModel> anexos) throws MailException, MessagingException {
        enviar(this.remetenteEmail, assunto, to, conteudo, conteudoHtml, anexos);
    }

    public void enviar(String from, String assunto, String to, String conteudo, boolean conteudoHtml) throws MailException, MessagingException {
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage());
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(assunto);
        mimeMessageHelper.setText(conteudo, conteudoHtml);
        javaMailSender.send(mimeMessageHelper.getMimeMessage());
    }

    public void enviar(String from, String assunto, String to, String conteudo, boolean conteudoHtml, List<AnexoModel> anexos) throws MailException, MessagingException {
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(), true);
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(assunto);
        mimeMessageHelper.setText(conteudo, conteudoHtml);
        if (anexos != null && anexos.size() > 0) {
            for (AnexoModel it : anexos) {
                InputStreamSource inputStreamSource = () -> new ByteArrayInputStream(it.getData());
                mimeMessageHelper.addAttachment(it.getNome(), inputStreamSource);
            }
        }
        javaMailSender.send(mimeMessageHelper.getMimeMessage());
    }

    public static final class AnexoModel {

        @Getter
        private final String nome;
        @Getter
        private final byte[] data;

        private AnexoModel(String nome, byte[] data) {
            this.nome = nome;
            this.data = data;
        }

        public static AnexoModel build(String nome, byte[] data) {
            return new AnexoModel(nome, data);
        }
    }
}
