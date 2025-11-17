package com.panaderia.controller;

import com.panaderia.entity.Cliente;
import com.panaderia.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistroClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Mostrar formulario de registro
    @GetMapping("/registroCliente")
    public String mostrarRegistroCliente() {
        return "registroCliente";
    }

    // Procesar registro
    @PostMapping("/registroCliente")
    public String registrarCliente(Cliente cliente, Model model) {

        Cliente existente = clienteRepository.findByEmail(cliente.getEmail());
        if (existente != null) {
            model.addAttribute("error", "Este correo ya está registrado. Intente con otro.");
            return "registroCliente";
        }

        // 🔥 ASIGNAR ROL CLIENTE
        cliente.setRol("CLIENTE");

        // Encriptar la contraseña antes de guardar
        String passwordEncriptada = passwordEncoder.encode(cliente.getPassword());
        cliente.setPassword(passwordEncriptada);

        clienteRepository.save(cliente);
        model.addAttribute("nombre", cliente.getNombre());

        return "clienteMenu";
    }

    // Mostrar formulario de actualización
    @GetMapping("/actualizarCliente")
    public String mostrarActualizarCliente(@RequestParam(required = false) Long id, Model model) {

        Cliente cliente = null;

        if (id != null) {
            cliente = clienteRepository.findById(id).orElse(null);
        }

        if (cliente == null) {
            cliente = new Cliente();
        }

        model.addAttribute("cliente", cliente);

        return "actualizar";
    }

    // Procesar actualización
    @PostMapping("/actualizarCliente")
    public String actualizarCliente(@ModelAttribute("cliente") Cliente cliente, Model model) {

        Cliente existente = clienteRepository.findById(cliente.getIdCliente()).orElse(null);

        if (existente == null) {
            model.addAttribute("error", "Cliente no encontrado");
            return "actualizar";
        }

        // Actualizar datos básicos
        existente.setNombre(cliente.getNombre());
        existente.setEmail(cliente.getEmail());
        existente.setTelefono(cliente.getTelefono());
        existente.setDireccion(cliente.getDireccion());

        // 🔥 ROL DEBE PERMANECER
        existente.setRol("CLIENTE");

        // Si el usuario ingresó una nueva contraseña → encriptar
        if (cliente.getPassword() != null && !cliente.getPassword().isBlank()) {
            String passwordEncriptada = passwordEncoder.encode(cliente.getPassword());
            existente.setPassword(passwordEncriptada);
        }

        clienteRepository.save(existente);

        model.addAttribute("nombre", existente.getNombre());
        model.addAttribute("mensaje", "Perfil actualizado correctamente");

        return "clienteMenu";
    }
}
