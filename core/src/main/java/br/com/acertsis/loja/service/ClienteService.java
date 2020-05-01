package br.com.acertsis.loja.service;

import br.com.acertsis.loja.acesso.ApiNubo;
import br.com.acertsis.loja.acesso.nubo.request.RequestException;
import br.com.acertsis.loja.dao.ClienteDAO;
import br.com.acertsis.loja.dto.FinalizarCompraDTO;
import br.com.acertsis.loja.entity.*;

import java.io.IOException;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ClienteService {

    @Autowired
    ClienteDAO clienteDAO;

    @Autowired
    PessoaService pessoaService;

    @Autowired
    ParceiroService parceiroService;

    @Autowired
    TelefoneService telefoneService;

    @Autowired
    EnderecoService enderecoService;

    @Autowired
    PessoaJuridicaService pessoaJuridicaService;

    @Autowired
    PessoaFisicaService pessoaFisicaService;

    public Cliente manterCliente(Cliente cliente) {
        return clienteDAO.save(cliente);
    }

    public Cliente cadastrarCliente(FinalizarCompraDTO finalizar) {
        Cliente cliente = new Cliente();

        PessoaFisica pessoaFisica = pessoaFisicaService.buscaPessoaCpf(finalizar.getCpf()).orElse(new PessoaFisica());
        if (pessoaFisica.getIdPessoa() == null) {
            pessoaFisica.setCpf(finalizar.getCpf());
            pessoaFisica.setDtNascimento(finalizar.getDatanasc());
            pessoaFisica.setNome(finalizar.getNome());
            pessoaFisica.setTipoPessoa(PessoaEnum.FISICA);
        }

        if (PessoaEnum.JURIDICA.equals(finalizar.getPessoaEnum())) {
            PessoaJuridica pessoaJuridica = pessoaJuridicaService.buscaPessoaCnpj(finalizar.getCnpj()).orElse(new PessoaJuridica());
            if (pessoaJuridica.getIdPessoa() == null) {
                pessoaJuridica.setCnpj(finalizar.getCnpj());
                pessoaJuridica.setRazaoSocial(finalizar.getRazaoSocial());
                pessoaJuridica.setTipoPessoa(PessoaEnum.JURIDICA);
            }
            cliente.setPessoa(pessoaJuridica);
            cliente.setResponsavel(pessoaFisica);
        } else {
            cliente.setPessoa(pessoaFisica);
        }

        if (cliente.getPessoa().getIdPessoa() != null) {
            cliente = clienteDAO.findByPessoa(cliente.getPessoa()).orElse(cliente);
        }

        Parceiro parceiro = parceiroService.bucarParceiro(finalizar.getParceiro());
        cliente.setParceiro(parceiro);
        /*****
         * Atualizar dados cadastrais de pessoa
         */
        cliente.getPessoa().setEmail(finalizar.getEmail());

        Telefone telefone = new Telefone(TelefoneEnum.GERAL, finalizar.getTelefone());
        telefone.setPessoa(cliente.getPessoa());
        telefone = telefoneService.manterTelefone(telefone);
        cliente.getPessoa().setTelefones(new ArrayList<>());
        cliente.getPessoa().getTelefones().add(telefone);
        cliente.setHabilitado(true);

        Endereco endereco = new Endereco(finalizar.getEndereco(), finalizar.getComplemento(), finalizar.getCep(),
                Integer.parseInt(finalizar.getNumero()), finalizar.getBairro(), finalizar.getCidade(), finalizar.getEstado());
        endereco.setPessoa(cliente.getPessoa());
        endereco = enderecoService.manterEndereco(endereco);
        cliente.getPessoa().setEndereco(new ArrayList<>());
        cliente.getPessoa().getEndereco().add(endereco);
        return clienteDAO.save(cliente);
    }

    public Cliente getClienteResponsavel(Long id) {
        return clienteDAO.findResponsavel(id);
    }

    public List<Cliente> buscarClienteAutoComplete(String nome, Parceiro parceiro) {
        List<Cliente> clientes = new ArrayList<>();
        clientes.addAll(clienteDAO.findByNomeFantasiaIlike("%" + nome + "%", parceiro).orElse(new ArrayList<>()));
        clientes.addAll(clienteDAO.findByNomeIlike("%" + nome + "%", parceiro).orElse(new ArrayList<>()));

        return clientes;
    }


}
