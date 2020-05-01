package br.com.acertsis.loja.controller;

import br.com.acertsis.loja.dao.ProdutoDAO;
import br.com.acertsis.loja.dto.ProdutoDTO;
import br.com.acertsis.loja.entity.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/produto")
@CrossOrigin
public class ProdutoController {
    @Autowired
    private ProdutoDAO produtoDAO;


    @RequestMapping(consumes=MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public HttpEntity<?> save(@Valid @RequestBody ProdutoDTO form) {
        produtoDAO.save(form.doBuild());
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Produto> finadAll(){
        return produtoDAO.findAll();

    }
}
