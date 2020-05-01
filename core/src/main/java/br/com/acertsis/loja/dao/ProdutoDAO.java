package br.com.acertsis.loja.dao;

import br.com.acertsis.loja.entity.Produto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoDAO extends CrudRepository<Produto, Long> {

    @Query("select m from Produto m")
    public Optional<List<Produto>> listAll();

    @Query("select m from Produto m where m.habilitado=true")
    public List<Produto> listAllHabilitado();
}
