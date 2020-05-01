package br.com.acertsis.loja.service;

import br.com.acertsis.loja.dao.PessoaJuridicaDAO;
import br.com.acertsis.loja.entity.PessoaJuridica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PessoaJuridicaService {

    @Autowired
    PessoaJuridicaDAO pessoaJuridicaDAO;

    public PessoaJuridica incluirPessoa(PessoaJuridica pessoa){
        return pessoaJuridicaDAO.save(pessoa);
    }

    public Optional<PessoaJuridica> buscaPessoaCnpj (String cnpj){
        return pessoaJuridicaDAO.findByCnpj(cnpj);
    }
}
