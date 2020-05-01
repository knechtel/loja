package br.com.acertsis.loja.service;

import br.com.acertsis.loja.dao.ContratoDAO;
import br.com.acertsis.loja.dto.NotaFiscalRenovacaoDTO;
import br.com.acertsis.loja.dto.PotencialVendasDTO;
import br.com.acertsis.loja.entity.*;
import br.com.acertsis.loja.exception.BusinessException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ContratoService {
    @Autowired
    ContratoDAO contratoDAO;

    @Autowired
    MensalidadeService mensalidadeService;

    @Autowired
    PagamentoRecorrenciaService pagamentoRecorrenciaService;

    @Autowired
    RegraComissaoService regraComissaoService;

    @Autowired
    PlanoService planoService;

    @Autowired
    ClienteService clienteService;

    @Autowired
    ContaPlataformaService plataformaService;

    public List<NotaFiscalRenovacaoDTO> listNotaFiscalRenovacao(){
        List<NotaFiscalRenovacaoDTO> lstNtFiscalRenovacao = new ArrayList<>();
        List<Contrato> lstContratos;
        NotaFiscalRenovacaoDTO notaFiscalRenovacaoDTO;
        LocalDate ld = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatterMonthYear = DateTimeFormatter.ofPattern("MM-yyyy");

        Double countValorTotal = 0.0;
        //get last 4 months
        for(int i = 0; i < 4; i++){
            notaFiscalRenovacaoDTO = new NotaFiscalRenovacaoDTO();
            lstContratos = new ArrayList<>();
            lstContratos =  contratoDAO.listByYearAndMonthByDtFim(ld.minusMonths(i).format(formatter)).orElse(new ArrayList<>());
            for (Contrato cont: lstContratos) {
                countValorTotal = countValorTotal + cont.getValorTotal();
            }
            notaFiscalRenovacaoDTO.setValorComissao(countValorTotal);
            notaFiscalRenovacaoDTO.setQuantidade(lstContratos.size());
            notaFiscalRenovacaoDTO.setMes(ld.minusMonths(i).format(formatterMonthYear));

            lstNtFiscalRenovacao.add(notaFiscalRenovacaoDTO);
        }

        return lstNtFiscalRenovacao;
    }

    public List<PotencialVendasDTO> listPotenciasDeVenda(Parceiro parceiro){
        List<Contrato> lstCont;
        List<PotencialVendasDTO> lstPotenciaisVenda = new ArrayList<>();
        PotencialVendasDTO potDTO;
        LocalDate ld = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatterMonthYear = DateTimeFormatter.ofPattern("MM-yyyy");

        //list last 4 months
        for(int i =0; i< 4; i++){
            potDTO = new PotencialVendasDTO();
            lstCont = new ArrayList<>();
            lstCont = contratoDAO.listSalesByMonthOfUser(ld.minusMonths(i).format(formatter), parceiro.getIdParceiro()).orElse(new ArrayList<>());

            potDTO.setQtd(lstCont.size());
            potDTO.setData(ld.minusMonths(i).format(formatterMonthYear));
            lstPotenciaisVenda.add(potDTO);
        }
        return  lstPotenciaisVenda;
    }

    public Contrato manterContato(Contrato contrato){
        contrato = contratoDAO.save(contrato);
        return contratoDAO.save(contrato);
    }

    public Contrato gerarContratacao(Cliente cliente, Plano planoSelecionado, MeioPagamentoEnum tipo, int numParcelas) throws BusinessException {
        Contrato contrato = gerarContratacaoNaoSalvar(cliente, planoSelecionado, tipo, numParcelas);
        return contratoDAO.save(contrato);
    }

    public Contrato gerarContratacaoNaoSalvar(Cliente cliente, Plano planoSelecionado, MeioPagamentoEnum tipo, int numParcelas) throws BusinessException {
        if (numParcelas == 0 || cliente.getIdCliente() == null || planoSelecionado.getIdPlano() == null || tipo == null) {
            throw new BusinessException("Erro ao gerar o contrato. Verifique as informações!");
        }

        Plano plano = planoService.bucarPlanoId(planoSelecionado.getIdPlano()).orElseThrow(() -> new BusinessException("Nenhum plano foi selecionado!"));
        RegraComissao regraComissao = regraComissaoService.buscarComissaoAtiva(cliente.getParceiro(), LocalDate.now()).orElseThrow(() -> new BusinessException("Parceiro não possue regra de comissão cadastrada!"));

        Contrato contrato = new Contrato();
        contrato.setMensalidades(new ArrayList<>());
        contrato.setPlano(plano);
        contrato.setRegraComissao(regraComissao);
        contrato.setCliente(cliente);
        contrato.setValorTotal(plano.getValorPlano() + regraComissao.getTxAdesao());
        contrato.setNumParcelas(numParcelas);
        contrato.setTipoPagamento(tipo);
        contrato.setStatus(StatusContratoEnum.PENDENTEPAG);


        double valorMensal = contrato.getValorTotal() / contrato.getNumParcelas();
        valorMensal = Math.round(valorMensal * 100.0) / 100.0;

        double diferenca = contrato.getValorTotal() - (valorMensal * contrato.getNumParcelas());
        diferenca = Math.round(diferenca * 100.0) / 100.0;

        for (int i = 1; i <= contrato.getNumParcelas(); i++) {
            LocalDate dtVenc = LocalDate.now();
            diferenca = 0;
            if (MeioPagamentoEnum.BOLETO.equals(tipo)) {
                if (i == 1) {
                    dtVenc = dtVenc.plusDays(5);
                }
            }
            Mensalidade mensalidade = new Mensalidade(i, valorMensal + diferenca, StatusPagamentoEnum.AGUARDANDO, dtVenc, contrato, tipo);
            dtVenc = dtVenc.plusMonths(i);

            if (DayOfWeek.SUNDAY == dtVenc.getDayOfWeek()) {
                dtVenc = dtVenc.plusDays(1);
            }
            contrato.getMensalidades().add(mensalidade);
        }

        //adicionado dia 13/08/2019
        contrato.setDtInicio(LocalDate.now());
        contrato.setDtFim(LocalDate.now().plusMonths(11));
        return contrato;
    }

    public List<Contrato> findContratosByCliente(Cliente cliente) {
        return this.contratoDAO.findByCliente(cliente);
    }

    public Contrato findContrato(Long id){
        return contratoDAO.findById(id).orElse(null);
    }

    @Transactional
    public List<Contrato> listClientesGerarConta() {
        return this.contratoDAO.listContratoAccountCreation();
    }

}
