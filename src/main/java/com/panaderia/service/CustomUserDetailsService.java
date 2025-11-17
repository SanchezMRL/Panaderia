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

        // EMPLEADO (contraseña sin encriptar)
        Empleado empleado = empleadoRepository.findByEmail(email).orElse(null);

        if (empleado != null) {
            return User.builder()
                    .username(empleado.getEmail())
                    .password("{noop}" + empleado.getPassword())  // <-- ESTO SOLUCIONA TU PROBLEMA
                    .authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))
                    .build();
        }

        // CLIENTE (contraseña encriptada)
        Cliente cliente = clienteRepository.findByEmail(email);

        if (cliente != null) {
            return User.builder()
                    .username(cliente.getEmail())
                    .password(cliente.getPassword()) // bcrypt
                    .authorities(new SimpleGrantedAuthority("ROLE_CLIENTE"))
                    .build();
        }

        throw new UsernameNotFoundException("No existe usuario con email: " + email);
    }
}
