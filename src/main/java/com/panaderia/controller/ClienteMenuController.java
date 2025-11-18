package com.panaderia.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClienteMenuController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/clienteMenu")
    public String clienteMenu(Model model, Authentication auth) {

        String email = auth.getName(); // email del usuario logueado

        Cliente cliente = clienteRepository.findByEmail(email);

        if (cliente == null) {
            return "error"; // por seguridad
        }

        model.addAttribute("cliente", cliente);

        return "clienteMenu";
    }
}
