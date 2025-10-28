package com.panaderia.controller;

import com.panaderia.entity.Cliente;
import com.panaderia.entity.Empleado;
import com.panaderia.repository.ClienteRepository;
import com.panaderia.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login"; // archivo login.html
    }

    @PostMapping("/login")
    public String procesarLogin(
            @RequestParam String nombre,
            @RequestParam String acceso,
            @RequestParam String datoExtra,
            Model model) {

        if ("cliente".equalsIgnoreCase(acceso)) {
            Cliente cliente = clienteRepository.findByNombreAndEmail(nombre, datoExtra);
            if (cliente != null) {
                model.addAttribute("usuario", cliente);
                return "clienteMenu"; // vista para clientes
            } else {
                model.addAttribute("error", "Cliente no encontrado");
                return "login";
            }
        } else if ("admin".equalsIgnoreCase(acceso)) {
            Empleado empleado = empleadoRepository.findByNombreAndCargo(nombre, datoExtra);
            if (empleado != null) {
                model.addAttribute("usuario", empleado);
                return "redirect:/index.html"; // vista principal admin
            } else {
                model.addAttribute("error", "Empleado no encontrado");
                return "login";
            }
        }

        model.addAttribute("error", "Tipo de acceso inválido");
        return "login";
    }
}

