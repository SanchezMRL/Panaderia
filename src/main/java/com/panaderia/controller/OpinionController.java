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

    @Autowired 
    private OpinionRepository opinionRepo;

    @Autowired 
    private ClienteRepository clienteRepo;

    @Autowired 
    private PedidoClienteRepository pedidoRepo;

    @PostMapping
    public Map<String, Object> registrarOpinion(@RequestBody Map<String, Object> datos) {
        // 🟢 IDs como Integer (coincide con las entidades)
        Integer idPedido = Integer.valueOf(datos.get("id_pedido_cliente").toString());
        Integer idCliente = Integer.valueOf(datos.get("id_cliente").toString());

        OpinionPedido op = new OpinionPedido();
        op.setPedidoCliente(pedidoRepo.findById(idPedido).orElse(null));
        op.setCliente(clienteRepo.findById(idCliente).orElse(null));
        op.setComentario((String) datos.get("comentario"));
        op.setCalificacion(Integer.valueOf(datos.get("calificacion").toString()));
        op.setSatisfaccion(Integer.valueOf(datos.get("satisfaccion").toString()));
        op.setFecha(LocalDateTime.now()); // ✅ se guarda correctamente

        opinionRepo.save(op);

        // 🟢 Asegúrate que en OpinionPedido el campo sea “idOpinion”
        return Map.of("id_opinion", op.getIdOpinion());
    }
}
