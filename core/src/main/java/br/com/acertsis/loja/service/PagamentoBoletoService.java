package br.com.acertsis.loja.service;

import br.com.acertsis.loja.dao.PagamentoBoletoDAO;
import br.com.acertsis.loja.entity.PagamentoBoleto;
import org.springframework.stereotype.Service;
import javax.inject.Inject;


@Service
public class PagamentoBoletoService {

    @Inject
    private PagamentoBoletoDAO pagamentoBoletoDAO;

    public PagamentoBoleto save(PagamentoBoleto pagamentoBoleto) {
        return pagamentoBoletoDAO.save(pagamentoBoleto);
    }

    public PagamentoBoleto findByID(Long id) {
        return pagamentoBoletoDAO.findById(id).orElse(null);
    }

    public PagamentoBoleto findByReftran(String reftran){
        return pagamentoBoletoDAO.findByReftran(reftran);
    }

    public PagamentoBoleto merge(PagamentoBoleto pagamentoBoleto){
        return pagamentoBoletoDAO.save(pagamentoBoleto);
    }


}
