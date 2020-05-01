/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.dao;

import br.com.acertsis.loja.entity.Mensalidade;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional()
public interface MensalidadeDAO extends CrudRepository<Mensalidade, Long> {

    @Query("Select m from  Mensalidade m where m.dtVencimento < :date")
    public List<Mensalidade> findMensalideVencida(@Param("date")  LocalDate date);

    @Query("select  m from Mensalidade m where m.dtVencimento < current_date()")
    public Optional<List<Mensalidade>> findMensalidadesVencidas();


    @Query("Select m from  Mensalidade m where m.dtVencimento = :date")
    public List<Mensalidade> findMensalideDiaDeHoje(@Param("date")  LocalDate date);

    @Query("select m from Mensalidade m join fetch m.contrato c where m.contrato.idContrato= :id")
    public List<Mensalidade> findByContrato(@Param("id")Long id);
}
