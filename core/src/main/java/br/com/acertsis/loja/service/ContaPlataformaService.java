package br.com.acertsis.loja.service;

import br.com.acertsis.loja.acesso.ApiNubo;
import br.com.acertsis.loja.acesso.nubo.Account;
import br.com.acertsis.loja.acesso.nubo.License;
import br.com.acertsis.loja.acesso.nubo.LicensePackage;
import br.com.acertsis.loja.acesso.nubo.Login;
import br.com.acertsis.loja.acesso.nubo.request.RequestException;
import br.com.acertsis.loja.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContaPlataformaService {
    @Autowired
    ContratoService contratoService;

    private static final String APINUBOUSER = "api@acertsis.com.br";
    private static final String APINUBOPASSWORD = "acertsis123@@";
    Logger logger = LoggerFactory.getLogger(ContaPlataformaService.class);

    public ApiNubo authenticate(String dominio) throws IOException, RequestException {
        ApiNubo api = new ApiNubo();
        api.authenticate(new Login(APINUBOUSER, APINUBOPASSWORD, dominio));
        return api;
    }

    public Account createAccount(Account newAccount, String dominioParceiro, Long skuPlano ) throws IOException, RequestException {
        ApiNubo api = this.authenticate(dominioParceiro);
        return api.createAccount(newAccount);
    }

    public void generateNewAccount() {
        logger.info("ROTINA DE CRIAÇÃO DE CONTAS ->(INÍCIO)");
        List<Contrato> listContrato = contratoService.listClientesGerarConta();

        if ((listContrato != null && !listContrato.isEmpty())){
            String dominioParceiro = "";
            ApiNubo api = new ApiNubo();
            String customer;
            String dominio;
            logger.info("Existe contas a serem criadas!!!!!");

            for (Contrato contrato : listContrato) {
                try {
                    if (contrato.getCliente().getParceiro().getDominio() != null) {
                        if (!dominioParceiro.equalsIgnoreCase(contrato.getCliente().getParceiro().getDominio())) {
                            logger.info("Domínio do parceiro: " + contrato.getCliente().getParceiro().getDominio());
                            dominioParceiro = contrato.getCliente().getParceiro().getDominio();
                            api = this.authenticate(dominioParceiro);
                        }

                        if (PessoaEnum.JURIDICA.equals(contrato.getCliente().getPessoa().getTipoPessoa())) {
                            PessoaJuridica pessoaJuridica = (PessoaJuridica) contrato.getCliente().getPessoa();
                            customer = pessoaJuridica.getRazaoSocial();
                        } else {
                            PessoaFisica pessoaFisica = (PessoaFisica) contrato.getCliente().getPessoa();
                            customer = pessoaFisica.getNome();
                        }

                        dominio = customer.toLowerCase();
                        dominio = dominio.replace(" ", "");
                        dominio = dominio.replaceAll("[^a-zZ-Z1-9 ]", "");

                        Account newAccount = new Account();
                        newAccount.setCompanyName(customer);
                        newAccount.setSubdomain(dominio);
                        newAccount.setUsername(contrato.getCliente().getPessoa().getEmail());
                        newAccount.setLicenses(new ArrayList<>());
                        LicensePackage licensePackage = new LicensePackage();
                        licensePackage.setId(contrato.getPlano().getSku());
                        License license = new License();
                        license.setLicensePackage(licensePackage);
                        newAccount.getLicenses().add(license);

                        //newAccount = api.createAccount(newAccount);
                        logger.info("Conta criada ->" + newAccount.toString());

                        if (newAccount != null) {
                            contrato.setStatus(StatusContratoEnum.ATIVO);
                            contrato.setDtInicio(LocalDate.now());
                            contrato.setDtFim(LocalDate.now().plusMonths(12));
                            contrato.getCliente().setSku(newAccount.getId());
                            contrato.getCliente().setDominio(newAccount.getSubdomain());
                            contrato = contratoService.manterContato(contrato);
                        }
                    } else {
                        logger.info("Contrato ->"+contrato.getIdContrato()+". Não foi possível criar conta na plataforma. " +
                                                                                                "Parceiro sem domínio.");
                    }
                }catch (IOException e) {
                    logger.info("Contrato ->"+contrato.getIdContrato()+" Erro ->" + e.getMessage());
                    e.printStackTrace();
                } catch (RequestException e) {
                    logger.info("Contrato ->"+contrato.getIdContrato()+" Erro ->" +
                                                                e.getError().getCode()+":"+e.getError().getMessage());
                }
            }
        } else {
            logger.info("Não existe nenhuma conta a ser criada!");
        }
        logger.info("ROTINA DE CRIAÇÃO DE CONTAS ->(FIM)");
    }
}
