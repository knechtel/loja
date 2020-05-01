package br.com.acertsis.loja.dao;

import br.com.acertsis.loja.entity.Plano;
import br.com.acertsis.loja.entity.Produto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanoDAO extends CrudRepository<Plano, Long> {

    @Query("select e from Plano e where e.habilitado = :habilitado")
    public Optional<List<Plano>> listAllHabilitado(boolean habilitado);

    @Query("select e from Plano e")
    public Optional<List<Plano>> listAll();

    @Query("select e from Plano e left join fetch e.modulos left join fetch e.listProduto where e.idPlano = :id")
    public Optional<Plano> findByIdFetchModulosProdutos(Long id);


    @Query("select distinct(p) from Plano p, Produto prod where prod member of p.listProduto and p.habilitado = true and prod.habilitado =true and prod = :produto order by p.valorPlano")
    public Optional<List<Plano>> findPlanosHabilitadosByProduto(@Param("produto") Produto produto);

    @Query("select distinct(e) from Plano e left join fetch e.modulos left join fetch e.listProduto")
    public Optional<List<Plano>> findAllFetchProdutosEModulos();

}
