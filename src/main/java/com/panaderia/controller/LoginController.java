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

    // 🔹 Redirige raíz "/" a "/login"
    @GetMapping("/")
    public String inicio() {
        return "redirect:/login";
    }

    // 🔹 Muestra el formulario de login
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login"; // templates/login.html
    }

    // 🔹 Procesa el formulario de login
    @PostMapping("/login")
    public String procesarLogin(
            @RequestParam String usuario,  // puede ser email o nombre
            @RequestParam String password,
            @RequestParam String tipoUsuario,
            Model model) {

        if ("cliente".equalsIgnoreCase(tipoUsuario)) {
            Cliente cliente = clienteRepository.findByEmailAndPassword(usuario, password);
            if (cliente != null) {
                model.addAttribute("nombre", cliente.getNombre());
                return "clienteMenu"; // clientes van aquí
            } else {
                model.addAttribute("error", "Credenciales de cliente incorrectas");
                return "login";
            }
        } else if ("admin".equalsIgnoreCase(tipoUsuario)) {
            Empleado admin = empleadoRepository.findByNombreAndPassword(usuario, password);
            if (admin != null) {
                model.addAttribute("nombre", admin.getNombre());
                return "index"; // administradores van a index.html
            } else {
                model.addAttribute("error", "Credenciales de administrador incorrectas");
                return "login";
            }
        }

        model.addAttribute("error", "Tipo de usuario no válido");
        return "login";
    }
}

