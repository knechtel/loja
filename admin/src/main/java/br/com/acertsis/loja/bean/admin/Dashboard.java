/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.bean.admin;

import br.com.acertsis.loja.bean.SessionMB;
import br.com.acertsis.loja.dao.ContratoDAO;
import br.com.acertsis.loja.dto.PotencialVendasDTO;
import br.com.acertsis.loja.entity.Parceiro;
import br.com.acertsis.loja.service.ParceiroService;

import java.io.Serializable;
import java.util.*;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import br.com.acertsis.loja.dto.NotaFiscalRenovacaoDTO;
import br.com.acertsis.loja.entity.Contrato;
import br.com.acertsis.loja.entity.Usuario;
import br.com.acertsis.loja.service.ClienteService;
import br.com.acertsis.loja.service.ContratoService;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.chart.*;
import org.springframework.stereotype.Component;

@Component
@Named
@ViewScoped
public class Dashboard implements Serializable {

    @Inject
    private ClienteService clienteService;

    @Inject
    private ParceiroService parceiroService;


    @Inject
    private ContratoService contratoService;

    @Inject
    private SessionMB sessionMB;

    @Getter
    @Setter
    private List<Contrato> lstContratosCurrentMonth;

    @Getter
    @Setter
    private List<Contrato> lstContratosCurrentYear;

    @Getter
    @Setter
    private List<NotaFiscalRenovacaoDTO> lstPlanosParaRenovar;

    @Getter
    @Setter
    private List<Parceiro> lstVendasCurrentMonth;


    @Getter
    @Setter
    private List<Parceiro> lstVendasCurrentYear;

    @Getter
    @Setter
    private List<PotencialVendasDTO> lstContratoLastMonths;

    @Getter
    @Setter
    private String nomeUsuarioLogado;

    @Getter
    @Setter
    private String posicaoUsuarioLogado;

    @Inject
    private ContratoDAO contratoDAO;

    private LinkedHashMap<Parceiro, Long> mapParceiroSaleCurrentMonth;
    private LinkedHashMap<Parceiro, Long> mapParceiroSaleCurrentYear;
    private PieChartModel pieModel1;
    private BarChartModel barModel;

    @PostConstruct
    public void init() {
        listTopParceirosCurrentMonth();
        listTopClientsCurrentYear();
        listSalesLastMonths();
        listPlanosParaRenovar();
        createPieModels();
        createBarModel();
        convertMapParceiroSaleForListMonth();
        convertMapClientSaleforListYear();
        nomeUsuarioLogado = sessionMB.getUsuarioLogado().getUsername();
    }

    public void listPlanosParaRenovar(){
        this.lstPlanosParaRenovar = new ArrayList<>();
        this.lstPlanosParaRenovar = contratoService.listNotaFiscalRenovacao();

    }

    public void listSalesLastMonths(){

        this.lstContratoLastMonths = new ArrayList<>();
        if(sessionMB.isUserParceiro()){
            this.lstContratoLastMonths = contratoService.listPotenciasDeVenda(sessionMB.getUsuarioLogado().getParceiro());
        }
    }

    public void listTopParceirosCurrentMonth() {
        //retorna id do cliente com maiores vendas
        this.lstContratosCurrentMonth = contratoDAO.listCurrentMonth().orElse(new ArrayList<>());
        this.mapParceiroSaleCurrentMonth =  parceiroService.mapParceirosAndSales(this.lstContratosCurrentMonth);
    }

    public void listTopClientsCurrentYear(){
        this.lstContratosCurrentYear = contratoDAO.listCurrentYear().orElse((new ArrayList<>()));
        this.mapParceiroSaleCurrentYear = parceiroService.mapParceirosAndSales(this.lstContratosCurrentYear);
    }

    public PieChartModel getPieModel1() {
        return pieModel1;
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    private void createPieModels() {
        createPieModel1();
    }

    private void createBarModel() {
        barModel = initBarModel();

        barModel.setLegendPosition("ne");
        barModel.setExtender("customExtender");
        Axis xAxis = barModel.getAxis(AxisType.X);
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(200);
    }

    private void createPieModel1() {
        pieModel1 = new PieChartModel();

        for (Map.Entry<Parceiro, Long> entry: this.mapParceiroSaleCurrentMonth.entrySet()) {
            pieModel1.set("Cód.: " + entry.getKey().getIdParceiro().toString() +
                    " Nome: " +
                    entry.getKey().getPessoa().getNome(), entry.getValue());
        }

        pieModel1.setExtender("customExtender");
        pieModel1.setLegendPosition("w");
        pieModel1.setShadow(false);
        pieModel1.setDiameter(150);
        pieModel1.setSeriesColors("DD4B39,4CA65A,2FC0EF,3D8DBC,D2D6DF");

    }

    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
        model.setSeriesColors("DD4B39,4CA65A,2FC0EF,3D8DBC,D2D6DF");

        for (Map.Entry<Parceiro, Long> entry: this.mapParceiroSaleCurrentYear.entrySet()) {

            ChartSeries cs = new ChartSeries();
            cs.setLabel(entry.getKey().getPessoa().getNome());
            cs.set("Quantidade de notas venddidas", entry.getValue());
            model.addSeries(cs);
        }

        return model;
    }

    private void convertMapParceiroSaleForListMonth(){
        ArrayList<Parceiro> lstParceiro = new ArrayList<>();
        this.lstVendasCurrentMonth = new ArrayList<>();


        for (Map.Entry<Parceiro, Long> entry: this.mapParceiroSaleCurrentMonth.entrySet()) {
            lstParceiro.add(entry.getKey());
        }



        //get top 5 clients more sales
        for(int i = lstParceiro.size() - 1; i > lstParceiro.size() - 6 && i != -1; i--){
            this.lstVendasCurrentMonth.add(lstParceiro.get(i));
        }

        Usuario user = sessionMB.getUsuarioLogado();
        Boolean usuarioEncontrado = false;
        int countPosition = lstParceiro.size();

        //get position user logged and is parceiro
        if(user.getParceiro() != null) {
            for (Parceiro parceiro : lstParceiro) {
                if (user.getParceiro().getIdParceiro() == parceiro.getIdParceiro()) {
                    posicaoUsuarioLogado = "" + countPosition;
                    usuarioEncontrado = true;
                    break;
                }
                countPosition--;
            }
            if(!usuarioEncontrado){
                posicaoUsuarioLogado = "Posição não encontrada";
            }
        }
    }

    private void convertMapClientSaleforListYear(){
        ArrayList<Parceiro> lstParceiro = new ArrayList<>();
        this.lstVendasCurrentYear = new ArrayList<>();

        for (Map.Entry<Parceiro, Long> entry: this.mapParceiroSaleCurrentYear.entrySet()) {
            lstParceiro.add(entry.getKey());
        }

        for(int i = lstParceiro.size() - 1; i > lstParceiro.size() - 6 && i != -1; i--){
            this.lstVendasCurrentYear.add(lstParceiro.get(i));
        }

    }

}
