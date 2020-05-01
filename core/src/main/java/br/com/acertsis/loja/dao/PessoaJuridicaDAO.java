package br.com.acertsis.loja.dao;

import br.com.acertsis.loja.entity.PessoaFisica;
import br.com.acertsis.loja.entity.PessoaJuridica;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaJuridicaDAO extends CrudRepository<PessoaJuridica,Long> {

    @Query("select p from PessoaJuridica p where p.cnpj = :cnpj")
    public Optional<PessoaJuridica> findByCnpj(String cnpj);
}
