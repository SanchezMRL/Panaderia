package com.panaderia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.panaderia.entity.Cliente;
import com.panaderia.entity.Empleado;
import com.panaderia.repository.ClienteRepository;
import com.panaderia.repository.EmpleadoRepository;

@Controller
public class LoginController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String login() {
        return "login";
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String tipoUsuario,
            Model model) {

        if (email.isBlank() || password.isBlank() || tipoUsuario.isBlank()) {
            model.addAttribute("error", "Debe completar todos los campos antes de continuar.");
            return "login";
        }

        // Login cliente (contraseña encriptada)
        if ("cliente".equalsIgnoreCase(tipoUsuario)) {

            Cliente cliente = clienteRepository.findByEmail(email);

            if (cliente != null && passwordEncoder.matches(password, cliente.getPassword())) {
                model.addAttribute("cliente", cliente);
                return "clienteMenu";
            }

            model.addAttribute("error", "Credenciales de cliente incorrectas.");
            return "login";
        }

        // Login admin/empleado (contraseña normal sin hash)
        if ("admin".equalsIgnoreCase(tipoUsuario)) {

            Empleado admin = empleadoRepository.findByEmailAndPassword(email, password);

            if (admin != null) {
                model.addAttribute("empleado", admin);
                return "index";
            }

            model.addAttribute("error", "Credenciales de administrador incorrectas.");
            return "login";
        }

        model.addAttribute("error", "Debe seleccionar un tipo de usuario válido.");
        return "login";
    }
}
