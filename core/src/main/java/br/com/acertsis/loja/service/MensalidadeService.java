package br.com.acertsis.loja.service;

import br.com.acertsis.loja.dao.MensalidadeDAO;
import br.com.acertsis.loja.dto.InadimplenciaDTO;
import br.com.acertsis.loja.entity.Mensalidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class MensalidadeService {

    @Autowired
    MensalidadeDAO mensalidadeDAO;

    public List<InadimplenciaDTO> listInadimplenciaDTO(){
        InadimplenciaDTO inadimplenciaDTO ;
        List<InadimplenciaDTO> lstInadimplencia = new ArrayList<>();
        List<Mensalidade> lstMensalidades = mensalidadeDAO.findMensalidadesVencidas().orElse( new ArrayList<>());

        for (Mensalidade mens: lstMensalidades) {
            inadimplenciaDTO = new InadimplenciaDTO();
            inadimplenciaDTO.setIdContrato(mens.getContrato().getIdContrato());
            lstInadimplencia.add(inadimplenciaDTO);
        }
        return lstInadimplencia;
    }

    public Mensalidade manterMensalidade(Mensalidade mensalidade){
        return mensalidadeDAO.save(mensalidade);
    }

    public  List<Mensalidade> getMensalidadesVencida(){
        return mensalidadeDAO.findMensalideVencida(LocalDate.now());
    }
    public  List<Mensalidade> getMensalidadesVencidaHoje(){
        return mensalidadeDAO.findMensalideDiaDeHoje(LocalDate.now());
    }

    public List<Mensalidade> findByContrato(Long id){
        return mensalidadeDAO.findByContrato(id);
    }

}
