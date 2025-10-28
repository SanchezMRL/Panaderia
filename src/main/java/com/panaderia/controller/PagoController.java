package com.panaderia.controller;

import com.panaderia.entity.*;
import com.panaderia.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/pago")
public class PagoController {

    @Autowired private PagoRepository pagoRepo;
    @Autowired private PedidoClienteRepository pedidoRepo;
    @Autowired private MetodoPagoRepository metodoPagoRepo;
    @Autowired private EstadoPagoRepository estadoPagoRepo;

    @PostMapping
public Map<String, Object> registrarPago(@RequestBody Map<String, Object> datos) {
    Long idPedido = Long.valueOf(datos.get("id_pedido_cliente").toString());
    Long idMetodo = Long.valueOf(datos.get("id_metodo_pago").toString());
    Long idEstado = Long.valueOf(datos.get("id_estado_pago").toString());

    PedidoCliente pedido = pedidoRepo.findById(idPedido).orElse(null);

    Pago pago = new Pago();
    pago.setPedidoCliente(pedido);
    pago.setMonto(new BigDecimal(datos.get("monto").toString()));
    pago.setMetodoPago(metodoRepo.findById(idMetodo).orElse(null));
    pago.setEstadoPago(estadoRepo.findById(idEstado).orElse(null));
    pago.setFecha(LocalDateTime.now());

    pagoRepo.save(pago);
    return Map.of("id_pago", pago.getId_pago());
}

