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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // 🔹 Buscar empleado (contraseña sin encriptar)
        Empleado empleado = empleadoRepository.findByEmail(email).orElse(null);
        if (empleado != null) {
            return User.builder()
                    .username(empleado.getEmail())
                    .password("{noop}" + empleado.getPassword()) // TEXTO PLANO
                    .authorities(Collections.singletonList(
                            new SimpleGrantedAuthority(empleado.getRol()) // YA VIENE COMO ROLE_ADMIN
                    ))
                    .build();
        }

        // 🔹 Buscar cliente (contraseña encriptada)
        Cliente cliente = clienteRepository.findByEmail(email);
        if (cliente != null) {
            return User.builder()
                    .username(cliente.getEmail())
                    .password(cliente.getPassword()) // BCrypt
                    .authorities(Collections.singletonList(
                            new SimpleGrantedAuthority(cliente.getRol()) // YA VIENE COMO ROLE_CLIENTE
                    ))
                    .build();
        }

        throw new UsernameNotFoundException("Usuario no encontrado: " + email);
    }
}
