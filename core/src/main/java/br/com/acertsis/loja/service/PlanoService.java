package br.com.acertsis.loja.service;

import br.com.acertsis.loja.dao.ModuloDAO;
import br.com.acertsis.loja.dao.PlanoDAO;
import br.com.acertsis.loja.dao.ProdutoDAO;
import br.com.acertsis.loja.entity.Modulo;
import br.com.acertsis.loja.entity.Plano;
import br.com.acertsis.loja.entity.Produto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanoService {

    @Autowired
    private PlanoDAO planoDAO;
    @Autowired
    private ModuloDAO moduloDAO;
    @Autowired
    private ProdutoDAO produtoDAO;

    public List<Produto> listProdutos() {
        return produtoDAO.listAll().orElse(new ArrayList<>());
    }

    public List<Modulo> listModulos() {
        return moduloDAO.listAll().orElse(new ArrayList<>());
    }

    public Plano manterPlano(Plano plano){
        return planoDAO.save(plano);
    }

    public Optional<Plano> bucarPlanoId(Long id){
        return planoDAO.findById(id);
    }

}
