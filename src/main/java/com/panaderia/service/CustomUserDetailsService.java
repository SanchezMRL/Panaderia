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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 🟡 BUSCAR EMPLEADO
        Empleado empleado = empleadoRepository.findByEmail(username).orElse(null);

        if (empleado != null) {
            return User.withUsername(empleado.getEmail())
                    .password(empleado.getPassword()) // SIN ENCRIPTAR
                    .roles("ADMIN")
                    .build();
        }

        // 🟢 BUSCAR CLIENTE
        Cliente cliente = clienteRepository.findByEmail(username).orElse(null);

        if (cliente != null) {
            return User.withUsername(cliente.getEmail())
                    .password(cliente.getPassword()) // ESTE SÍ ES BCrypt
                    .roles("CLIENTE")
                    .build();
        }

        throw new UsernameNotFoundException("Usuario no encontrado");
    }
}

