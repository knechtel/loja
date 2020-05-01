/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.service;

import java.util.Optional;

public interface CrudService<T, ID> {

    public void save(T entity);

    public void deleteById(ID id);

    public Optional<T> findById(ID id);
}
