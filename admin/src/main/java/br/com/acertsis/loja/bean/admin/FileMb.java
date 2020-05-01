package br.com.acertsis.loja.bean.admin;

import br.com.acertsis.loja.entity.PagamentoBoleto;
import br.com.acertsis.loja.service.PagamentoBoletoService;
import br.com.acertsis.loja.service.PagamentoService;
import br.com.acertsis.loja.util.Util;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.stereotype.Component;

@Component
@Named
@ViewScoped
public class FileMb implements Serializable {

    @Inject
    private PagamentoBoletoService pagamentoBoletoService;

    @Inject
    private PagamentoService pagamentoService;

    private UploadedFile file;

    @Getter
    @Setter
    private List<PagamentoBoleto> listPagamentoBoleto;

    @PostConstruct
    private void init() {
        listPagamentoBoleto = new ArrayList<PagamentoBoleto>();
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void upload() {
        if (file != null) {
            FacesMessage message = new FacesMessage("Sucesso", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void handleFileUpload(FileUploadEvent event) throws IOException {
        FacesMessage msg = new FacesMessage("Sucesso", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        byte[] arquivo = event.getFile().getContents();


        FileOutputStream fos = new FileOutputStream(new File(System.getProperty("user.dir") + "/" + "/data/swap.ret"));
        fos.write(arquivo);
        fos.close();
        try {
            BufferedReader bf = new BufferedReader(new FileReader(new File(System.getProperty("user.dir") + "" + "/data/swap.ret")));
            String line = null;
            while ((line = bf.readLine()) != null) {

                String segmento = line.substring(0, 14);

                if (!line.substring(7, 8).equals("0") && !line.substring(7, 8).equals("1")) {

                    if (segmento.contains("T") || segmento.contains("U")) {
                        String idCodigoMovimento = line.substring(15, 17);
                        String segmentoAux = segmento.substring(13, 14);
                        String nossoNumero = line.substring(37, 57);
                        PagamentoBoleto p = pagamentoBoletoService.findByReftran(nossoNumero.trim());
                        String valorTitulo = line.substring(81, 96);
                        String dataVencimento = Util.formataData(line.substring(73, 81));
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");


                        if (p != null) {
                            //    LocalDate dt = LocalDate.parse(dataVencimento,formatter);
                            //   Double vlr = Double.parseDouble(valorTitulo.trim().replace("0", ""));
                            p.setCodigoMovimento("12");
                            // p.setValorPagamento(vlr / 100);
                            //p.setDataVencimento(dt);
                            p = pagamentoBoletoService.save(p);
                            listPagamentoBoleto.add(p);
                        } else {

                            if (nossoNumero.replace("0", "").equals("")) {
                            } else {
                                PagamentoBoleto pagamento = new PagamentoBoleto();
                                LocalDate dt = LocalDate.parse(dataVencimento, formatter);
                                Double vlr = Double.parseDouble(valorTitulo.trim().replace("0", ""));
                                pagamento.setRefTran(nossoNumero);
                                pagamento.setCodigoMovimento("12");
                                pagamento.setValorPagamento(vlr / 100);
                                pagamento.setDataVencimento(dt);
                                pagamento = pagamentoBoletoService.save(pagamento);
                                listPagamentoBoleto.add(pagamento);
                            }
                        }
                    }
                }
            }
            bf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
