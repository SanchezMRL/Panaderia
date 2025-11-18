package com.panaderia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.panaderia.entity.Cliente;
import com.panaderia.repository.ClienteRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class ClienteMenuController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/clienteMenu")   // 🔥 CAMBIADO
    public String clienteMenu(HttpSession session, Model model) {
        Long idCliente = (Long) session.getAttribute("idCliente");

        if (idCliente == null) {
            return "redirect:/login";
        }

        Cliente cliente = clienteRepository.findById(idCliente).orElse(null);

        if (cliente == null) {
            return "redirect:/login";
        }

        model.addAttribute("cliente", cliente);

        return "clienteMenu";   // 🔥 Asegúrate que exista clienteMenu.html
    }
}
