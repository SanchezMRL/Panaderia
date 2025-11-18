package com.panaderia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.panaderia.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(CustomUserDetailsService userDetailsService,
                                                            BCryptPasswordEncoder encoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService); // OK, ya no causa ciclo
        provider.setPasswordEncoder(encoder);
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

                .requestMatchers("/index", "/registrar", "/consultar",
                                 "/opiniones", "/inventario", "/reportes",
                                 "/entregas", "/agregar", "/observar")
                    .hasRole("ADMIN")

                .requestMatchers("/clienteMenu", "/cliente/pedidos",
                                 "/cliente/opinion/nueva",
                                 "/cliente/entregas", "/actualizarCliente")
                    .hasRole("CLIENTE")

                .anyRequest().authenticated()
            )

            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
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
