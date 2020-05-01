/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.bean;

import br.com.acertsis.loja.dao.CategoriaDAO;
import br.com.acertsis.loja.dao.EstadoDAO;
import br.com.acertsis.loja.dao.ModuloDAO;
import br.com.acertsis.loja.dao.ProdutoDAO;
import br.com.acertsis.loja.entity.Banco;
import br.com.acertsis.loja.entity.BrandCardEnum;
import br.com.acertsis.loja.entity.Categoria;
import br.com.acertsis.loja.entity.Estado;
import br.com.acertsis.loja.entity.MeioPagamentoEnum;
import br.com.acertsis.loja.entity.ModoComissaoParceiroEnum;
import br.com.acertsis.loja.entity.Modulo;
import br.com.acertsis.loja.entity.PessoaEnum;
import br.com.acertsis.loja.entity.Produto;
import br.com.acertsis.loja.entity.StatusParceiroEnum;
import br.com.acertsis.loja.entity.TelefoneEnum;
import br.com.acertsis.loja.entity.TipoContaBancariaEnum;
import br.com.acertsis.loja.security.RolesSecurityEnum;
import br.com.acertsis.loja.service.BancoService;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Named
@ViewScoped
public class DataProviderMb implements Serializable {

    @Inject
    private ProdutoDAO produtoDAO;

    @Inject
    private CategoriaDAO categoriaDAO;

    @Inject
    private ModuloDAO moduloDAO;

    @Inject
    private EstadoDAO estadoDAO;

    @Inject
    private BancoService bancoService;

    @Getter
    private List<Categoria> categorias;

    @Getter
    private List<Produto> produtos;

    @Getter
    private List<Modulo> modulos;

    @Getter
    private List<PessoaEnum> tiposPessoa;

    @Getter
    private List<StatusParceiroEnum> statusParceiroEnums;

    @Getter
    private List<Integer> mesesVencimentoCartao;

    @Getter
    private List<Integer> anosVencimentoCartao;

    @Getter
    private List<Integer> parcelasCartao;

    @Getter
    private List<BrandCardEnum> banderiasCartao;

    @Getter
    private List<ModoComissaoParceiroEnum> modoComissaoParceiro;
    @Getter
    private List<TipoContaBancariaEnum> tipoContaBancariaEnum;
    @Getter
    private List<RolesSecurityEnum> rolesSecurityEnums;
    @Getter
    private List<TelefoneEnum> tipoTelefones;
    @Getter
    private List<MeioPagamentoEnum> meiosPagamentos;
    @Getter
    private List<Estado> estados;

    @PostConstruct
    private void init() {
        this.categorias = new ArrayList<Categoria>();
        Optional.ofNullable(categoriaDAO.findAll()).orElse(new ArrayList<>()).forEach((it) -> categorias.add(it));

        this.produtos = new ArrayList<Produto>();
        Optional.ofNullable(produtoDAO.findAll()).orElse(new ArrayList<>()).forEach((it) -> produtos.add(it));

        this.modulos = new ArrayList<Modulo>();
        Optional.ofNullable(moduloDAO.findAll()).orElse(new ArrayList<>()).forEach((it) -> modulos.add(it));

        this.tiposPessoa = PessoaEnum.toList();

        this.statusParceiroEnums = StatusParceiroEnum.toList();

        this.mesesVencimentoCartao = new ArrayList<>();
        for (int c = 1; c <= 12; c++) {
            this.mesesVencimentoCartao.add(c);
        }

        this.parcelasCartao = new ArrayList<>();
        for (int c = 1; c <= 12; c++) {
            this.parcelasCartao.add(c);
        }

        this.anosVencimentoCartao = new ArrayList<>();
        for (int c = LocalDate.now().getYear(); c <= LocalDate.now().getYear() + 10; c++) {
            this.anosVencimentoCartao.add(c);
        }

        this.banderiasCartao = BrandCardEnum.toList();

        this.modoComissaoParceiro = ModoComissaoParceiroEnum.toList();

        this.tipoContaBancariaEnum = TipoContaBancariaEnum.toList();

        this.rolesSecurityEnums = RolesSecurityEnum.toList();

        this.tipoTelefones = TelefoneEnum.toList();

        this.meiosPagamentos = MeioPagamentoEnum.toList();

        this.estados = estadoDAO.listAllOrderBySigla();

    }

    public List<Banco> autoCompleteBanco(String nome) {
        List<Banco> bancos = null;
        if (nome != null && nome.length() >= 3) {
            bancos = this.bancoService.findFirst10ByNomeOrCodigo(nome).orElse(new ArrayList<>());
        }
        return bancos;
    }

    public List<Integer> getNumeroParcelasMax(Integer max) {
        List<Integer> parcelas = new ArrayList<>();
        parcelasCartao.forEach((it) -> {
            if (it <= max) {
                parcelas.add(it);
            }
        });
        return parcelas;
    }

}
