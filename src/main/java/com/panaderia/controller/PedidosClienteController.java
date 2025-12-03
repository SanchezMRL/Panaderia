package com.panaderia.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.panaderia.entity.Cliente;
import com.panaderia.repository.ClienteRepository;
import com.panaderia.service.PedidosClienteService;

@Controller
public class PedidosClienteController {
    
    @Autowired
    private PedidosClienteService pedidosClienteService;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/cliente/pedidos")
    public String MostrarpedidosDeCliente(Principal principal,Model model) {
        Cliente cliente = clienteRepository.findByEmail(principal.getName());
        model.addAttribute("listaPedidosCliente", pedidosClienteService.pedidosDeCliente(cliente.getIdCliente()));
        return "cliente/pedidos";
    }

}
