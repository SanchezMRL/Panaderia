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

        // 🔁 Recorremos los detalles con un bucle clásico (no lambda)
        for (DetallePedidoCliente det : pedido.getDetalles()) {
            det.setPedidoCliente(pedido);

            Long idProducto = null;

            // ✅ Obtener el ID del producto
            if (det.getProducto() != null && det.getProducto().getIdProducto() != null) {
                idProducto = det.getProducto().getIdProducto();
            } else {
                try {
                    // Si el JSON vino con "id_producto" plano
                    var field = det.getClass().getDeclaredField("id_producto");
                    field.setAccessible(true);
                    Object valor = field.get(det);
                    if (valor != null) idProducto = Long.valueOf(valor.toString());
                } catch (Exception ignored) {}
            }

            if (idProducto == null) {
                throw new RuntimeException("⚠️ No se envió un id_producto válido en el detalle.");
            }

            // 🔹 Buscar el producto en la base de datos
            Producto producto = productoRepo.findById(idProducto)
                    .orElseThrow(() -> new RuntimeException("❌ Producto con ID " + idProducto + " no encontrado."));

            det.setProducto(producto);

            // 🔹 Asignar precio unitario y subtotal
            BigDecimal precio = producto.getPrecioUnitario();
            if (precio == null) {
                throw new RuntimeException("⚠️ El producto con ID " + idProducto + " no tiene precio definido.");
            }

            det.setPrecioUnitario(precio);

            if (det.getCantidad() == null || det.getCantidad() <= 0) {
                throw new RuntimeException("⚠️ La cantidad del producto " + idProducto + " no puede ser nula o cero.");
            }

            det.setSubtotal(precio.multiply(BigDecimal.valueOf(det.getCantidad())));
        }

        // 🟢 Guardar el pedido completo con sus detalles
        PedidoCliente guardado = pedidoRepo.save(pedido);

        // 🟢 Devolver el ID del pedido guardado
        return Map.of("id_pedido_cliente", guardado.getIdPedidoCliente());
    }
}
