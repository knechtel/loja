/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.service;

import br.com.acertsis.loja.dao.ClienteDAO;
import br.com.acertsis.loja.dao.ContratoDAO;
import br.com.acertsis.loja.dao.MensalidadeDAO;
import br.com.acertsis.loja.dto.TableContratosDto;
import br.com.acertsis.loja.entity.Cliente;
import br.com.acertsis.loja.entity.Contrato;
import br.com.acertsis.loja.entity.Mensalidade;
import br.com.acertsis.loja.entity.Parceiro;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GerenciarVendaService {

    @Inject
    private ContratoService contratoService;
    @Inject
    private ClienteService clienteService;
    @Inject
    private ClienteDAO clienteDAO;
    @Inject
    private ContratoDAO contratoDAO;
    @Inject
    private MensalidadeDAO mensalidadeDAO;

    public List<TableContratosDto> listarContratosCliente(Cliente cliente) {
        List<Contrato> contratos = contratoService.findContratosByCliente(cliente);
        List<TableContratosDto> mensalidades = new ArrayList<>();
        contratos.forEach((contrato) -> {
            contrato.getMensalidades().forEach((mensalidade) -> mensalidades.add(TableContratosDto.create(contrato, mensalidade)));
        });
        return mensalidades;
    }

    public List<Contrato> listarContratos(Cliente cliente) {
        List<Contrato> contratos = contratoService.findContratosByCliente(cliente);
        if (contratos != null) {
            contratos.sort(Comparator.comparing((Contrato t) -> t.getIdContrato()).reversed());
        }
        return contratos;
    }

    public List<Cliente> buscarClienteAutoComplete(String nome, Parceiro parceiro) {
        List<Cliente> clientes = new ArrayList<>();
        clientes.addAll(clienteDAO.findByNomeFantasiaIlike("%" + nome + "%", parceiro).orElse(new ArrayList<>()));
        clientes.addAll(clienteDAO.findByNomeIlike("%" + nome + "%", parceiro).orElse(new ArrayList<>()));

        return clientes;
    }

    @Transactional
    public Contrato findContrato(Long idContrato) {
        return contratoDAO.findById(idContrato).get();
    }

    @Transactional
    public List<Mensalidade> findMensalidadesByContrato(Contrato contrato) {
        List<Mensalidade> mensalidades = null;
        mensalidades = this.mensalidadeDAO.findByContrato(contrato.getIdContrato());
        if (mensalidades == null) {
            mensalidades = new ArrayList<>();
        }
        return mensalidades;
    }

}
