package com.panaderia.controller.admin;

import com.panaderia.entity.OpinionPedido;
import com.panaderia.repository.OpinionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping
@CrossOrigin(origins = "*")
public class OpinionAdminController {

    @Autowired
    private OpinionRepository opinionRepo;

    // ============================
    // 1. LISTAR TODAS LAS OPINIONES PARA LA TABLA
    // ============================
    @GetMapping("/opiniones")
    public List<Map<String, Object>> listarOpiniones() {

        return opinionRepo.findAll().stream().map(op -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id_pedido_cliente", 
                op.getPedidoCliente() != null ? op.getPedidoCliente().getIdPedidoCliente() : null
            );
            map.put("comentario", op.getComentario());
            map.put("calificacion", op.getCalificacion());
            map.put("satisfaccion", op.getSatisfaccion());
            map.put("fecha", op.getFecha());
            return map;
        }).collect(Collectors.toList());
    }

    // ============================
    // 2. DASHBOARD: PROMEDIOS PARA EL GRÁFICO
    // ============================
    @GetMapping("/dashboard/opiniones")
    public List<Map<String, Object>> obtenerDashboard() {

        List<OpinionPedido> lista = opinionRepo.findAll();

        // Agrupar por producto (pedidoCliente > producto > nombre)
        Map<String, List<OpinionPedido>> agrupado = lista.stream()
                .filter(op -> op.getPedidoCliente() != null && op.getPedidoCliente().getProducto() != null)
                .collect(Collectors.groupingBy(op -> op.getPedidoCliente().getProducto().getNombre()));

        // Convertir a formato JSON esperado por tu HTML
        List<Map<String, Object>> response = new ArrayList<>();

        for (String producto : agrupado.keySet()) {
            List<OpinionPedido> ops = agrupado.get(producto);

            double promedioCalificacion = ops.stream()
                    .mapToDouble(OpinionPedido::getCalificacion)
                    .average().orElse(0);

            double promedioSatisfaccion = ops.stream()
                    .mapToDouble(OpinionPedido::getSatisfaccion)
                    .average().orElse(0);

            Map<String, Object> fila = new HashMap<>();
            fila.put("nombre", producto);
            fila.put("promedio", promedioCalificacion);
            fila.put("satisfaccion", promedioSatisfaccion);

            response.add(fila);
        }

        return response;
    }
}
