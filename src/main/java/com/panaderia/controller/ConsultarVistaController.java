package com.panaderia.controller;

import com.panaderia.entity.PedidoCliente;
import com.panaderia.entity.DetallePedidoCliente;
import com.panaderia.entity.OpinionPedido;
import com.panaderia.repository.PedidoClienteRepository;
import com.panaderia.repository.OpinionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class ConsultarVistaController {

    @Autowired
    private PedidoClienteRepository pedidoClienteRepo;

    @Autowired
    private OpinionRepository opinionRepo;

    @GetMapping("/consultar")
    public String mostrarFormulario() {
        return "consultar"; // muestra el HTML con el formulario vacío
    }

    @PostMapping("/consultar")
    public String buscarPedido(
            @RequestParam("tipoPedido") String tipo,
            @RequestParam("pedidoId") Long id,
            Model model) {

        if (tipo.equalsIgnoreCase("cliente")) {
            Optional<PedidoCliente> pedidoOpt = pedidoClienteRepo.findById(id);
            if (pedidoOpt.isEmpty()) {
                model.addAttribute("mensaje", "Pedido no encontrado");
                return "consultar";
            }

            PedidoCliente pedido = pedidoOpt.get();
            model.addAttribute("pedido", pedido);

            // buscar opinión asociada
            Optional<OpinionPedido> opinion = opinionRepo.findByPedidoCliente_IdPedidoCliente(id);
            opinion.ifPresent(o -> model.addAttribute("opinion", o));

            model.addAttribute("tipo", "cliente");
            return "consultar";
        }

        model.addAttribute("mensaje", "Consultas para proveedor aún no implementadas");
        return "consultar";
    }
}
