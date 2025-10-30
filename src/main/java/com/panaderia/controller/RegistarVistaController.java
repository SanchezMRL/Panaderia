package com.panaderia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistarVistaController {

    @GetMapping("/registrar")
    public String mostrarRegistrarPedido() {
        // ✅ Devuelve el nombre del archivo registrar.html
        return "registrar";
    }
}
