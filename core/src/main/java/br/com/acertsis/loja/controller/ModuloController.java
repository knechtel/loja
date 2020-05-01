package br.com.acertsis.loja.controller;


import br.com.acertsis.loja.dao.ModuloDAO;
import br.com.acertsis.loja.dto.ModuloDTO;
import br.com.acertsis.loja.entity.Modulo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/modulo")
@CrossOrigin
public class ModuloController {

    @Autowired
    private ModuloDAO moduloDAO;

    @RequestMapping(consumes= MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public HttpEntity<?> save(@Valid @RequestBody ModuloDTO form) {
        moduloDAO.save(form.doBuild());
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Modulo> finadAll(){
        return moduloDAO.findAll();

    }

}