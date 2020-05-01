package br.com.acertsis.loja.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Componente para criptografar a senha.
 */
@Component
public class MyBCryptPasswordEncoder extends BCryptPasswordEncoder {

}
