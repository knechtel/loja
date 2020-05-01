package br.com.acertsis.loja.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

@Entity
public class DadosRecorrencia implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private BrandCardEnum bandeira;
    @OneToOne
    @JoinColumn(name = "id_Contrato", foreignKey = @ForeignKey(name = "fk_contratodadosrecorrencia"))
    private Contrato contrato;
    @Column(length = 36)
    private String token;
    //Campo codigo retorno operacao.
    @Column(name = "reason_code")
    private Integer reasonCode;
    //Campo mensagem retorno operação.
    @Column(name = "reason_message")
    private String reasonMessage;
    //Data do inicio da recorrência
    @Column(name = "dt_inicio")
    private LocalDate dtInicio;
    //Data do fim da recorrência.
    @Column(name = "dt_fim")
    private LocalDate dtFim;
    //Campo Identificador da recorrência.
    @Column(name = "recurrent_payment_id")
    private String recurrentPaymentId;
    @Column(name = "status_transacao")
    //@Enumerated(EnumType.STRING)
    //private StatusPagamentoEnum statusTransaction;
    private Integer status;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "dadosRecorrencia")
    public List<PagamentoRecorrencia> pagamentos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BrandCardEnum getBandeira() {
        return bandeira;
    }

    public void setBandeira(BrandCardEnum bandeira) {
        this.bandeira = bandeira;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(Integer reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getReasonMessage() {
        return reasonMessage;
    }

    public void setReasonMessage(String reasonMessage) {
        this.reasonMessage = reasonMessage;
    }

    public LocalDate getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(LocalDate dtInicio) {
        this.dtInicio = dtInicio;
    }

    public LocalDate getDtFim() {
        return dtFim;
    }

    public void setDtFim(LocalDate dtFim) {
        this.dtFim = dtFim;
    }

    public String getRecurrentPaymentId() {
        return recurrentPaymentId;
    }

    public void setRecurrentPaymentId(String recurrentPaymentId) {
        this.recurrentPaymentId = recurrentPaymentId;
    }

    public List<PagamentoRecorrencia> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<PagamentoRecorrencia> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DadosRecorrencia that = (DadosRecorrencia) o;
        return Objects.equals(id, that.id) &&
                bandeira == that.bandeira &&
                Objects.equals(contrato, that.contrato) &&
                Objects.equals(token, that.token) &&
                Objects.equals(reasonCode, that.reasonCode) &&
                Objects.equals(reasonMessage, that.reasonMessage) &&
                Objects.equals(dtInicio, that.dtInicio) &&
                Objects.equals(dtFim, that.dtFim) &&
                Objects.equals(recurrentPaymentId, that.recurrentPaymentId) &&
                Objects.equals(pagamentos, that.pagamentos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bandeira, contrato, token, reasonCode, reasonMessage, dtInicio, dtFim, recurrentPaymentId, pagamentos);
    }

    @Override
    public String toString() {
        return "DadosRecorrencia{" +
                "id=" + id +
                ", bandeira=" + bandeira +
                ", contrato=" + contrato +
                ", token='" + token + '\'' +
                ", reasonCode=" + reasonCode +
                ", reasonMessage='" + reasonMessage + '\'' +
                ", dtInicio=" + dtInicio +
                ", dtFim=" + dtFim +
                ", recurrentPaymentId='" + recurrentPaymentId + '\'' +
                ", pagamentos=" + pagamentos +
                '}';
    }
}
