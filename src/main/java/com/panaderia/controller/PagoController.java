package com.panaderia.controller;

import com.panaderia.entity.Pago;
import com.panaderia.entity.PedidoCliente;
import com.panaderia.repository.PagoRepository;
import com.panaderia.repository.PedidoClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/pago")
public class PagoController {

    @Autowired private PagoRepository pagoRepo;
    @Autowired private PedidoClienteRepository pedidoRepo;

    @PostMapping
    public Map<String, Object> registrarPago(@RequestBody Map<String, Object> datos) {
        Integer idPedido = (Integer) datos.get("id_pedido_cliente");
        PedidoCliente pedido = pedidoRepo.findById(idPedido).orElse(null);

        Pago pago = new Pago();
        pago.setPedidoCliente(pedido);
        pago.setMonto(new java.math.BigDecimal(datos.get("monto").toString()));
        pago.setMetodo((String) datos.get("metodo"));
        pago.setFecha(LocalDate.now());
        pago.setEstado("pendiente");
        pagoRepo.save(pago);
        return Map.of("id_pago", pago.getId_pago());
    }
}
