package br.com.acertsis.loja.dao;


import br.com.acertsis.loja.entity.PagamentoBoleto;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PagamentoBoletoDAO extends CrudRepository<PagamentoBoleto, Long> {
    @Query("Select p from  PagamentoBoleto p where p.codigoMovimento = :codigoMovimento")
    public List<PagamentoBoleto> findCodigoMovimento(@Param("codigoMovimento") String codigoMovimento);
    @Query("Select p from  PagamentoBoleto p where p.refTran like :refTran")
    public PagamentoBoleto findByReftran(@Param("refTran") String refTran);

    @Query(value = "SELECT max(reftran) FROM pagamento_boleto", nativeQuery = true)
    public String getUltimoRefTran();

}
