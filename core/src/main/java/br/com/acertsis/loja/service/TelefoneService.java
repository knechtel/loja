package br.com.acertsis.loja.service;

import br.com.acertsis.loja.dao.RegraComissaoDAO;
import br.com.acertsis.loja.dao.TelefoneDAO;
import br.com.acertsis.loja.entity.Pessoa;
import br.com.acertsis.loja.entity.Telefone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TelefoneService {
    
    @Autowired
    TelefoneDAO telefoneDAO;

    public List<Telefone> buscarTelefonesPessoa(Pessoa pessoa){
        return telefoneDAO.findTelefoneByPessoa(pessoa);
    }

    public Telefone manterTelefone(Telefone telefone){
        if(telefone.getPessoa().getIdPessoa() != null) {
            List<Telefone> telefones = telefoneDAO.findTelefoneByPessoa(telefone.getPessoa());
            if(telefones != null && !telefones.isEmpty()) {
                for (Telefone key: telefones){
                    if(key.getTipoTelefone().equals(telefone.getTipoTelefone())){
                        telefone.setIdTelefone(key.getIdTelefone());
                    }
                }
            }
            return telefoneDAO.save(telefone);
        }
        return telefone;
    }
}
