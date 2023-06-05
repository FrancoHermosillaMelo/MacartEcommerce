package Macart.Ecommerce.Configuraciones;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@EnableWebSecurity
@Configuration
public class WebAutorizacion {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors().and().authorizeRequests()

                .antMatchers(HttpMethod.POST,"/api/login", "/api/logout","/api/clientes").permitAll()
                // HTML JS CSS PERMIT ALL
                .antMatchers("/api/productoTienda",
                        "/api/clientes/actual/rol",
                        "/html/catalogo.html",
                        "/html/preguntasFrecuentes.html",
                        "/html/sobreNosotros.html",
                        "/index.html",
                        "/css/**",
                        "/img/**",
                        "/js/index.js",
                        "/js/catalogo.js",
                        "/js/eze.js",
                        "/js/**",
                        "/api/productoTienda/{id}").permitAll()

                // CLIENTE ADMIN
                .antMatchers(HttpMethod.POST,
                        "/api/direcciones",
                        "/api/pedidos",
                        "/api/productoTienda",
                        "/api/comprobantes/pdf",
                        "/api/clientes/{id}",
                        "/api/clientes/autenticar",
                        "/api/pedidos/carrito").hasAnyAuthority("CLIENTE", "ADMIN")

                .antMatchers("/api/clientes/actual",
                        "/api/clientes/pedidos",
                        "/api/pedidoProducto",
                        "/api/pedidoProducto/{id}",
                        "/api/comprobantes/pdf",
                        "/api/comprobantes/fechas",
                        "/api/clientes/comprobantes/fechas",
                        "/api/clientes/comprobantes",
                        "/api/clientes/autenticar",
                        "/api/clientes/pedidosActivados",
                        "/api/pedidos/{id}",
                        "/api/clientes/id",
                        "/api/clientes/{id}/direccion",
                        "/html/perfilCliente.html",
                        "/js/perfilCliente.js").hasAnyAuthority("CLIENTE", "ADMIN")

                .antMatchers(HttpMethod.PUT,
                        "/api/direcciones",
                        "/api/productoTienda",
                        "/api/pedidos",
                        "/api/clientes/{id}").hasAnyAuthority("CLIENTE", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/direcciones/{id}").hasAnyAuthority("CLIENTE", "ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/numero").hasAnyAuthority("CLIENTE", "ADMIN")

                // ENDPOINTS ADMIN
                .antMatchers("/js/manager.js",
                        "/manager.html",
                        "/h2-console/**",
                        "/api/clientes",
                        "/api/comprobantes",
                        "/api/clientes/direcciones",
                        "/api/pedidos",
                        "/api/pedidoProducto").hasAuthority("ADMIN")




                .antMatchers(HttpMethod.POST, "/api/productoTienda").hasAuthority("ADMIN")

                .antMatchers(HttpMethod.PUT, "/api/productoTienda").hasAuthority("ADMIN")

                .antMatchers(HttpMethod.PATCH, "/api/productoTienda/{id}").hasAuthority("ADMIN");









        http.formLogin()
                .usernameParameter("correo")
                .passwordParameter("contraseña")
                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout").deleteCookies("JSESSIONID");

        http.csrf().disable();

        //disabling frameOptions so h2-console can be accessed

        http.headers().frameOptions().disable();

        // if user is not authenticated, just send an authentication failure response

        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication

        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response

        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response

        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

        return http.build();
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }

}





