package com.panaderia.controller;

import com.panaderia.entity.Cliente;
import com.panaderia.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistroClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    // 🟢 Mostrar formulario de registro
    @GetMapping("/registroCliente")
    public String mostrarRegistroCliente() {
        return "registroCliente"; // templates/registroCliente.html
    }

    // 🟢 Procesar registro
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

    @GetMapping("/actualizarCliente")
public String mostrarActualizarCliente(@RequestParam(required = false) Long id, Model model) {
    Cliente cliente = null;

    // Si llega un ID, busca al cliente
    if (id != null) {
        cliente = clienteRepository.findById(id).orElse(null);
    }

    if (cliente == null) {
        // Evita error de modelo vacío
        cliente = new Cliente();
    }

    model.addAttribute("cliente", cliente);
    return "actualizar"; // templates/actualizar.html
}


    @PostMapping("/actualizarCliente")
public String actualizarCliente(Cliente cliente, Model model) {
    Cliente existente = clienteRepository.findById(cliente.getIdCliente()).orElse(null);

    if (existente == null) {
        model.addAttribute("error", "Cliente no encontrado");
        return "actualizar";
    }

    // Actualiza los campos
    existente.setNombre(cliente.getNombre());
    existente.setEmail(cliente.getEmail());
    existente.setTelefono(cliente.getTelefono());
    existente.setDireccion(cliente.getDireccion());

    clienteRepository.save(existente);

    model.addAttribute("nombre", existente.getNombre());
    model.addAttribute("mensaje", "✅ Perfil actualizado correctamente");
    return "clienteMenu"; // redirige al panel del cliente
}
}
