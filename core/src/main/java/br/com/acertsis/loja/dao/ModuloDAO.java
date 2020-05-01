package br.com.acertsis.loja.dao;

import br.com.acertsis.loja.entity.Modulo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuloDAO extends CrudRepository<Modulo, Long> {

    @Query("select m from Modulo m")
    public Optional<List<Modulo>> listAll();
}
