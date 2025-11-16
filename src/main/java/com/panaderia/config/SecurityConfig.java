package com.panaderia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth
                // Rutas públicas
                .requestMatchers("/", "/login", "/registroCliente",
                                 "/css/**", "/js/**", "/images/**").permitAll()

                // Rutas de ADMIN
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

                // Rutas de CLIENTE
                .requestMatchers("/clienteMenu").hasRole("CLIENTE")

                .anyRequest().authenticated()
            )

            .formLogin(form -> form
                .loginPage("/login")
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
