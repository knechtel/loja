/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.service;

import br.com.acertsis.loja.bean.exception.MudarSenhaUsuarioException;
import br.com.acertsis.loja.dao.AuthoritiesDAO;
import br.com.acertsis.loja.dao.ParceiroDAO;
import br.com.acertsis.loja.dao.UsuarioDAO;
import br.com.acertsis.loja.entity.Authorities;
import br.com.acertsis.loja.entity.Parceiro;
import br.com.acertsis.loja.entity.Usuario;
import br.com.acertsis.loja.security.MyBCryptPasswordEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminUsuarioService {

    @Inject
    private ParceiroDAO parceiroDAO;

    @Inject
    private UsuarioDAO usuarioDAO;

    @Inject
    private AuthoritiesDAO authoritiesDAO;

    @Inject
    private MyBCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void salvar(Usuario usuario) {
        usuarioDAO.save(usuario);
    }

    @Transactional
    public void mudarSenha(String senhaAtual, String novaSenha, String senhaConfirmacao, Long idUsuario) throws MudarSenhaUsuarioException {
        Usuario usuario = usuarioDAO.findById(idUsuario).get();
        if (!passwordEncoder.matches(senhaAtual, usuario.getPassword())) {
            throw new MudarSenhaUsuarioException(MudarSenhaUsuarioException.TipoExcecao.SENHA_ATUAL_INVALIDA,
                    "Senha atual não é correta.");
        }
        if (!novaSenha.equals(senhaConfirmacao)) {
            throw new MudarSenhaUsuarioException(MudarSenhaUsuarioException.TipoExcecao.SENHA_CONFIRMACAO_INVALIDA,
                    "Nova senha e senha de confirmação não são iguais.");
        }
        usuario.setPassword(passwordEncoder.encode(novaSenha));
        this.usuarioDAO.save(usuario);
    }

    public Usuario loadUsuarioWithAuthoritiesById(Long id) {
        Usuario user = usuarioDAO.findById(id).get();
        List<Authorities> auts = authoritiesDAO.findByUsernameAndUsuario(user.getUsername(), user);
        user.setAuthorities(auts);
        return user;
    }

    public Usuario loadUsuarioWithAuthoritiesByUsername(String username) {
        Usuario user = usuarioDAO.findByUsername(username);
        List<Authorities> auts = authoritiesDAO.findByUsernameAndUsuario(user.getUsername(), user);
        user.setAuthorities(auts);
        return user;
    }

    /**
     * Metodo utilizado para saber se um usuario existe no sistema. Na inclusão
     * um de um novo usuario, não pode ser salvo um username que ja exista. Na
     * edição deve-ser permitir alterar o usuario e permitir até que se altere o
     * username.
     *
     * @param usuario
     * @return
     */
    public boolean canSaveUsuario(Usuario usuario) {
        Usuario user = usuarioDAO.findByUsernameOrIdUsuario(usuario.getUsername(), usuario.getIdUsuario());
        if (user != null) {
            if (user.getIdUsuario().equals(usuario.getIdUsuario())) {
                return true;
            }
        }
        return Objects.isNull(user);
    }

    public List<Parceiro> findParceiroPorNome(String nome) {
        List<Parceiro> parceiros = new ArrayList<>();
        parceiros.addAll(parceiroDAO.findByNomeIlike("%" + nome + "%").orElse(new ArrayList<>()));
        parceiros.addAll(parceiroDAO.findByNomeFantasiaIlike("%" + nome + "%").orElse(new ArrayList<>()));
        return parceiros;
    }
}
