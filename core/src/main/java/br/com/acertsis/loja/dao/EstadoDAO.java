/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.dao;

import br.com.acertsis.loja.entity.Estado;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EstadoDAO extends CrudRepository<Estado, Long> {

    /**
     * @return Todos os estados ordenados pela sigla utilizando o cache.
     */
    @Cacheable("estado")
    @Query("select e from Estado e order by sigla")
    public List<Estado> listAllOrderBySigla();
}
