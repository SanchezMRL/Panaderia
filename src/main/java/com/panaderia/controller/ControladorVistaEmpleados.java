package com.panaderia.controller;

import com.panaderia.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/empleados")
public class ControladorVistaEmpleados {

    @Autowired
    private EmpleadoRepository empleadoRepo;

    @GetMapping
    public String mostrarEmpleados(Model model) {
        model.addAttribute("empleados", empleadoRepo.findAll());
        return "empleados";  // Nombre del HTML
    }
}
