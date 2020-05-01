package br.com.acertsis.loja.controller;


import br.com.acertsis.loja.dao.PlanoDAO;
import br.com.acertsis.loja.dto.PlanoDTO;
import br.com.acertsis.loja.dto.ProdutoDTO;
import br.com.acertsis.loja.entity.Plano;
import br.com.acertsis.loja.entity.Produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/plano")
@CrossOrigin
public class PlanoController {
    @Autowired
    private PlanoDAO planoDAO;


    @RequestMapping(consumes= MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public HttpEntity<?> save(@Valid @RequestBody PlanoDTO form) {
        planoDAO.save(form.doBuild());
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Plano> finadAll(){
        return planoDAO.findAll();

    }
}
