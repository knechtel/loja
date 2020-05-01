/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.service.email;

import br.com.acertsis.loja.dao.ContratoDAO;
import br.com.acertsis.loja.dto.FormContatoLojaDto;
import br.com.acertsis.loja.entity.Cliente;
import br.com.acertsis.loja.entity.Contrato;
import br.com.acertsis.loja.entity.Mensalidade;
import br.com.acertsis.loja.entity.Parceiro;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

@Service
public class EmailsPadraoService {

    @Value("${email.contato}")
    private String emailContato;
    @Inject
    private FreemarkerService freemarkerService;
    @Inject
    private EmailService emailService;
    @Inject
    private ContratoDAO contratoDAO;

    public void solicitacaoParceria(Parceiro parceiro) throws IOException, TemplateException, MailException, MessagingException {
        Map<String, Object> data = new HashMap<>();
        data.put("parceiro", parceiro);
        String html = this.freemarkerService.parse("solicitacaoParceria.html", data);
        emailService.enviar("Solicitação de Parceria", parceiro.getPessoa().getEmail(), html, true);
    }

    public void confirmacaoParceria(Parceiro parceiro, String login, String senha) throws IOException, TemplateException, MailException, MessagingException {
        Map<String, Object> data = new HashMap<>();
        data.put("parceiro", parceiro);
        data.put("login", login);
        data.put("senha", senha);
        String html = this.freemarkerService.parse("confirmacaoParceria.html", data);
        emailService.enviar("Confirmação de Parceria", parceiro.getPessoa().getEmail(), html, true);
    }

    public void contratoAprovado(Contrato contrato) throws IOException, TemplateException, MailException, MessagingException {
        Map<String, Object> data = new HashMap<>();
        data.put("contrato", contrato);
        String html = this.freemarkerService.parse("contratoAprovado.html", data);

        emailService.enviar("Confirmação de Parceria", contrato.getCliente().getPessoa().getEmail(), html, true);
    }

    public void confirmacaoPagamentoMensalidade(Cliente cliente, Mensalidade mensalidade) throws IOException, TemplateException, MailException, MessagingException {
        Map<String, Object> data = new HashMap<>();
        data.put("cliente", cliente);
        data.put("mensalidade", mensalidade);
        String html = this.freemarkerService.parse("confirmacaoPagamentoMensalidade.html", data);
        emailService.enviar("Confirmação Pagamento Mensalidade", cliente.getPessoa().getEmail(), html, true);
    }

    public void mensalidadeNaoPaga(Cliente cliente, Mensalidade mensalidade) throws IOException, TemplateException, MailException, MessagingException {
        Map<String, Object> data = new HashMap<>();
        data.put("cliente", cliente);
        data.put("mensalidade", mensalidade);
        String html = this.freemarkerService.parse("mensalidadeNaoPaga.html", data);
        emailService.enviar("Mensalidade em aberto", cliente.getPessoa().getEmail(), html, true);
    }

    public void novaCompraCliente(Cliente cliente, Contrato contrato) throws MailException, MessagingException, IOException, TemplateException {

        Map<String, Object> model = new HashMap();
        model.put("cliente", cliente);
        model.put("plano", contrato.getPlano());
        model.put("contrato", contrato);
        model.put("dataInicio", contrato.getDtInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        model.put("dataFinal", contrato.getDtFim().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        String conteudo = freemarkerService.parse("notificarCompra.html", model);
        emailService.enviar("Você adquiriu o ACNF", cliente.getPessoa().getEmail(), conteudo, true);
    }

    public void novaCompraClienteBoleto(Cliente cliente, Contrato contrato, byte[] boleto) throws MailException, MessagingException, IOException, TemplateException {

        Map<String, Object> model = new HashMap();
        model.put("cliente", cliente);
        model.put("plano", contrato.getPlano());
        model.put("contrato", contrato);
        model.put("dataInicio", contrato.getDtInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        model.put("dataFinal", contrato.getDtFim().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        String nomeAnexo = "boleto".concat(cliente.getPessoa().getNome()).concat(".pdf");
        EmailService.AnexoModel anexo = EmailService.AnexoModel.build(nomeAnexo, boleto);
        String conteudo = freemarkerService.parse("notificarCompra.html", model);
        emailService.enviar("Você adquiriu o ACNF", cliente.getPessoa().getEmail(), conteudo, true, Arrays.asList(anexo));
    }

    public void enviarEmailContato(FormContatoLojaDto formContato) throws IOException, TemplateException, MailException, MessagingException {
        Map<String, Object> model = new HashMap();
        model.put("formContato", formContato);
        model.put("notificarAcertsis", false);
        String conteudo = freemarkerService.parse("notificarContato.html", model);
        emailService.enviar("Recebemos o seu contato", formContato.getEmail(), conteudo, true);
        //
        Map<String, Object> model2 = new HashMap();
        model2.put("formContato", formContato);
        model2.put("notificarAcertsis", true);
        String conteudo2 = freemarkerService.parse("notificarContato.html", model2);
        emailService.enviar("Recebemos um novo contato", this.emailContato, conteudo2, true);
    }
}
