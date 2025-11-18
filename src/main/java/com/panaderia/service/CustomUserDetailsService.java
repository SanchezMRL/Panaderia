package com.panaderia.service;

import com.panaderia.entity.Cliente;
import com.panaderia.entity.Empleado;
import com.panaderia.repository.ClienteRepository;
import com.panaderia.repository.EmpleadoRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final EmpleadoRepository empleadoRepository;
    private final ClienteRepository clienteRepository;

    public CustomUserDetailsService(EmpleadoRepository empleadoRepository,
                                    ClienteRepository clienteRepository) {
        this.empleadoRepository = empleadoRepository;
        this.clienteRepository = clienteRepository;
    }

    // Normaliza cualquier rol a formato "ROLE_X"
    private String normalizeRole(String rol) {
        if (rol == null) return "ROLE_CLIENTE";
        return rol.startsWith("ROLE_") ? rol : "ROLE_" + rol;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // Buscar empleado (contraseña sin encriptar)
        Empleado empleado = empleadoRepository.findByEmail(email).orElse(null);
        if (empleado != null) {
            return User.builder()
                    .username(empleado.getEmail())
                    .password("{noop}" + empleado.getPassword())  // TEXTO PLANO
                    .authorities(Collections.singletonList(
                            new SimpleGrantedAuthority(normalizeRole(empleado.getRol()))
                    ))
                    .build();
        }

        // Buscar cliente (contraseña BCrypt)
        Cliente cliente = clienteRepository.findByEmail(email);
        if (cliente != null) {
            return User.builder()
                    .username(cliente.getEmail())
                    .password(cliente.getPassword())  // BCRYPT
                    .authorities(Collections.singletonList(
                            new SimpleGrantedAuthority(normalizeRole(cliente.getRol()))
                    ))
                    .build();
        }

        throw new UsernameNotFoundException("Usuario no encontrado: " + email);
    }
}
