/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.service;

import br.com.acertsis.loja.dao.AuthoritiesDAO;
import br.com.acertsis.loja.dao.UsuarioDAO;
import br.com.acertsis.loja.entity.Authorities;
import br.com.acertsis.loja.entity.Usuario;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.ServletRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 *
 * @author moacirrf
 */
@Component
public class SpringDataUserDetailsService implements UserDetailsService {

    @Inject
    private UsuarioDAO usuarioDAO;
    @Inject
    private AuthoritiesDAO authoritiesDAO;

    @Inject
    private ServletRequest servletRequest;

    @Override
    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {
        Usuario user = usuarioDAO.findByUsername(string);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário ou Senha incorretos");
        }

        List<Authorities> lista = authoritiesDAO.findByUsernameAndUsuario(user.getUsername(), user);
        List<GrantedAuthority> auths = new ArrayList<>();
        if (lista != null) {
            lista.forEach(it -> {
                GrantedAuthority g = () -> it.getAuthority();
                auths.add(g);
            });
        }

        UserDetails userDetails = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return auths;
            }

            @Override
            public String getPassword() {
                return user.getPassword();
            }

            @Override
            public String getUsername() {
                return user.getUsername();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return user.isEnabled();
            }
        };
        return userDetails;
    }

}
