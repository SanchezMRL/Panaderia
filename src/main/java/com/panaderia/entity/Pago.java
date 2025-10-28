package com.panaderia.entity;

import jakarta.persistence.*; // Para Entity, Table, Id, etc.
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "pago")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_pago;

    @ManyToOne
    @JoinColumn(name = "id_pedido_cliente")
    private PedidoCliente pedidoCliente;

    @Enumerated(EnumType.STRING)
    private MetodoPago metodopago;

    @Enumerated(EnumType.STRING)
    private EstadoPago estadopago;

    private BigDecimal monto;
    private LocalDate fecha_pago;

    // Getters y Setters
    public Integer getIdPago() { return idPago; }
    public void setIdPago(Integer idPago) { this.idPago = idPago; }

    public PedidoCliente getPedidoCliente() { return pedidoCliente; }
    public void setPedidoCliente(PedidoCliente pedidoCliente) { this.pedidoCliente = pedidoCliente; }

    public MetodoPago getMetodoPago() { return metodoPago; }
    public void setMetodoPago(MetodoPago metodoPago) { this.metodoPago = metodoPago; }

    public EstadoPago getEstadoPago() { return estadoPago; }
    public void setEstadoPago(EstadoPago estadoPago) { this.estadoPago = estadoPago; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
}
