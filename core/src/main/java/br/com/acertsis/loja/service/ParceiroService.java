/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.service;

import br.com.acertsis.loja.dao.ParceiroDAO;
import br.com.acertsis.loja.entity.Contrato;
import br.com.acertsis.loja.entity.Parceiro;
import br.com.acertsis.loja.entity.StatusParceiroEnum;
import br.com.acertsis.loja.service.email.EmailsPadraoService;
import freemarker.template.TemplateException;
import java.io.IOException;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import javax.mail.MessagingException;
import org.springframework.mail.MailException;

@Service
@Transactional
public class ParceiroService {

    @Autowired
    private ParceiroDAO parceiroDAO;
    @Inject
    private EmailsPadraoService emailsPadraoService;

    @Transactional
    public void solicitarCredenciamento(Parceiro parceiro) throws IOException, TemplateException, MailException, MessagingException {
        parceiro.setStatus(StatusParceiroEnum.SOLICITADO);
        parceiroDAO.save(parceiro);
        emailsPadraoService.solicitacaoParceria(parceiro);
    }

    public LinkedHashMap<Parceiro, Long> mapParceirosAndSales(List<Contrato> lstContratosSales) {

        //guardo em hashmap como chave o id cliente e valor a quantidade de contratos gerados
        HashMap<Long, Long> contParceiros = new HashMap<>();

        for (Contrato contrato : lstContratosSales) {
            if (contrato.getCliente().getParceiro() != null) {
                if (!contParceiros.containsKey(contrato.getCliente().getParceiro().getIdParceiro())) {
                    contParceiros.put(contrato.getCliente().getParceiro().getIdParceiro(),
                            lstContratosSales.stream().filter(
                                    contr -> contrato.getCliente().getParceiro().getIdParceiro()
                                    == contr.getCliente().getParceiro().getIdParceiro()
                            ).count());
                }
            }
        }

        //orderhashmap
        LinkedHashMap<Long, Long> lkdMapSorted = sortHashMapByValues(contParceiros);
        LinkedHashMap<Parceiro, Long> lkdMap = new LinkedHashMap<>();

        for (Map.Entry<Long, Long> entry : lkdMapSorted.entrySet()) {
            lkdMap.put(parceiroDAO.findAllByIdParceiro(entry.getKey()), entry.getValue());
        }

        return lkdMap;
    }

    public Parceiro manterParceiro(Parceiro parceiro) {
        return parceiroDAO.save(parceiro);
    }

    public Parceiro buscaParceiroPadrao() {
        return parceiroDAO.findByRaiz(true);
    }

    public Parceiro buscaParceiroById(Parceiro parceiro) {
        return parceiroDAO.findById(parceiro.getIdParceiro()).orElse(new Parceiro());
    }

    public Parceiro bucarParceiro(Parceiro parceiro) {
        if (parceiro == null) {
            parceiro = parceiroDAO.findByRaiz(true);
        } else {
            parceiro = parceiroDAO.findById(parceiro.getIdParceiro()).get();
        }
        return parceiro;
    }

    private LinkedHashMap<Long, Long> sortHashMapByValues(HashMap<Long, Long> passedMap) {
        List<Long> mapKeys = new ArrayList<>(passedMap.keySet());
        List<Long> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);

        Iterator<Long> valueIt = mapValues.iterator();

        LinkedHashMap<Long, Long> sortedMap = new LinkedHashMap<>();

        while (valueIt.hasNext()) {
            Long val = valueIt.next();
            Iterator<Long> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                Long key = keyIt.next();
                Long comp1 = passedMap.get(key);
                Long comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }
}
