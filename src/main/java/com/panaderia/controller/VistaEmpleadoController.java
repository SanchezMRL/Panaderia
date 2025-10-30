package com.panaderia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VistaEmpleadoController {

    @GetMapping("/agregar")
    public String mostrarFormularioEmpleado() {
        return "agregar"; // busca agregar.html en /templates/
    }
}
