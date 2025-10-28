package com.panaderia.service;

import com.panaderia.entity.Cliente;
import com.panaderia.entity.Empleado;
import com.panaderia.repository.ClienteRepository;
import com.panaderia.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    /**
     * Autenticación general de usuarios.
     */
    public Object autenticar(String usuario, String password, String tipo) {
        if ("cliente".equalsIgnoreCase(tipo)) {
            return clienteRepository.findByEmailAndPassword(usuario, password);
        } else if ("admin".equalsIgnoreCase(tipo)) {
            return empleadoRepository.findByNombreAndPassword(usuario, password);
        }
        return null;
    }
}

