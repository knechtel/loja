package br.com.acertsis.loja.service;


import br.com.acertsis.loja.dao.EnderecoDAO;
import br.com.acertsis.loja.entity.Endereco;
import br.com.acertsis.loja.entity.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoService {

    @Autowired
    EnderecoDAO enderecoDAO;

    public List<Endereco> buscarEnderecoPessoa(Pessoa pessoa){
        return enderecoDAO.findEnderecoByPessoa(pessoa);
    }

    public Endereco manterEndereco(Endereco endereco) {
        if(endereco.getPessoa().getIdPessoa() != null) {
            List<Endereco> enderecos = enderecoDAO.findEnderecoByPessoa(endereco.getPessoa());
            if (enderecos != null && !enderecos.isEmpty()) {
                for (Endereco key : enderecos) {
                    endereco.setId(key.getId());
                }
            }
            return enderecoDAO.save(endereco);
        }
        return endereco;
    }
}
