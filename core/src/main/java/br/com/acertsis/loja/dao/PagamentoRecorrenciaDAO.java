package br.com.acertsis.loja.dao;

import br.com.acertsis.loja.entity.PagamentoRecorrencia;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional()
public interface PagamentoRecorrenciaDAO extends CrudRepository<PagamentoRecorrencia, Long> {
}
