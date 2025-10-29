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

            Long idProducto = null;

            // ✅ Intentar obtener el ID del producto desde el objeto recibido
            if (det.getProducto() != null && det.getProducto().getIdProducto() != null) {
                idProducto = det.getProducto().getIdProducto();
            } else {
                try {
                    // ✅ Si vino como "id_producto" (sin objeto anidado)
                    var field = det.getClass().getDeclaredField("id_producto");
                    field.setAccessible(true);
                    Object valor = field.get(det);
                    if (valor != null) {
                        idProducto = Long.valueOf(valor.toString());
                    }
                } catch (Exception ignored) {}
            }

            if (idProducto == null) {
                throw new RuntimeException("⚠️ No se envió un id_producto válido en el detalle.");
            }

            // 🔹 Buscar el producto en la BD
            Producto producto = productoRepo.findById(idProducto)
                    .orElseThrow(() -> new RuntimeException("❌ Producto con ID " + idProducto + " no encontrado."));

            det.setProducto(producto);

            // 🔹 Asignar precio y subtotal
            BigDecimal precio = producto.getPrecioUnitario();
            if (precio == null) {
                throw new RuntimeException("⚠️ El producto con ID " + idProducto + " no tiene precio_unitario definido.");
            }

            det.setPrecioUnitario(precio);

            if (det.getCantidad() != null && det.getCantidad() > 0) {
                det.setSubtotal(precio.multiply(BigDecimal.valueOf(det.getCantidad())));
            } else {
                throw new RuntimeException("⚠️ La cantidad del producto " + idProducto + " no puede ser nula o cero.");
            }
        });

        // 🟢 Guardar el pedido completo con sus detalles
        PedidoCliente guardado = pedidoRepo.save(pedido);

        // 🟢 Devolver el ID del pedido guardado
        return Map.of("id_pedido_cliente", guardado.getIdPedidoCliente());
    }
}
