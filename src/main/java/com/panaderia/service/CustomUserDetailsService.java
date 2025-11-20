package com.panaderia.service;

import com.panaderia.entity.Cliente;
import com.panaderia.entity.Empleado;
import com.panaderia.repository.ClienteRepository;
import com.panaderia.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    private String normalizeRole(String rol) {
        if (rol == null) return "ROLE_CLIENTE";
        return rol.startsWith("ROLE_") ? rol : "ROLE_" + rol;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // === BUSCAR EMPLEADO ===
        Empleado empleado = empleadoRepository.findByEmail(email).orElse(null);

        if (empleado != null) {

            boolean passwordEsBCrypt = empleado.getPassword().startsWith("$2a$");

            return User.builder()
                    .username(empleado.getEmail())
                    .password(
                            passwordEsBCrypt
                                    ? empleado.getPassword()          // BCrypt
                                    : "{noop}" + empleado.getPassword() // texto plano
                    )
                    .authorities(
                            new SimpleGrantedAuthority(
                                    normalizeRole(empleado.getRol())
                            ))
                    .build();
        }

        // === BUSCAR CLIENTE ===
        Cliente cliente = clienteRepository.findByEmail(email);

        if (cliente != null) {
            return User.builder()
                    .username(cliente.getEmail())
                    .password(cliente.getPassword()) // BCRYPT SIEMPRE
                    .authorities(
                            new SimpleGrantedAuthority(
                                    normalizeRole(cliente.getRol())
                            ))
                    .build();
        }

        // === NO EXISTE ===
        throw new UsernameNotFoundException("Usuario no encontrado: " + email);
    }
}
