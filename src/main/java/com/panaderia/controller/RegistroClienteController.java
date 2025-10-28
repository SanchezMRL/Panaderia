package com.panaderia.controller;

import com.panaderia.entity.Cliente;
import com.panaderia.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistroClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    // Mostrar formulario de registro
    @GetMapping("/registroCliente")
    public String mostrarRegistroCliente() {
        return "registroCliente"; // templates/registroCliente.html
    }

    // Procesar registro
    @PostMapping("/registroCliente")
    public String registrarCliente(Cliente cliente, Model model) {
        // Verificar si el email ya está registrado
        Cliente existente = clienteRepository.findByEmail(cliente.getEmail());
        if (existente != null) {
            model.addAttribute("error", "Este correo ya está registrado. Intente con otro.");
            return "registroCliente";
        }

        // Guardar nuevo cliente
        clienteRepository.save(cliente);

        // Pasar el nombre al menú del cliente
        model.addAttribute("nombre", cliente.getNombre());
        return "clienteMenu"; // ✅ Redirige directamente al menú del cliente
    }
}
