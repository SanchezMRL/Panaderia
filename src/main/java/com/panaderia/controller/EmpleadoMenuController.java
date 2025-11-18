package com.panaderia.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmpleadoMenuController {

    @GetMapping("/index")
    public String mostrarMenuEmpleado(Authentication auth, Model model) {

        if (auth != null) {
            model.addAttribute("email", auth.getName());
        }

        return "index";
    }
}
