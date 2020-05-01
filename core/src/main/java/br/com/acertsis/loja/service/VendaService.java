/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.service;

import br.com.acertsis.loja.dao.PlanoDAO;
import br.com.acertsis.loja.dto.CartaoCreditoDTO;
import br.com.acertsis.loja.entity.Cliente;
import br.com.acertsis.loja.entity.Contrato;
import br.com.acertsis.loja.entity.MeioPagamentoEnum;
import br.com.acertsis.loja.entity.Mensalidade;
import br.com.acertsis.loja.entity.PagamentoBoleto;
import br.com.acertsis.loja.entity.Plano;
import br.com.acertsis.loja.entity.Produto;
import br.com.acertsis.loja.entity.StatusPagamentoEnum;
import br.com.acertsis.loja.exception.BusinessException;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.mail.MessagingException;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VendaService {

    @Inject
    private ClienteCrudService clienteCrudService;
    @Inject
    private PlanoDAO planoDAO;
    @Inject
    private PagamentoRecorrenciaService pagamentoRecorrenciaService;
    @Inject
    private PagamentoService pagamentoService;
    @Inject
    private ContratoService contratoService;



    public List<Plano> listPlanosByProduto(Produto produto) {
        return planoDAO.findPlanosHabilitadosByProduto(produto).orElse(new ArrayList<>());
    }

    @Transactional
    public Optional<Plano> getPlanoFetchModulosProdutos(Plano plano) {
        return this.planoDAO.findByIdFetchModulosProdutos(plano.getIdPlano());
    }


    @Transactional
    public byte[] pagarBoleto(final Cliente clienteParam, final Plano plano, final MeioPagamentoEnum meioPagamentoEnum, final int numParcelas) throws BusinessException, MessagingException, MailException, IOException, TemplateException, ParseException {
        if (clienteParam == null) {
            throw new BusinessException("Cliente não foi selecionado");
        }
        if (plano == null) {
            throw new BusinessException("Plano não foi selecionado.");
        }
        if (meioPagamentoEnum == null) {
            throw new BusinessException("MeioPagamento não foi selecionado.");
        }
        if (numParcelas == 0) {
            throw new BusinessException("Numero de parcela não pode ser zero.");
        }

        //buscar clientes com todos os valores necessarios preenchidos
        Cliente cliente = clienteCrudService.findById(clienteParam.getIdCliente()).get();

        if (cliente == null || cliente.getPessoa() == null) {
            throw new BusinessException("Cliente não foi selecionado");
        }
        if (cliente.getPessoa().getEndereco() == null || cliente.getPessoa().getEndereco().size() == 0) {
            throw new BusinessException("Endereço do Cliente não foi cadastrado entre no cadastro de cliente cadastre um endereço.");
        }
        if (cliente.getPessoa().getTelefones() == null || cliente.getPessoa().getTelefones().size() == 0) {
            throw new BusinessException("Telefone do Cliente não foi cadastrado entre no cadastro de cliente cadastre um telefone.");
        }


        Contrato contrato = this.contratoService.gerarContratacao(cliente, plano, meioPagamentoEnum, numParcelas);
        Mensalidade mensalidade = contrato.getMensalidades().stream().filter((m) -> m.getParcela().equals(1)).findFirst().get();
        PagamentoBoleto pagamento = new PagamentoBoleto();
        pagamento.setValorPago(mensalidade.getValor());
        pagamento.setMeioPagamentoEnum(MeioPagamentoEnum.BOLETO);
        pagamento.setMensalidade(mensalidade);
        pagamento.setDataVencimento(mensalidade.getDtVencimento());
        mensalidade.setPagamento(pagamento);
        for (int c = 1; c < contrato.getMensalidades().size(); c++) {
            Mensalidade m = contrato.getMensalidades().get(c);
            m.setStatus(StatusPagamentoEnum.BOLETO_NAO_EMITIDO);

        }
        byte[] bytes = null;
        bytes = pagamentoService.gerarBoleto(plano, cliente, pagamento);
//        FileInputStream fs = new FileInputStream(new File("/home/moacir/output13.pdf"));
//        bytes = new byte[fs.available()];
//        fs.read(bytes);
//        fs.close();

        return bytes;
    }

    @Transactional
    public void pagarCartaoCredito(final CartaoCreditoDTO cartaoCreditoDto, final Cliente clienteParam, final Plano plano, final MeioPagamentoEnum meioPagamentoEnum, final int numParcelas) throws BusinessException, MessagingException, MailException, IOException, TemplateException, ParseException {
        if (clienteParam == null) {
            throw new BusinessException("Cliente não foi selecionado");
        }
        if (plano == null) {
            throw new BusinessException("Plano não foi selecionado.");
        }
        if (meioPagamentoEnum == null) {
            throw new BusinessException("MeioPagamento não foi selecionado.");
        }
        if (numParcelas == 0) {
            throw new BusinessException("Numero de parcela não pode ser zero.");
        }

        if (cartaoCreditoDto == null) {
            throw new BusinessException("Dados Cartão de credito não inseridos.");
        }

        if (cartaoCreditoDto.getAnoVencimento() == null) {
            throw new BusinessException("Ano de vencimento do Cartão de Crédito não preenchido.");
        }
        if (cartaoCreditoDto.getBandeira() == null) {
            throw new BusinessException("Bandeira do Cartão de Crédito não preenchido.");
        }
        if (cartaoCreditoDto.getCodSeguranca() == null) {
            throw new BusinessException("Codigo verificação do Cartão de Crédito não preenchido.");
        }
        if (cartaoCreditoDto.getMesVencimento() == null) {
            throw new BusinessException("Mes de Vencimento do Cartão de Crédito não preenchido.");
        }

        if (cartaoCreditoDto.getNumero() == null) {
            throw new BusinessException("Número do Cartão de Crédito não preenchido.");
        }

        if (cartaoCreditoDto.getTitular() == null) {
            throw new BusinessException("Titular do Cartão de Crédito não preenchido.");
        }

        //buscar clientes com todos os valores necessarios preenchidos
        Cliente cliente = clienteCrudService.findById(clienteParam.getIdCliente()).get();

        if (cliente == null || cliente.getPessoa() == null) {
            throw new BusinessException("Cliente não foi selecionado");
        }
        if (cliente.getPessoa().getEndereco() == null || cliente.getPessoa().getEndereco().size() == 0) {
            throw new BusinessException("Endereço do Cliente não foi cadastrado entre no cadastro de cliente cadastre um endereço.");
        }
        if (cliente.getPessoa().getTelefones() == null || cliente.getPessoa().getTelefones().size() == 0) {
            throw new BusinessException("Telefone do Cliente não foi cadastrado entre no cadastro de cliente cadastre um telefone.");
        }

        Contrato contrato = this.contratoService.gerarContratacao(cliente, plano, meioPagamentoEnum, numParcelas);
        if (contrato.getMensalidades() == null || contrato.getMensalidades().size() == 0) {
            throw new BusinessException("Contrato não gerou nenhuma mensalidade.");
        }

        for (Mensalidade mensalidade : contrato.getMensalidades()) {

            mensalidade.setDtVencimento(LocalDate.now().plusDays(28));
            this.pagamentoRecorrenciaService.gerarRecorrencia(mensalidade, contrato, cliente, cartaoCreditoDto);
        }
    }

@Transactional
    public Contrato gerarContratoNaoSalvar(final Cliente cliente, final Plano plano, final MeioPagamentoEnum meioPagamentoEnum, final int numParcelas) throws BusinessException {
        Contrato contrato = this.contratoService.gerarContratacaoNaoSalvar(cliente, plano, meioPagamentoEnum, numParcelas);
        return contrato;
    }
}
