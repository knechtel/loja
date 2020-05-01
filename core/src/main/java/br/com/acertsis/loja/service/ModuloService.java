/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.acertsis.loja.dao.ModuloDAO;
import br.com.acertsis.loja.entity.Modulo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModuloService  {
    @Autowired
    private ModuloDAO moduloDAO;

}
