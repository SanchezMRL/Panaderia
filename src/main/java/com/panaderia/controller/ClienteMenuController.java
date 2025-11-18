package com.panaderia.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClienteMenuController {

    @GetMapping("/clienteMenu")
    public String mostrarMenuCliente(Authentication authentication, Model model) {

        if (authentication != null) {
            String email = authentication.getName();
            model.addAttribute("email", email);
        }

        return "clienteMenu";
    }
}
