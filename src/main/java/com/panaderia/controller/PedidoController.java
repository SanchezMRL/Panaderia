package com.panaderia.controller;

import com.panaderia.entity.*;
import com.panaderia.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

        // 🟢 Vincular detalles al pedido y calcular precios
        pedido.getDetalles().forEach(det -> {
            det.setPedidoCliente(pedido);

            if (det.getProducto() != null && det.getProducto().getId_producto() != null) {
                Long idProducto = det.getProducto().getId_producto();

                Producto producto = productoRepo.findById(idProducto).orElse(null);
                det.setProducto(producto);

                if (producto != null) {
                    // ✅ Asignar el precio unitario desde el producto
                    det.setPrecioUnitario(producto.getPrecio());

                    // ✅ Calcular el subtotal = cantidad * precio
                    if (det.getCantidad() != null) {
                        BigDecimal subtotal = producto.getPrecio()
                                .multiply(BigDecimal.valueOf(det.getCantidad()));
                        det.setSubtotal(subtotal);
                    }
                }
            }
        });

        // 🟢 Guardar el pedido completo
        PedidoCliente guardado = pedidoRepo.save(pedido);

        // 🟢 Devolver el ID del pedido guardado
        return Map.of("id_pedido_cliente", guardado.getIdPedidoCliente());
    }
}
