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

        // Primero buscamos al empleado (contraseña sin encriptar)
        Empleado empleado = empleadoRepository.findByEmail(email).orElse(null);

        if (empleado != null) {
            return new User(
                    empleado.getEmail(),
                    empleado.getPassword(), // sin bcrypt
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
        }

        // Luego buscamos al cliente (contraseña encriptada)
        Cliente cliente = clienteRepository.findByEmail(email);

        if (cliente != null) {
            return new User(
                    cliente.getEmail(),
                    cliente.getPassword(), // encriptada
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_CLIENTE"))
            );
        }

        throw new UsernameNotFoundException("No existe usuario con email: " + email);
    }
}

