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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 🔹 Buscar empleado
        Empleado empleado = empleadoRepository.findByEmail(username).orElse(null);
        if (empleado != null) {
            return new User(
                    empleado.getEmail(),
                    empleado.getPassword(), // SIN ENCRIPTAR si deseas
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
        }

        // 🔹 Buscar cliente
        Cliente cliente = clienteRepository.findByEmail(username); // NO Optional
        if (cliente != null) {
            return new User(
                    cliente.getEmail(),
                    cliente.getPassword(), // ENCRIPTADO con BCrypt
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_CLIENTE"))
            );
        }

        throw new UsernameNotFoundException("Usuario no encontrado con email: " + username);
    }
}

