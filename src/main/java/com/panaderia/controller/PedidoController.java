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

    @Autowired 
    private PedidoClienteRepository pedidoRepo;

    @Autowired 
    private ClienteRepository clienteRepo;

    @Autowired 
    private EmpleadoRepository empleadoRepo;

    @Autowired 
    private ProductoRepository productoRepo;

    @PostMapping
    @Transactional
    public Map<String, Object> registrarPedido(@RequestBody PedidoCliente pedido) {
        pedido.setFecha(LocalDate.now());

        // 🟢 Vincular detalles al pedido y cargar productos existentes
        pedido.getDetalles().forEach(det -> {
            det.setPedidoCliente(pedido);
            if (det.getProducto() != null && det.getProducto().getId_producto() != null) {
                // Cambiado a Long (coherente con las entidades)
                Long idProducto = det.getProducto().getId_producto();
                det.setProducto(productoRepo.findById(idProducto).orElse(null));
            }
        });

        // 🟢 Guardar el pedido completo
        PedidoCliente guardado = pedidoRepo.save(pedido);

        // 🟢 Devolver el ID del pedido guardado
        return Map.of("id_pedido_cliente", guardado.getIdPedidoCliente());
    }
}
