package br.com.acertsis.loja.dao;

import br.com.acertsis.loja.entity.Categoria;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaDAO extends CrudRepository<Categoria, Long> {
}
