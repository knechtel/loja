package br.com.acertsis.loja.service;

import br.com.acertsis.loja.dao.ClienteDAO;
import br.com.acertsis.loja.dao.EnderecoDAO;
import br.com.acertsis.loja.dao.ParceiroDAO;
import br.com.acertsis.loja.dao.PessoaDAO;
import br.com.acertsis.loja.dao.TelefoneDAO;
import br.com.acertsis.loja.dao.UsuarioDAO;
import br.com.acertsis.loja.entity.Cliente;
import br.com.acertsis.loja.entity.Endereco;
import br.com.acertsis.loja.entity.Parceiro;
import br.com.acertsis.loja.entity.Pessoa;
import br.com.acertsis.loja.entity.Telefone;
import br.com.acertsis.loja.entity.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClienteCrudService implements CrudService<Cliente, Long> {

    @Inject
    private ClienteDAO clienteDAO;
    @Inject
    private PessoaDAO pessoaDAO;
    @Inject
    private EnderecoDAO enderecoDAO;
    @Inject
    private TelefoneDAO telefoneDAO;
    @Inject
    private ParceiroDAO parceiroDAO;
    @Inject
    private UsuarioDAO usuarioDAO;

    @Transactional
    public void save(Cliente entity, List<Telefone> telefonesParaRemover) {
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
        clienteDAO.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Optional<Cliente> opt = this.findById(id);
        if (opt.isPresent()) {
            Cliente cliente = opt.get();
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
            this.clienteDAO.delete(cliente);
            this.clienteDAO.deleteById(id);
        }
    }

    @Override
    @Transactional
    public Optional<Cliente> findById(Long id) {
        Optional<Cliente> opt = this.clienteDAO.findById(id);
        List<Endereco> enderecos = this.enderecoDAO.findEnderecoByPessoa(opt.get().getPessoa());
        List<Telefone> telefones = this.telefoneDAO.findTelefoneByPessoa(opt.get().getPessoa());
        opt.get().getPessoa().setEndereco(enderecos);
        opt.get().getPessoa().setTelefones(telefones);

        return opt;
    }

    public Optional<List<Cliente>> findAllByParceiro(Parceiro parceiro) {
        return this.clienteDAO.findByParceiro(parceiro);
    }

    @Override
    public void save(Cliente entity) {
        this.clienteDAO.save(entity);
    }

    public List<Parceiro> findParceiroPorNome(String query) {
        query = "%" + query + "%";
        List<Parceiro> parceiros = new ArrayList<>();
        parceiros.addAll(this.parceiroDAO.findByNomeIlike(query).orElse(new ArrayList()));
        parceiros.addAll(this.parceiroDAO.findByNomeFantasiaIlike(query).orElse(new ArrayList()));
        return parceiros;
    }

    public Parceiro findParceiroByUsuario(Usuario usuario) {
        return this.usuarioDAO.findByUsernameOrIdUsuario(usuario.getUsername(), usuario.getIdUsuario()).getParceiro();
    }

    public Iterable<Cliente> findAll() {
        return this.clienteDAO.findAll();
    }
}
