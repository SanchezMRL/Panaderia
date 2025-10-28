package com.panaderia.controller;

import com.panaderia.entity.*;
import com.panaderia.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/opinion")
public class OpinionController {

    @Autowired private OpinionRepository opinionRepo;
    @Autowired private ClienteRepository clienteRepo;
    @Autowired private PedidoClienteRepository pedidoRepo;

    @PostMapping
    public Map<String, Object> registrarOpinion(@RequestBody Map<String, Object> datos) {
        Integer idPedido = (Integer) datos.get("id_pedido_cliente");
        Integer idCliente = (Integer) datos.get("id_cliente");

        OpinionPedido op = new OpinionPedido();
        op.setPedidoCliente(pedidoRepo.findById(idPedido).orElse(null));
        op.setCliente(clienteRepo.findById(idCliente).orElse(null));
        op.setComentario((String) datos.get("comentario"));
        op.setCalificacion((Integer) datos.get("calificacion"));
        op.setSatisfaccion((Integer) datos.get("satisfaccion"));
        op.setFecha(LocalDate.now());

        opinionRepo.save(op);
        return Map.of("id_opinion", op.getId_opinion());
    }
}
