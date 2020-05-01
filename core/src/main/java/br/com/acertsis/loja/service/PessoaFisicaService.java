package br.com.acertsis.loja.service;

import br.com.acertsis.loja.dao.PessoaFisicaDAO;
import br.com.acertsis.loja.entity.PessoaFisica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PessoaFisicaService {

    @Autowired
    private PessoaFisicaDAO pessoaFisicaDAO;

    public PessoaFisica incluirPessoa(PessoaFisica pessoa){
        return pessoaFisicaDAO.save(pessoa);
    }

    public Optional<PessoaFisica> buscaPessoaCpf(String cpf){
        return pessoaFisicaDAO.findByCpf(cpf);
    }

}
