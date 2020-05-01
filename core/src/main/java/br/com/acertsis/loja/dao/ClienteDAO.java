package br.com.acertsis.loja.dao;

import br.com.acertsis.loja.entity.Cliente;
import br.com.acertsis.loja.entity.Parceiro;
import br.com.acertsis.loja.entity.Pessoa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional()
public interface ClienteDAO extends CrudRepository<Cliente, Long> {

    @Query("select c from Cliente c left join fetch c.pessoa p where p.cpf = :cpf")
    public Optional<Cliente> findByCpf(@Param("cpf") String cpf);
// Comentado em detrimento da versao abaixo com @Param
//    @Query("select c from Cliente c left join fetch c.pessoa p where p.cnpj = :cnpj")
//    public Optional<Cliente> findByCnpj(String cnpj);

    @Query("select c from Cliente c left join fetch c.pessoa p where p = :p")
    public Optional<Cliente> findByPessoa(Pessoa p);

    public Cliente findByIdCliente(long l);
    @Query("select c from Cliente c left join fetch c.pessoa p where p.cnpj =:cnpj")
    public Optional<Cliente> findByCnpj(@Param("cnpj") String cnpj);

    @Query("select c from Cliente c left join fetch c.pessoa p where c.idCliente = :idCliente")
    public Optional<Cliente> findClienteFetchPessoa(@Param("idCliente") Long idCliente);

    public Optional<List<Cliente>> findByParceiro(Parceiro parceiro);

    @Query("select cli from PessoaFisica p, Cliente cli where cli.pessoa = p and lower(p.nome) like :nome  and cli.parceiro = :parceiro")
    public Optional<List<Cliente>> findByNomeIlike(@Param("nome") String nome, @Param("parceiro") Parceiro parceiro);

    @Query("select c from Cliente c left join  fetch c.responsavel where c.id =:id")
    public Cliente findResponsavel(@Param("id")Long id);

    @Query("select cli from PessoaJuridica p, Cliente cli where cli.pessoa = p and lower(p.nomeFantasia) like :nome and cli.parceiro = :parceiro")
    public Optional<List<Cliente>> findByNomeFantasiaIlike(@Param("nome") String nome, @Param("parceiro") Parceiro parceiro);


}
