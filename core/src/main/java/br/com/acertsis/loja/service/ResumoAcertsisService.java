/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.service;

import br.com.acertsis.loja.dao.ContratoDAO;
import br.com.acertsis.loja.dto.ResumoAcertsisDTO;
import br.com.acertsis.loja.entity.Contrato;
import br.com.acertsis.loja.entity.Mensalidade;
import br.com.acertsis.loja.entity.Parceiro;
import br.com.acertsis.loja.entity.StatusContratoEnum;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service
public class ResumoAcertsisService {

    @Inject
    private ContratoDAO contratoDAO;

    public List<ResumoAcertsisDTO> listar() {
        List<ResumoAcertsisDTO> lista = new ArrayList<>();
        List<Contrato> contratosAtivos = contratoDAO.findByStatus(StatusContratoEnum.ATIVO);
        Map<Parceiro, List<Contrato>> grouped = agruparPorParceiro(contratosAtivos);
        grouped.keySet().forEach((Parceiro parceiro) -> {
            ResumoAcertsisDTO resumo = new ResumoAcertsisDTO();
            //inicializa o objeto de pessoa em parceiro
            parceiro.getPessoa().getNome();
            resumo.setParceiro(parceiro);
            resumo.setVendaRealizada(totalVendasRealizadas(grouped.get(parceiro)));
            lista.add(resumo);
        });

        return lista;
    }

    /**
     * Soma o total de vendas realizadas, utiliza o seguinte criterio: Soma
     * todos os contratos que possuem mensalidades e cujas mensalidades tenha
     * pelo menos um pagamento associado.
     *
     * @param contratos.
     * @return Total de vendas realizadas.
     */
    public long totalVendasRealizadas(List<Contrato> contratos) {
        return contratos.stream().filter((contrato)
                -> Optional.ofNullable(contrato.getMensalidades()).orElse(new ArrayList<Mensalidade>())
                        .stream().filter((mensalidade) -> Optional.ofNullable(mensalidade.getPagamento())
                        .isPresent()).findAny().isPresent()).count();
    }

    public Map<Parceiro, List<Contrato>> agruparPorParceiro(final List<Contrato> contratos) {
        Map<Parceiro, List<Contrato>> grouped = new HashMap<>();
        if (contratos != null && !contratos.isEmpty()) {
            grouped = contratos.stream().collect(Collectors.groupingBy((contrato) -> contrato.getCliente().getParceiro()));
        }
        return grouped;
    }
}
