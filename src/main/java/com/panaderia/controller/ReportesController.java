package com.panaderia.controller;

import com.panaderia.entity.Cliente;
import com.panaderia.entity.ReporteGeneralDto;
import com.panaderia.repository.ClienteRepository;
import com.panaderia.service.PedidosClienteService;
import com.panaderia.service.ReporteService;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportesController {
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
