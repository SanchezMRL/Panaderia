package com.panaderia.config;

import com.panaderia.config.CustomSuccessHandler;
import com.panaderia.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CustomSuccessHandler customSuccessHandler;

    // BCrypt para los clientes (empleados usan NOOP desde CustomUserDetailsService)
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);

        // NECESARIO para validar contraseñas BCRYPT de clientes
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           DaoAuthenticationProvider authProvider) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authenticationProvider(authProvider)

            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/registroCliente",
                                 "/css/**", "/js/**", "/images/**").permitAll()

                // Rutas solo para ADMIN/empleado
                .requestMatchers("/index", "/registrar", "/consultar",
                                 "/opiniones", "/inventario", "/reportes",
                                 "/entregas", "/agregar", "/observar")
                    .hasRole("ADMIN")

                // Rutas solo para CLIENTE
.requestMatchers("/clienteMenu",
                 "/cliente/pedidos",
                 "/cliente/opinion/nueva",
                 "/cliente/entregas",
                 "/actualizarCliente",
                 "/cliente/**")      // 🔥 NECESARIO PARA EVITAR REBOTES
    .hasRole("CLIENTE")


                .anyRequest().authenticated()
            )

            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler(customSuccessHandler) // 🔥 REDIRECCIÓN AUTOMÁTICA
                .failureUrl("/login?error=true")
                .permitAll()
            )

            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }
}
