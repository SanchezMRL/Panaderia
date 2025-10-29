package com.panaderia.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "detalle_pedido_cliente")
public class DetallePedidoCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido_cliente", nullable = false)
    private PedidoCliente pedidoCliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private Integer cantidad;

    // 💰 Campo de precio unitario (NO puede ser nulo)
    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    // 🧮 Subtotal calculado (cantidad × precio)
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;

    // ===== Getters y Setters =====
    public Long getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Long idDetalle) {
        this.idDetalle = idDetalle;
    }

    public PedidoCliente getPedidoCliente() {
        return pedidoCliente;
    }

    public void setPedidoCliente(PedidoCliente pedidoCliente) {
        this.pedidoCliente = pedidoCliente;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
        // Si ya tenemos precio, recalculamos subtotal
        if (this.precioUnitario != null) {
            this.subtotal = this.precioUnitario.multiply(BigDecimal.valueOf(cantidad));
        }
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
        // Si ya tenemos cantidad, recalculamos subtotal
        if (this.cantidad != null) {
            this.subtotal = precioUnitario.multiply(BigDecimal.valueOf(this.cantidad));
        }
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}

