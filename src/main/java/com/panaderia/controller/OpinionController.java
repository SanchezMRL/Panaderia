package com.panaderia.controller;

import com.panaderia.entity.*;
import com.panaderia.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/opinion")
public class OpinionController {

    @Autowired private OpinionRepository opinionRepo;
    @Autowired private ClienteRepository clienteRepo;
    @Autowired private PedidoClienteRepository pedidoRepo;

    @PostMapping
    public Map<String, Object> registrarOpinion(@RequestBody Map<String, Object> datos) {
        Long idPedido = Long.valueOf(datos.get("idPedidoCliente").toString());
        Long idCliente = Long.valueOf(datos.get("idCliente").toString());

        OpinionPedido opinion = new OpinionPedido();
        opinion.setPedidoCliente(pedidoRepo.findById(idPedido).orElse(null));
        opinion.setCliente(clienteRepo.findById(idCliente).orElse(null));
        opinion.setComentario((String) datos.get("comentario"));
        opinion.setCalificacion(Integer.valueOf(datos.get("calificacion").toString()));
        opinion.setSatisfaccion(Integer.valueOf(datos.get("satisfaccion").toString()));
        opinion.setFecha(LocalDateTime.now());

        opinionRepo.save(opinion);
        return Map.of("idOpinion", opinion.getIdOpinion());
    }
}
