package br.com.acertsis.loja.dao;

import br.com.acertsis.loja.entity.Pessoa;
import br.com.acertsis.loja.entity.Telefone;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TelefoneDAO extends CrudRepository<Telefone, Long>{

    @Query("select t from Telefone t join t.pessoa p where p = :p")
    public List<Telefone> findTelefoneByPessoa(Pessoa p);

}
