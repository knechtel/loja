/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.service;

import br.com.acertsis.loja.dao.BancoDAO;
import br.com.acertsis.loja.entity.Banco;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service
public class BancoService implements CrudService<Banco, String> {

    @Inject
    private BancoDAO bancoDao;

    @Override
    public void save(Banco entity) {
        this.bancoDao.save(entity);
    }

    @Override
    public void deleteById(String id) {
        this.bancoDao.deleteById(id);
    }

    @Override
    public Optional<Banco> findById(String id) {
        return bancoDao.findById(id);
    }

    public Optional<List<Banco>> findFirst10ByNomeOrCodigo(String nome) {
        List<Banco> bancos = null;
        if (nome != null) {
            bancos = bancoDao.findFirst10ByNomeLikeIgnoreCaseOrCodigoLike("%" + nome.toLowerCase() + "%", nome);
        }
        return Optional.ofNullable(bancos);
    }

}
