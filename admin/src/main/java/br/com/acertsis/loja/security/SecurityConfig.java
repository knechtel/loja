package br.com.acertsis.loja.security;

import br.com.acertsis.loja.service.SpringDataUserDetailsService;
import javax.inject.Inject;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by aLeXcBa1990 on 24/11/2018.
 *
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Inject
    private SpringDataUserDetailsService springDataUserDetailsService;
    @Inject
    private MyBCryptPasswordEncoder myBCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(springDataUserDetailsService).passwordEncoder(myBCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // form login

        http.csrf().disable().authorizeRequests().antMatchers("/index.html", "/login.xhtml", "/logout").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/index.xhtml", "/admin/**").hasAnyRole("ADMIN", "PARCEIRO")
                .and()
                .formLogin().loginPage("/login.xhtml")
                .defaultSuccessUrl("/index.xhtml")
                .failureUrl("/login.xhtml?error")
                .and()
                .logout().logoutUrl("/logout")
                .and()
                .httpBasic();
        // allow to use ressource links like pdf
        //http.headers().frameOptions().sameOrigin();
    }
}
