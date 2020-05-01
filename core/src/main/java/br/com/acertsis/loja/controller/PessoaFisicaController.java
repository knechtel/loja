package br.com.acertsis.loja.controller;

import br.com.acertsis.loja.dao.PessoaFisicaDAO;
import br.com.acertsis.loja.dto.PessoaDTO;
import br.com.acertsis.loja.dto.PessoaFisicaDTO;
import br.com.acertsis.loja.entity.PessoaFisica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PessoaFisicaController {
    @Autowired
    private PessoaFisicaDAO pessoaFisicaDAO;

    @PostMapping("/doPessoaFisica")
    public HttpEntity<?> save(@Valid @RequestBody PessoaFisicaDTO form) {
        pessoaFisicaDAO.save(form.doBuild());
        return ResponseEntity.ok().build();
    }
    @RequestMapping(value="/pessoaFisica",consumes= MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    List<PessoaFisica> finadAll(){
        pessoaFisicaDAO.findAll();
        return null;
    }

}
