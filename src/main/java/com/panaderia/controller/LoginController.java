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

    // 🔹 Redirigir raíz "/" hacia "/login"
    @GetMapping("/")
    public String inicio() {
        return "redirect:/login";
    }

    // 🔹 Mostrar la página de login
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login"; // templates/login.html
    }

    // 🔹 Procesar el formulario de login
    @PostMapping("/login")
    public String procesarLogin(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String tipoUsuario,
            Model model) {

        if ("cliente".equalsIgnoreCase(tipoUsuario)) {
            Cliente cliente = clienteRepository.findByEmail(email);
            if (cliente != null && password.equals("123")) { // puedes cambiar la lógica luego
                model.addAttribute("nombre", cliente.getNombre());
                return "clienteMenu"; // templates/clienteMenu.html
            } else {
                model.addAttribute("error", "Credenciales de cliente incorrectas");
                return "login";
            }
        } else if ("admin".equalsIgnoreCase(tipoUsuario)) {
            Empleado admin = empleadoRepository.findByEmail(email);
            if (admin != null && password.equals("admin")) { // puedes mejorar luego
                model.addAttribute("nombre", admin.getNombre());
                return "index"; // templates/index.html
            } else {
                model.addAttribute("error", "Credenciales de administrador incorrectas");
                return "login";
            }
        }

        model.addAttribute("error", "Tipo de usuario no válido");
        return "login";
    }
}
