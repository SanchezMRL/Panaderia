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

        // Recorremos los detalles con bucle clásico
        for (DetallePedidoCliente det : pedido.getDetalles()) {

            det.setPedidoCliente(pedido);

            // Validar producto y obtener ID
            if (det.getProducto() == null || det.getProducto().getIdProducto() == null) {
                throw new IllegalArgumentException("Debe especificarse un producto válido en cada detalle.");
            }

            Long idProducto = det.getProducto().getIdProducto();

            // Buscar producto en base de datos
            Producto producto = productoRepo.findById(idProducto)
                    .orElseThrow(() -> new IllegalArgumentException("Producto con ID " + idProducto + " no encontrado."));

            det.setProducto(producto);

            // Validar precio
            BigDecimal precio = producto.getPrecioUnitario();
            if (precio == null) {
                throw new IllegalArgumentException("El producto con ID " + idProducto + " no tiene precio definido.");
            }

            det.setPrecioUnitario(precio);

            // Validar cantidad
            if (det.getCantidad() == null || det.getCantidad() <= 0) {
                throw new IllegalArgumentException("La cantidad del producto " + idProducto + " no puede ser nula o cero.");
            }

            // Calcular subtotal
            BigDecimal subtotal = precio.multiply(BigDecimal.valueOf(det.getCantidad()));
            det.setSubtotal(subtotal);
        }

        // Guardar pedido y detalles
        PedidoCliente guardado = pedidoRepo.save(pedido);

        // Retornar ID del pedido guardado
        return Map.of("id_pedido_cliente", guardado.getIdPedidoCliente());
    }
}
