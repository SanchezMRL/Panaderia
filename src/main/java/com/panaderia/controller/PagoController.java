package com.panaderia.controller;

import com.panaderia.entity.*;
import com.panaderia.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/pago")
public class PagoController {

    @Autowired private PagoRepository pagoRepo;
    @Autowired private PedidoClienteRepository pedidoRepo;
    @Autowired private MetodoPagoRepository metodoRepo;
    @Autowired private EstadoPagoRepository estadoRepo;

    @PostMapping
    public Map<String, Object> registrarPago(@RequestBody Map<String, Object> datos) {
        // ✅ Conversión segura de IDs
        Integer idPedido = Integer.valueOf(datos.get("id_pedido_cliente").toString());
        Long idMetodo = Long.valueOf(datos.get("id_metodo_pago").toString());
        Long idEstado = Long.valueOf(datos.get("id_estado_pago").toString());

        // ✅ Buscar entidades relacionadas
        PedidoCliente pedido = pedidoRepo.findById(idPedido).orElse(null);
        MetodoPago metodo = metodoRepo.findById(idMetodo).orElse(null);
        EstadoPago estado = estadoRepo.findById(idEstado).orElse(null);

        // ✅ Crear y guardar el pago
        Pago pago = new Pago();
        pago.setPedidoCliente(pedido);
        pago.setMetodoPago(metodo);
        pago.setEstadoPago(estado);
        pago.setMonto(new BigDecimal(datos.get("monto").toString()));
        pago.setFecha(LocalDateTime.now());

        pagoRepo.save(pago);

        // ✅ Devolver ID correcto
        return Map.of("id_pago", pago.getIdPago());
    }
}

