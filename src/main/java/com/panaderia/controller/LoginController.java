package com.panaderia.controller;

import com.panaderia.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String usuario,
                                @RequestParam String password,
                                @RequestParam String tipo,
                                Model model) {

        Object autenticado = loginService.autenticar(usuario, password, tipo);

        if (autenticado != null) {
            if ("admin".equalsIgnoreCase(tipo)) {
                return "redirect:/adminMenu";
            } else {
                return "redirect:/clienteMenu";
            }
        }

        model.addAttribute("error", "Credenciales incorrectas o usuario no encontrado");
        return "login";
    }

    @GetMapping("/adminMenu")
    public String adminMenu() {
        return "adminMenu";
    }

    @GetMapping("/clienteMenu")
    public String clienteMenu() {
        return "clienteMenu";
    }
}
