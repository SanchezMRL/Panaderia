package com.panaderia.service;

import com.panaderia.entity.Cliente;
import com.panaderia.entity.Empleado;
import com.panaderia.repository.ClienteRepository;
import com.panaderia.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1️⃣ Buscar EMPLEADO primero
        Empleado empleado = empleadoRepository.findByEmail(username);
        if (empleado != null) {
            return new User(
                    empleado.getEmail(),
                    empleado.getPassword(),   // SIN BCRYPT — Y NO CAUSA ERROR
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
        }

        // 2️⃣ Buscar CLIENTE
        Cliente cliente = clienteRepository.findByEmail(username);
        if (cliente != null) {
            return new User(
                    cliente.getEmail(),
                    cliente.getPassword(),   // AQUÍ SÍ VA BCRYPT
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_CLIENTE"))
            );
        }

        // 3️⃣ Si no existe
        throw new UsernameNotFoundException("Usuario no encontrado: " + username);
    }
}
