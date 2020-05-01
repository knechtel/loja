/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.dao;

import br.com.acertsis.loja.entity.Authorities;
import br.com.acertsis.loja.entity.Usuario;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthoritiesDAO extends CrudRepository<Authorities, Long> {
    public List<Authorities> findByUsernameAndUsuario(String username, Usuario usuario);
}
