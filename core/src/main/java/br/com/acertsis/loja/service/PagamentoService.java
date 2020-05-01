/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.service;

import br.com.acertsis.loja.dao.*;
import br.com.acertsis.loja.dto.BoletoBancoDoBrasilDTO;
import br.com.acertsis.loja.entity.*;
import br.com.acertsis.loja.exception.BusinessException;
import br.com.acertsis.loja.service.email.EmailsPadraoService;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.text.ParseException;
import javax.inject.Inject;
import javax.mail.MessagingException;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class PagamentoService {

    // @Inject
    //private EmailService emailService;
    @Inject
    private ParceiroDAO parceiroDAO;

    @Inject
    private BoletoBancoBrasilService boletoBancoBrasilService;
    @Inject
    private PagamentoBoletoDAO pagamentoBoletoDAO;
    @Inject
    private EmailsPadraoService emailsPadraoService;

    @Transactional(rollbackFor = Exception.class)
    public byte[] gerarBoleto(Plano plano, Cliente cliente, PagamentoBoleto pagamento)
            throws MessagingException, MailException, IOException, TemplateException, BusinessException, ParseException {
        byte[] bytes = null;
        pagamentoBoletoDAO.save(pagamento);
        String reTran = pagamentoBoletoDAO.getUltimoRefTran();
        if (reTran == null) {
            reTran = BoletoBancoDoBrasilDTO.MONTAR_REF_TRAN(boletoBancoBrasilService.getNumeroRefTran(), pagamento.getIdPagamento() + boletoBancoBrasilService.getNumeroRefTranInicial());
        } else {
            Long novoRef = BoletoBancoDoBrasilDTO.REVERTER_REF_TRAN(boletoBancoBrasilService.getNumeroRefTran(), reTran) + 1;
            if (novoRef < boletoBancoBrasilService.getNumeroRefTranInicial()) {
                novoRef = boletoBancoBrasilService.getNumeroRefTranInicial();
            }
            reTran = BoletoBancoDoBrasilDTO.MONTAR_REF_TRAN(boletoBancoBrasilService.getNumeroRefTran(), novoRef);
        }
        BoletoBancoDoBrasilDTO boletoBB = BoletoBancoDoBrasilDTO
                .create(cliente, pagamento, boletoBancoBrasilService.getNumeroConvenio(), reTran, boletoBancoBrasilService.getMsgLoja());

        BoletoBancoBrasilService.RetornoBoleto ret = boletoBancoBrasilService.emitirBoleto(boletoBB);

        bytes = ret.getDados();
        pagamento.setRefTran(ret.getRefTran());
        pagamentoBoletoDAO.save(pagamento);
        if (bytes == null || bytes.length == 0) {
            throw new RuntimeException("Erro ao gerar boleto.");
        }
        emailsPadraoService.novaCompraClienteBoleto(cliente, pagamento.getMensalidade().getContrato(), bytes);
        return bytes;
    }
}
