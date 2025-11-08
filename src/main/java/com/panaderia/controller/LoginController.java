package com.panaderia.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

    // Redirige raíz "/" a "/index"
    @GetMapping("/")
    public String login() {
    return "login"; 
    }


    // Muestra el formulario de login
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login"; // templates/login.html
    }

    // Procesa el formulario de login
    @PostMapping("/login")
    public String procesarLogin(
            @RequestParam(required = false) String email,     
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String tipoUsuario,
            Model model) {

        // Validar campos vacíos o faltantes
        if (email == null || password == null || tipoUsuario == null ||
            email.isBlank() || password.isBlank() || tipoUsuario.isBlank()) {
            model.addAttribute("error", "Debe completar todos los campos antes de continuar.");
            return "login";
        }

        // Si es cliente
        if ("cliente".equalsIgnoreCase(tipoUsuario)) {
            Cliente cliente = clienteRepository.findByEmailAndPassword(email, password);
            if (cliente != null) {
                model.addAttribute("cliente", cliente); 
                return "clienteMenu"; // Página para clientes
            } else {
                model.addAttribute("error", "Credenciales de cliente incorrectas.");
                return "login";
            }
        }

        // Si es administrador / empleado
        if ("admin".equalsIgnoreCase(tipoUsuario)) {
            Empleado admin = empleadoRepository.findByEmailAndPassword(email, password); 
            if (admin != null) {
                model.addAttribute("empleado", admin); 
                return "index"; // Página principal del administrador
            } else {
                model.addAttribute("error", "Credenciales de administrador incorrectas.");
                return "login";
            }
        }

        // Si no seleccionó tipo de usuario válido
        model.addAttribute("error", "Debe seleccionar un tipo de usuario válido.");
        return "login";
    }

}
