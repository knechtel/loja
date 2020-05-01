package br.com.acertsis.loja.bean.admin;

import br.com.acertsis.loja.entity.Cliente;
import br.com.acertsis.loja.entity.Contrato;
import br.com.acertsis.loja.entity.Mensalidade;
import br.com.acertsis.loja.service.ClienteService;
import br.com.acertsis.loja.service.MensalidadeService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Component
@Named
@ViewScoped
public class ClienteMensalidadeMb {

    @Inject
    private MensalidadeService mensalidadeService;
    @Getter
    @Setter
    private List<Cliente> listCliente;
    @Getter
    @Setter
    private List<Mensalidade> listMensalidade;
    @Inject
    private ClienteService clienteService;
    @Getter
    @Setter
    private List<Mensalidade> listMensalidadeHoje;

    @Getter
    @Setter
    private List<Cliente> listClienteHoje;

    @PostConstruct
    void init() {
        List<Mensalidade> listMensalidade = mensalidadeService.getMensalidadesVencida();
        listCliente = new ArrayList<Cliente>();
        for (Mensalidade m : listMensalidade) {
            Contrato c = m.getContrato();
            if (!listCliente.contains(c.getCliente())) {
                Cliente c1 = clienteService.getClienteResponsavel(c.getCliente().getIdCliente());
                listCliente.add(c.getCliente());
            }
        }
        List<Mensalidade> listMensalidadeVencidaHoje = mensalidadeService.getMensalidadesVencidaHoje();
        listClienteHoje = new ArrayList<Cliente>();
        for (Mensalidade m : listMensalidade) {
            Contrato c = m.getContrato();
            if (!listClienteHoje.contains(c.getCliente())) {
                Cliente c1 = clienteService.getClienteResponsavel(c.getCliente().getIdCliente());
                listClienteHoje.add(c.getCliente());
            }
        }

    }

    public void doTitulos(Cliente c) {
        listMensalidade = mensalidadeService.getMensalidadesVencida();
    }


}
