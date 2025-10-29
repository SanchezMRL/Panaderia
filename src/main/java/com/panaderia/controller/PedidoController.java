package com.panaderia.controller;

import com.panaderia.entity.*;
import com.panaderia.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {

    @Autowired private PedidoClienteRepository pedidoRepo;
    @Autowired private ClienteRepository clienteRepo;
    @Autowired private EmpleadoRepository empleadoRepo;
    @Autowired private ProductoRepository productoRepo;

    @PostMapping
    @Transactional
    public Map<String, Object> registrarPedido(@RequestBody PedidoCliente pedido) {
        pedido.setFecha(LocalDate.now());

        pedido.getDetalles().forEach(det -> {
            det.setPedidoCliente(pedido);
            if (det.getProducto() != null && det.getProducto().getId_producto() != null) {
                det.setProducto(productoRepo.findById(det.getProducto().getId_producto()).orElse(null));
            }
        });

        PedidoCliente guardado = pedidoRepo.save(pedido);
        return Map.of("id_pedido_cliente", guardado.getIdPedidoCliente());
    }
}
