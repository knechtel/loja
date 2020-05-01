package br.com.acertsis.loja.bean;


import br.com.acertsis.loja.entity.*;
import br.com.acertsis.loja.exception.BusinessException;
import br.com.acertsis.loja.service.*;

import java.io.*;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.MessagingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.TemplateException;
import lombok.Getter;
import lombok.Setter;
import okhttp3.ResponseBody;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.nio.file.Files;

@Component
@Named
@ConversationScoped
public class ContratacaoMb implements Serializable {
    @Getter
    @Setter
    private String cpf;
    @Getter
    @Setter
    private String contrato;
    @Getter
    @Setter
    private List<Mensalidade> listMensalidades;
    @Inject
    private MensalidadeService mensalidadeService;
    @Inject
    private ClienteService clienteService;
    @Inject
    private ContratoService contratoService;
    @Getter
    @Setter
    private Contrato contratoView;
    @Getter
    @Setter
    private Integer parcela = 0;
    @Inject
    private VendaService vendaService;
    @Getter
    @Setter
    private StreamedContent downloadBoleto;


    @PostConstruct
    public void init() {

    }


    public String doSearch() {
        if (contrato.equals("")) {
            contrato = "0";
        }
        contrato = contrato.replaceAll("[A-Za-z .]+", "");
        Long id = new Long(contrato);
        listMensalidades = new ArrayList<Mensalidade>();
        contratoView = contratoService.findContrato(id);
        listMensalidades = mensalidadeService.findByContrato(id);
        parcela = listMensalidades.size();
        return "mensalidades.xhtml";
    }


    public void geraSegundaVia(Mensalidade mensalidade) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();

        File file1 = new File(System.getProperty("user.dir") + "/core/src/main/resources/br/com/acertsis/loja/service" + "/boletoFalso.pdf");

        response.setHeader("Content-Disposition", "attachment;filename=\"" + "boletoFalso.pdf" + "\""); //aki eu seto o header e o nome q vai aparecer na hr do donwload
        response.setContentLength((int) file1.length());
        response.setContentType("application/pdf");
        
        try {

            byte[] fileContent = Files.readAllBytes(file1.toPath());
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.flush();
            outputStream.write(fileContent);
            outputStream.flush();
            outputStream.close();

            context.responseComplete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
