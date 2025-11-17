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
    private ClienteRepository clienteRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // 1. Buscar EMPLEADO
        Empleado empleado = empleadoRepository.findByEmail(email);
        if (empleado != null) {
            // CONTRASEÑA SIN ENCRIPTAR (empleados)
            return User.withUsername(empleado.getEmail())
                    .password("{noop}" + empleado.getPassword())   // <= AQUI EL TRUCO
                    .roles("ADMIN")
                    .build();
        }

        // 2. Buscar CLIENTE
        Cliente cliente = clienteRepository.findByEmail(email);
        if (cliente != null) {
            // CONTRASEÑA ENCRIPTADA (clientes)
            return User.withUsername(cliente.getEmail())
                    .password(cliente.getPassword()) // BCrypt
                    .roles("CLIENTE")
                    .build();
        }

        throw new UsernameNotFoundException("Usuario no encontrado: " + email);
    }
}
