package com.panaderia.service;

import com.panaderia.entity.Cliente;
import com.panaderia.entity.Empleado;
import com.panaderia.repository.ClienteRepository;
import com.panaderia.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 🟢 Función para normalizar el rol
    private String normalizeRole(String rol) {
        if (rol == null) return "ROLE_CLIENTE"; // fallback
        return rol.startsWith("ROLE_") ? rol : "ROLE_" + rol;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // 🔹 Buscar empleado (ADMIN)
        Empleado empleado = empleadoRepository.findByEmail(email).orElse(null);
        if (empleado != null) {
            return User.builder()
                    .username(empleado.getEmail())
                    .password("{noop}" + empleado.getPassword()) // texto plano
                    .authorities(Collections.singletonList(
                            new SimpleGrantedAuthority("ROLE_" + empleado.getRol())
                    ))
                    .build();
        }

        // 🔹 Buscar cliente
        Cliente cliente = clienteRepository.findByEmail(email);
        if (cliente != null) {

            return User.builder()
                    .username(cliente.getEmail())
                    .password(cliente.getPassword()) // BCrypt
                    .authorities(Collections.singletonList(
                            new SimpleGrantedAuthority(normalizeRole(cliente.getRol()))
                    ))
                    .build();
        }

        throw new UsernameNotFoundException("Usuario no encontrado con email: " + email);
    }
}
