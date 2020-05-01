package br.com.acertsis.loja.dao;

import br.com.acertsis.loja.entity.PessoaFisica;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaFisicaDAO extends CrudRepository<PessoaFisica,Long> {

    @Query("select p from PessoaFisica p where p.cpf = :cpf")
    public Optional<PessoaFisica> findByCpf(String cpf);
}
