/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.dao;

import br.com.acertsis.loja.entity.Banco;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface BancoDAO extends CrudRepository<Banco, String> {

    public List<Banco> findFirst10ByNomeLikeIgnoreCaseOrCodigoLike(String nome, String codigo);
}
