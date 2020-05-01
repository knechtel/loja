/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.dao;

import br.com.acertsis.loja.entity.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioDAO extends CrudRepository<Usuario, Long> {

    public Usuario findByUsername(String string);

    public Usuario findByUsernameOrIdUsuario(String string, Long idUsuario);

    @Query("select distinct(a.usuario) from Authorities a where a.authority = ?1")
    public Optional<List<Usuario>> findByAuthorityRoleName(String roleName);

}
