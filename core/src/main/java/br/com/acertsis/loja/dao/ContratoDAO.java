package br.com.acertsis.loja.dao;

import br.com.acertsis.loja.entity.Cliente;
import br.com.acertsis.loja.entity.Contrato;
import br.com.acertsis.loja.entity.StatusContratoEnum;
import java.util.List;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContratoDAO extends CrudRepository<Contrato, Long> {

    @Query(value = "SELECT con.* FROM acnf_loja.contrato con " +
            " INNER JOIN acnf_loja.cliente cli ON con.id_cliente = cli.id_cliente " +
            " INNER JOIN acnf_loja.parceiro par ON par.id_parceiro = cli.parceiro_id" +
            " WHERE  MONTH(CURRENT_DATE()) = MONTH(?) and YEAR(CURRENT_DATE()) = YEAR(dt_inicio)" +
            " AND par.id_parceiro = ?", nativeQuery = true)
    public Optional<List<Contrato>> listSalesByMonthOfUser(String dataMes, Long idParceiro);

    @Query(value = "SELECT con.* FROM acnf_loja.contrato con " +
            " INNER JOIN acnf_loja.cliente cli ON con.id_cliente = cli.id_cliente " +
            " INNER JOIN acnf_loja.parceiro par ON par.id_parceiro = cli.parceiro_id" +
            " WHERE YEAR(CURRENT_DATE()) = YEAR(dt_inicio)" +
            " AND par.id_parceiro = ?", nativeQuery = true)
    public Optional<List<Contrato>> listSalesByYearOfUser(String dataMes, Long idParceiro);

    @Query("select c from Contrato c where YEAR(:date) = YEAR(dt_fim) and MONTH(:date) = MONTH(dt_fim)")
    public Optional<List<Contrato> >listByYearAndMonthByDtFim(String date);

    @Query(value = "SELECT con.* FROM acnf_loja.contrato con " +
            "INNER JOIN acnf_loja.cliente cli ON con.id_cliente = cli.id_cliente " +
            "INNER JOIN acnf_loja.parceiro par ON par.id_parceiro = cli.parceiro_id" +
            " WHERE  MONTH(CURRENT_DATE()) = MONTH(dt_inicio) and YEAR(CURRENT_DATE()) = YEAR(dt_inicio)", nativeQuery = true)
    public Optional<List<Contrato>> listCurrentMonth();

    @Query(value = "SELECT con.* FROM acnf_loja.contrato con " +
            "INNER JOIN acnf_loja.cliente cli ON con.id_cliente = cli.id_cliente " +
            "INNER JOIN acnf_loja.parceiro par ON par.id_parceiro = cli.parceiro_id" +
            " WHERE YEAR(CURRENT_DATE()) = YEAR(dt_inicio)", nativeQuery = true)

    public Optional<List<Contrato>> listCurrentYear();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from Contrato c inner join c.cliente cl inner join c.mensalidades m on m.parcela = 1" +
            " where c.status = br.com.acertsis.loja.entity.StatusContratoEnum.PENDENTEATV " +
            " and m.status = br.com.acertsis.loja.entity.StatusPagamentoEnum.QUITADO " +
            " and cl.sku is null and cl.dominio is null " +
            " ORDER BY cl.parceiro")
    public List<Contrato> listContratoAccountCreation();

    public List<Contrato> findByCliente(Cliente cliente);

    public List<Contrato> findByStatus(StatusContratoEnum statusContratoEnum);

}
