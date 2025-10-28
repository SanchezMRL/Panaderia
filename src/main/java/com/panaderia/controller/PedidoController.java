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

        // Asignar productos y vínculo entre pedido y detalles
        pedido.getDetalles().forEach(det -> {
            det.setPedidoCliente(pedido); // vínculo bidireccional
            Long idProducto = det.getProducto().getIdProducto(); // corregido nombre
            det.setProducto(productoRepo.findById(idProducto).orElse(null));
        });

        PedidoCliente guardado = pedidoRepo.save(pedido);
        return Map.of("idPedidoCliente", guardado.getIdPedidoCliente()); // corregido nombre
    }
}
