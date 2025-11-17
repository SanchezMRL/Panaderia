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

        http.csrf(csrf -> csrf.disable())
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/registroCliente",
                                "/css/**", "/js/**", "/images/**").permitAll()

                        .requestMatchers("/index", "/registrar", "/consultar",
                                "/opiniones", "/inventario", "/reportes",
                                "/entregas", "/agregar", "/observar")
                        .hasRole("ADMIN")

                        .requestMatchers("/clienteMenu", "/cliente/pedidos",
                                "/cliente/opinion/nueva", "/cliente/entregas",
                                "/actualizarCliente")
                        .hasRole("CLIENTE")

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler((req, res, auth) -> {

                            String rol = auth.getAuthorities()
                                    .iterator().next().getAuthority();

                            if (rol.equals("ROLE_ADMIN")) {
                                res.sendRedirect("/index");
                            } else if (rol.equals("ROLE_CLIENTE")) {
                                res.sendRedirect("/clienteMenu");
                            } else {
                                res.sendRedirect("/login?error=rol");
                            }
                        })
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
