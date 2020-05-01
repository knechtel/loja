/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.dao;

import br.com.acertsis.loja.entity.Parceiro;
import br.com.acertsis.loja.entity.RegraComissao;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RegraComissaoDAO extends CrudRepository<RegraComissao, Long> {

    @Query("select e from RegraComissao e")
    public Optional<List<RegraComissao>> listAll();

    @Query("select rc from RegraComissao rc inner join fetch rc.parceiro p "+
                                           " where p = :p AND "+
                                         " rc.habilitado = true AND :data between rc.dtInicial and rc.dtFinal")
    public Optional<RegraComissao> findByParceiro(@Param("p") Parceiro p, @Param("data") LocalDate data);
}
