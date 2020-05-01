/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.service;

import br.com.acertsis.loja.dao.UsuarioDAO;
import br.com.acertsis.loja.entity.Authorities;
import br.com.acertsis.loja.entity.Usuario;
import br.com.acertsis.loja.security.MyBCryptPasswordEncoder;
import br.com.acertsis.loja.security.RolesSecurityEnum;
import java.util.ArrayList;
import javax.inject.Inject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Inject
    private AdminUsuarioService usuarioService;
    @Inject
    private MyBCryptPasswordEncoder passwordEncoder;

    @Inject
    private UsuarioDAO usuarioDAO;

    @Inject
    private ClienteCrudService clien;

    @Test
    public void assertUsuarioServiceNotNull() {
        Assert.assertNotNull(usuarioService);
    }

    @Test
    public void testSaveAdmin() {
        Usuario usuario = new Usuario();
        usuario.setNome("Administrador");
        usuario.setUsername("admin");
        usuario.setEnabled(true);
        usuario.setPassword(this.passwordEncoder.encode("admin"));
        usuario.setAuthorities(new ArrayList<>());
        Authorities roleAdmin = new Authorities();
        roleAdmin.setUsuario(usuario);
        roleAdmin.setUsername(usuario.getUsername());
        roleAdmin.setAuthority(RolesSecurityEnum.ROLE_ADMIN.name());
        usuario.getAuthorities().add(roleAdmin);
        if (usuarioService.canSaveUsuario(usuario)) {
            usuarioService.salvar(usuario);
            Usuario userTest = usuarioService.loadUsuarioWithAuthoritiesByUsername(usuario.getUsername());
            Assert.assertNotNull(userTest);
            Assert.assertNotNull(userTest.getAuthorities());
        }
    }

    @Test
    public void testSaveParceiro() {
        Usuario usuario = new Usuario();
        usuario.setNome("Parceiro Teste");
        usuario.setUsername("parceiro");
        usuario.setEnabled(true);
        usuario.setPassword(this.passwordEncoder.encode("parceiro"));
        usuario.setAuthorities(new ArrayList<>());
        Authorities roleAdmin = new Authorities();
        roleAdmin.setUsuario(usuario);
        roleAdmin.setUsername(usuario.getUsername());
        roleAdmin.setAuthority(RolesSecurityEnum.ROLE_PARCEIRO.name());
        usuario.getAuthorities().add(roleAdmin);
        if (usuarioService.canSaveUsuario(usuario)) {
            usuarioService.salvar(usuario);
            Usuario userTest = usuarioService.loadUsuarioWithAuthoritiesByUsername(usuario.getUsername());
            Assert.assertNotNull(userTest);
            Assert.assertNotNull(userTest.getAuthorities());
        }
    }

//    public void testeCascata() {
//        Cliente c = this.clien.findById(1L).get();
//        c.getPessoa().setEndereco(null);
//        clien.save(c);
//    }

}
