package br.com.acertsis.loja.service;

import br.com.acertsis.loja.dao.PessoaFisicaDAO;
import br.com.acertsis.loja.dao.PessoaJuridicaDAO;
import br.com.acertsis.loja.entity.PessoaEnum;
import br.com.acertsis.loja.entity.PessoaFisica;
import br.com.acertsis.loja.entity.PessoaJuridica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PessoaService {
    @Autowired
    PessoaFisicaDAO pessoaFisicaDAO;
    @Autowired
    PessoaJuridicaDAO pessoaJuridicaDAO;

    public Object buscarPessoa(String cpfCnpj, PessoaEnum tipo){
        Object obj;
        if (tipo.equals(PessoaEnum.FISICA)){
            obj =  (PessoaFisica) pessoaFisicaDAO.findByCpf(cpfCnpj).orElse(new PessoaFisica());
        } else {
            obj =  (PessoaJuridica) pessoaJuridicaDAO.findByCnpj(cpfCnpj).orElse(new PessoaJuridica());
        }
        return obj;
    }

}
