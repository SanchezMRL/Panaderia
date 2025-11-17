package com.panaderia.service;

import com.panaderia.entity.Cliente;
import com.panaderia.entity.Empleado;
import com.panaderia.repository.ClienteRepository;
import com.panaderia.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // 🔹 Buscar empleado
        Empleado empleado = empleadoRepository.findByEmail(email).orElse(null);

        if (empleado != null) {
            return User.builder()
                    .username(empleado.getEmail())
                    .password(empleado.getPassword()) // ❗ NO ENCRIPTADO (ESTÁ BIEN)
                    .roles("ADMIN")
                    .build();
        }

        // 🔹 Buscar cliente
        Cliente cliente = clienteRepository.findByEmail(email).orElse(null);

        if (cliente != null) {
            return User.builder()
                    .username(cliente.getEmail())
                    .password(cliente.getPassword()) // 🔐 ENCRIPTADO (BCrypt)
                    .roles("CLIENTE")
                    .build();
        }

        throw new UsernameNotFoundException("No se encontró usuario con email: " + email);
    }
}
