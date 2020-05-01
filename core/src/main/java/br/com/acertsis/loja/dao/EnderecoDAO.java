package br.com.acertsis.loja.dao;

import br.com.acertsis.loja.entity.Endereco;
import br.com.acertsis.loja.entity.Pessoa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoDAO extends CrudRepository<Endereco, Long> {

    @Query("select e from Endereco e join e.pessoa p where p = :p")
    public List<Endereco> findEnderecoByPessoa(Pessoa p);

}
