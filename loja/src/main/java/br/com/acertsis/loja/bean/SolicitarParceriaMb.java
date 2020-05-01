package br.com.acertsis.loja.bean;

import br.com.acertsis.loja.entity.Endereco;
import br.com.acertsis.loja.entity.Parceiro;
import br.com.acertsis.loja.entity.PessoaEnum;
import br.com.acertsis.loja.entity.PessoaFisica;
import br.com.acertsis.loja.entity.PessoaJuridica;
import br.com.acertsis.loja.entity.ReferenciaBancaria;
import br.com.acertsis.loja.entity.Telefone;
import br.com.acertsis.loja.entity.TelefoneEnum;
import br.com.acertsis.loja.entity.TipoContaBancariaEnum;
import br.com.acertsis.loja.service.ParceiroService;
import br.com.acertsis.loja.service.PesquisarCepService;
import com.google.common.base.Strings;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Named
@ViewScoped
public class SolicitarParceriaMb extends AbstractManagedBean implements Serializable {

    private Logger logger = Logger.getGlobal();

    @Inject
    private ParceiroService parceiroService;

    @Inject
    private PesquisarCepService pesquisarCepService;

    @Getter
    @Setter
    private Parceiro parceiro;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private PessoaJuridica pessoaJuridica;
    @Getter
    @Setter
    private ReferenciaBancaria referenciaBancariaPessoaJuridica;

    @Getter
    @Setter
    private Endereco enderecoPessoaJuridica;

    @Getter
    @Setter
    private Telefone telefonePessoaJuridica;

    @Getter
    @Setter
    private String obsevacaoSolicitacaoParceriaJuridica;

    //pessoa fisica
    @Getter
    @Setter
    private PessoaFisica pessoaFisica;

    @Getter
    @Setter
    private ReferenciaBancaria referenciaBancariaPessoaFisica;

    @Getter
    @Setter
    private Endereco enderecoPessoaFisica;

    @Getter
    @Setter
    private Telefone telefonePessoaFisica;

    @PostConstruct
    private void init() {
        this.parceiro = new Parceiro();
        pessoaFisica = new PessoaFisica();
        pessoaJuridica = new PessoaJuridica();

        referenciaBancariaPessoaFisica = new ReferenciaBancaria();
        referenciaBancariaPessoaFisica.setTipoContaBancariaEnum(TipoContaBancariaEnum.FISICA);

        referenciaBancariaPessoaJuridica = new ReferenciaBancaria();
        referenciaBancariaPessoaJuridica.setTipoContaBancariaEnum(TipoContaBancariaEnum.FISICA);

        telefonePessoaFisica = new Telefone();
        telefonePessoaFisica.setTipoTelefone(TelefoneEnum.GERAL);
        telefonePessoaJuridica = new Telefone();
        telefonePessoaJuridica.setTipoTelefone(TelefoneEnum.GERAL);

        this.enderecoPessoaFisica = new Endereco();
        this.enderecoPessoaJuridica = new Endereco();

    }

    public String savePessoaFisica() {
        try {
            pessoaFisica.setEndereco(new ArrayList());
            pessoaFisica.getEndereco().add(this.enderecoPessoaFisica);
            parceiro.setPessoa(pessoaFisica);
            parceiro.setReferenciaBancaria(referenciaBancariaPessoaFisica);
            parceiro.setTipoPessoa(PessoaEnum.FISICA);
            parceiro.setDtSolicitacao(LocalDate.now());
            if (!Strings.isNullOrEmpty(telefonePessoaFisica.getNumero())) {
                pessoaFisica.setTelefones(new ArrayList<>());
                if (!Strings.isNullOrEmpty(telefonePessoaFisica.getNumero())) {
                    pessoaFisica.getTelefones().add(telefonePessoaFisica);
                }
            }

            this.parceiroService.solicitarCredenciamento(parceiro);
            return "confirma_envio.xhtml";
        } catch (Exception ex) {
            addMensagemErroFlash("Erro ao solicitar credenciamento por favor tente mais tarde.");
            Logger.getLogger(SolicitarParceriaMb.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String savePessoaJuridica() {
        try {
            pessoaJuridica.setEndereco(new ArrayList());
            pessoaJuridica.getEndereco().add(this.enderecoPessoaJuridica);
            parceiro.setObsevacaoSolicitacaoParceria(obsevacaoSolicitacaoParceriaJuridica);
            parceiro.setPessoa(pessoaJuridica);
            parceiro.setReferenciaBancaria(referenciaBancariaPessoaJuridica);
            parceiro.setTipoPessoa(PessoaEnum.JURIDICA);
            parceiro.setDtSolicitacao(LocalDate.now());
            if (!Strings.isNullOrEmpty(telefonePessoaJuridica.getNumero())) {
                pessoaJuridica.setTelefones(new ArrayList<>());
                if (!Strings.isNullOrEmpty(telefonePessoaJuridica.getNumero())) {
                    pessoaJuridica.getTelefones().add(telefonePessoaJuridica);
                }
            }
            parceiro.getPessoa().setEmail(email);
            this.parceiroService.solicitarCredenciamento(parceiro);
            return "confirma_envio.xhtml";
        } catch (Exception ex) {
            addMensagemErroFlash("Erro ao solicitar credenciamento por favor tente mais tarde.");
            Logger.getLogger(SolicitarParceriaMb.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void preencherEnderecoPessoaFisica() {
        if (getEnderecoPessoaFisica().getCep() != null) {
            Map<String, String> maps = pesquisarCepService.pesquisar(getEnderecoPessoaFisica().getCep());
            getEnderecoPessoaFisica().setBairro(maps.get("bairro"));
            getEnderecoPessoaFisica().setCidade(maps.get("localidade"));
            getEnderecoPessoaFisica().setEstado(maps.get("uf"));
            getEnderecoPessoaFisica().setRua(maps.get("logradouro"));
        }
    }

    public void preencherEnderecoPessoaJuridica() {
        if (getEnderecoPessoaJuridica().getCep() != null) {
            Map<String, String> maps = pesquisarCepService.pesquisar(getEnderecoPessoaJuridica().getCep());
            getEnderecoPessoaJuridica().setBairro(maps.get("bairro"));
            getEnderecoPessoaJuridica().setCidade(maps.get("localidade"));
            getEnderecoPessoaJuridica().setEstado(maps.get("uf"));
            getEnderecoPessoaJuridica().setRua(maps.get("logradouro"));
        }
    }
}
