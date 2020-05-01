/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.service;

import br.com.acertsis.loja.dao.ParceiroDAO;
import br.com.acertsis.loja.dao.RegraComissaoDAO;
import br.com.acertsis.loja.entity.Parceiro;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;

import br.com.acertsis.loja.entity.RegraComissao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegraComissaoService {

    @Inject
    private ParceiroDAO parceiroDAO;

    @Autowired
    RegraComissaoDAO regraComissaoDAO;

    public List<Parceiro> findParceiroPorNome(String nome) {
        List<Parceiro> parceiros = new ArrayList<>();
        parceiros.addAll(parceiroDAO.findByNomeIlike("%" + nome + "%").orElse(new ArrayList<>()));
        parceiros.addAll(parceiroDAO.findByNomeFantasiaIlike("%" + nome + "%").orElse(new ArrayList<>()));
        return parceiros;
    }

    public RegraComissao manterRegraComissao(RegraComissao regra){
        return regraComissaoDAO.save(regra);
    }

    public Optional<RegraComissao> buscarComissaoAtiva(Parceiro parceiro, LocalDate localDate){
        return regraComissaoDAO.findByParceiro(parceiro, localDate);
    }
}
