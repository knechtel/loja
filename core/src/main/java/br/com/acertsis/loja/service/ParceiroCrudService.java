/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.service;

import br.com.acertsis.loja.dao.AuthoritiesDAO;
import br.com.acertsis.loja.dao.EnderecoDAO;
import br.com.acertsis.loja.dao.ParceiroDAO;
import br.com.acertsis.loja.dao.PessoaDAO;
import br.com.acertsis.loja.dao.TelefoneDAO;
import br.com.acertsis.loja.dao.UsuarioDAO;
import br.com.acertsis.loja.entity.Authorities;
import br.com.acertsis.loja.entity.Endereco;
import br.com.acertsis.loja.entity.Parceiro;
import br.com.acertsis.loja.entity.Pessoa;
import br.com.acertsis.loja.entity.PessoaFisica;
import br.com.acertsis.loja.entity.PessoaJuridica;
import br.com.acertsis.loja.entity.StatusParceiroEnum;
import br.com.acertsis.loja.entity.Telefone;
import br.com.acertsis.loja.entity.Usuario;
import br.com.acertsis.loja.security.MyBCryptPasswordEncoder;
import br.com.acertsis.loja.security.RolesSecurityEnum;
import br.com.acertsis.loja.service.email.EmailsPadraoService;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.mail.MessagingException;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ParceiroCrudService implements CrudService<Parceiro, Long> {

    @Inject
    private ParceiroDAO parceiroDAO;
    @Inject
    private PessoaDAO pessoaDAO;
    @Inject
    private TelefoneDAO telefoneDAO;
    @Inject
    private EnderecoDAO enderecoDAO;
    @Inject
    private UsuarioDAO usuarioDAO;
    @Inject
    private AuthoritiesDAO authoritiesDAO;
    @Inject
    private EmailsPadraoService emailService;
    @Inject
    private MyBCryptPasswordEncoder passwordEncoder;
    @Override
    public void save(Parceiro entity) {
        parceiroDAO.save(entity);
    }

    @Transactional
    public void save(Parceiro entity, List<Telefone> telefonesParaRemover) {
        Pessoa pessoa = entity.getPessoa();
        if (pessoa.getEndereco() != null && pessoa.getEndereco().size() == 0) {
            pessoa.setEndereco(null);
        }

        if (pessoa.getTelefones() != null && pessoa.getTelefones().size() == 0) {
            pessoa.setTelefones(null);
        }

        if (pessoa.getEndereco() != null) {
            pessoa.getEndereco().forEach((it) -> it.setPessoa(pessoa));
            this.enderecoDAO.saveAll(pessoa.getEndereco());
        }

        if (pessoa.getTelefones() != null) {
            pessoa.getTelefones().forEach((it) -> it.setPessoa(pessoa));
            this.telefoneDAO.saveAll(pessoa.getTelefones());
        }

        if (entity.getResponsavel() != null) {
            Pessoa respoPessoa = entity.getResponsavel();
            pessoaDAO.save(respoPessoa);
        }

        if (telefonesParaRemover != null) {
            telefonesParaRemover.forEach((it) -> telefoneDAO.delete(it));
        }

        parceiroDAO.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Optional<Parceiro> opt = this.findById(id);
        if (opt.isPresent()) {
            Parceiro cliente = opt.get();
            Pessoa pessoa = cliente.getPessoa();
            List<Endereco> enderecos = pessoa.getEndereco();
            List<Telefone> telefones = pessoa.getTelefones();

            pessoa.setTelefones(null);
            pessoa.setEndereco(null);
            cliente.setPessoa(null);
            cliente.setResponsavel(null);

            if (cliente.getResponsavel() != null) {
                Pessoa respoPessoa = cliente.getResponsavel();
                pessoaDAO.delete(respoPessoa);
            }

            this.telefoneDAO.deleteAll(telefones);
            this.enderecoDAO.deleteAll(enderecos);
            this.pessoaDAO.delete(pessoa);
            this.parceiroDAO.delete(cliente);
        }
    }


    @Override
    @Transactional
    public Optional<Parceiro> findById(Long id) {
        Optional<Parceiro> op = parceiroDAO.findById(id);

        Pessoa p = op.get().getPessoa();
        List<Endereco> enderecos = enderecoDAO.findEnderecoByPessoa(p);
        List<Telefone> telefones = telefoneDAO.findTelefoneByPessoa(p);
        p.setEndereco(enderecos);
        p.setTelefones(telefones);

        return op;
    }

    @Transactional
    public void aprovarCadastroParceiro(Long idParceiro) throws IOException, TemplateException, MailException, MessagingException {
        Parceiro parceiro = this.parceiroDAO.findAllByIdParceiro(idParceiro);
        parceiro.setStatus(StatusParceiroEnum.ATIVO);

        String login = parceiro.getPessoa().getEmail();
        String senha = null;
        if (verificarLoginExiste(login)) {
            if (parceiro.getPessoa() instanceof PessoaFisica) {
                login = ((PessoaFisica) parceiro.getPessoa()).getCpf();

            }
            if (verificarLoginExiste(login)) {
                if (parceiro.getPessoa() instanceof PessoaJuridica) {
                    login = ((PessoaJuridica) parceiro.getPessoa()).getCnpj();
                }
            }
        }
        Usuario usuario = new Usuario();
        senha = login;
        usuario.setNome(parceiro.getPessoa().getNome());
        usuario.setUsername(login);
        usuario.setPassword(passwordEncoder.encode(senha));
        usuario.setAuthorities(new ArrayList<>());
        Authorities a = new Authorities();
        a.setUsuario(usuario);
        a.setUsername(login);
        a.setAuthority(RolesSecurityEnum.ROLE_PARCEIRO.name());
        usuario.getAuthorities().add(a);
        usuario.setParceiro(parceiro);
        this.usuarioDAO.save(usuario);

        parceiroDAO.save(parceiro);
        this.emailService.confirmacaoParceria(parceiro, login, senha);
    }


    private boolean verificarLoginExiste(String login) {
        Usuario usuario = usuarioDAO.findByUsername(login);
        return usuario != null;
    }
}
