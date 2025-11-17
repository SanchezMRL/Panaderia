package com.panaderia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // Mostrar la página de login
    @GetMapping({"/", "/login"})
    public String mostrarLogin() {
        return "login"; // Thymeleaf renderiza login.html
    }

    @GetMapping("/registroCliente")
    public String registroCliente() {
        return "registroCliente"; // Tu página de registro
    }
}
