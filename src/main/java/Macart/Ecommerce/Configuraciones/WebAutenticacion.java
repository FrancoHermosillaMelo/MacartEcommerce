package Macart.Ecommerce.Configuraciones;

import Macart.Ecommerce.Modelos.Cliente;
import Macart.Ecommerce.Repositorio.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebAutenticacion extends GlobalAuthenticationConfigurerAdapter {
    @Autowired
    ClienteRepositorio clienteRepositorio;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(inputName-> {

            Cliente cliente = clienteRepositorio.findByCorreo(inputName);

            if (cliente != null) {

                if(cliente.getCorreo().contains("carlosandresgoo@gmail.com")){
                    return new User(cliente.getCorreo(), cliente.getContraseña(),

                            AuthorityUtils.createAuthorityList("ADMIN"));
                }

                return new User(cliente.getCorreo(), cliente.getContraseña(),


                        AuthorityUtils.createAuthorityList("CLIENTE"));

            } else {

                throw new UsernameNotFoundException("Usuario desconocido: " + inputName);

            }

        });


    }
    @Bean

    public PasswordEncoder passwordEncoder() {

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();

    }

}
