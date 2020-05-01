/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.service.email;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

/**
 * Serviço para utilizar o Freemarker como gerador de conteudo HTML.
 *
 *
 * @author moacir.flores@acertsis.com.br
 *
 *
 */
@Service
@SessionScope
public class FreemarkerService {

    @Inject
    private Configuration freeConfiguration;

//    @PostConstruct
//    private void init() {
//        this.freeConfiguration.setClassForTemplateLoading(getClass(), "/templates");
//    }

    public String parse(String templatePath, Map<String, Object> dataModel) throws IOException, TemplateException {
        Template temp = freeConfiguration.getTemplate(templatePath);
        StringWriter writer = new StringWriter();
        temp.process(dataModel, writer);
        return writer.toString();
    }
}
