package com.panaderia.config;

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

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authenticationProvider(authenticationProvider())

            .authorizeHttpRequests(auth -> auth

                // 🔓 RUTAS PÚBLICAS (solo estas)
                .requestMatchers("/", "/login", "/registroCliente",
                 "/css/**", "/js/**", "/images/**").permitAll()


                // 🔐 ADMIN
                .requestMatchers(
                        "/index",
                        "/registrar",
                        "/consultar",
                        "/opiniones",
                        "/inventario",
                        "/reportes",
                        "/entregas",
                        "/agregar",
                        "/observar"
                ).hasRole("ADMIN")

                // 🔐 CLIENTE
                .requestMatchers(
                        "/clienteMenu",
                        "/cliente/pedidos",
                        "/cliente/opinion/nueva",
                        "/cliente/entregas",
                        "/actualizarCliente"
                ).hasRole("CLIENTE")

                // 🔐 TODO LO DEMÁS -> REQUIERE LOGIN
                .anyRequest().authenticated()
            )

            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()

                // 🔥 REDIRECCIÓN SEGÚN ROL
                .successHandler((request, response, authentication) -> {
                    String role = authentication.getAuthorities()
                                                .iterator()
                                                .next()
                                                .getAuthority();

                    if (role.equals("ROLE_ADMIN")) {
                        response.sendRedirect("/index");
                    } else {
                        response.sendRedirect("/clienteMenu");
                    }
                })
            )

            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }
}

