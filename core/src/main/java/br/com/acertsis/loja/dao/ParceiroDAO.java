/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.dao;

import br.com.acertsis.loja.entity.Parceiro;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ParceiroDAO extends CrudRepository<Parceiro, Long> {

    @Query("select e from Parceiro e")
    public Optional<List<Parceiro>> listAll();

    @Query("select par from PessoaFisica p, Parceiro par where par.pessoa = p and lower(p.nome) like :nome")
    public Optional<List<Parceiro>> findByNomeIlike(@Param("nome") String nome);

    @Query("select par from PessoaJuridica p, Parceiro par where par.pessoa = p and lower(p.nomeFantasia) like :nome")
    public Optional<List<Parceiro>> findByNomeFantasiaIlike(@Param("nome") String nome);

    public Parceiro findByRaiz(boolean raiz);

    public Parceiro findAllByIdParceiro(long id);

    @Query("select p from Parceiro p left join fetch p.pessoa where p.idParceiro = :id")
    public Optional<Parceiro> findByIdFetchPessoa(@Param("id") Long id);
}
