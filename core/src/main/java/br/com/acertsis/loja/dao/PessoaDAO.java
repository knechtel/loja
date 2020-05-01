package br.com.acertsis.loja.dao;

import br.com.acertsis.loja.entity.Endereco;
import br.com.acertsis.loja.entity.Pessoa;
import br.com.acertsis.loja.entity.Telefone;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaDAO extends CrudRepository<Pessoa, Long> {

    @Query("select p.endereco from Pessoa p where p.idPessoa = :idPessoa")
    public Iterable<Endereco> findEnderecosByIdPessoa(@Param("idPessoa") Long idPessoa);

    @Query("select p.telefones from Pessoa p where p.idPessoa = :idPessoa")
    public Iterable<Telefone> findTelefonesByIdPessoa(@Param("idPessoa") Long idPessoa);
}
