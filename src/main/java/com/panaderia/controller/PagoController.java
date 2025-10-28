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

        Integer idPedido = (Integer) datos.get("id_pedido_cliente");
        Integer idMetodo = (Integer) datos.get("id_metodo_pago");
        Integer idEstado = (Integer) datos.get("id_estado_pago");

        PedidoCliente pedido = pedidoRepo.findById(idPedido).orElse(null);
        MetodoPago metodo = metodoPagoRepo.findById(idMetodo).orElse(null);
        EstadoPago estado = estadoPagoRepo.findById(idEstado).orElse(null);

        Pago pago = new Pago();
        pago.setPedidoCliente(pedido);
        pago.setMetodoPago(metodo);
        pago.setEstadoPago(estado);
        pago.setMonto(new java.math.BigDecimal(datos.get("monto").toString()));
        pago.setFecha(LocalDate.now());

        pagoRepo.save(pago);

        return Map.of("id_pago", pago.getIdPago());
    }
}
