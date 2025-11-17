package com.panaderia.service;

import com.panaderia.entity.Cliente;
import com.panaderia.entity.Empleado;
import com.panaderia.repository.ClienteRepository;
import com.panaderia.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // 🔹 Buscar empleado (ADMIN, password en texto plano)
        Empleado empleado = empleadoRepository.findByEmail(email).orElse(null);
        if (empleado != null) {
            return User.builder()
                    .username(empleado.getEmail())
                    .password("{noop}" + empleado.getPassword()) // texto plano permitido
                    .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")))
                    .build();
        }

        // 🔹 Buscar cliente (CLIENTE, password en BCrypt)
        Cliente cliente = clienteRepository.findByEmail(email);
        if (cliente != null) {
            return User.builder()
                    .username(cliente.getEmail())
                    .password(cliente.getPassword()) // BCrypt
                    .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_CLIENTE")))
                    .build();
        }

        throw new UsernameNotFoundException("Usuario no encontrado con email: " + email);
    }
}
