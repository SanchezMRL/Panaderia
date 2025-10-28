package com.panaderia.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_pedido_cliente")
    private int idPedidoCliente;

    private String metodo;
    private double monto;
    private Timestamp fecha;
    private String estado;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getIdPedidoCliente() { return idPedidoCliente; }
    public void setIdPedidoCliente(int idPedidoCliente) { this.idPedidoCliente = idPedidoCliente; }

    public String getMetodo() { return metodo; }
    public void setMetodo(String metodo) { this.metodo = metodo; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public Timestamp getFecha() { return fecha; }
    public void setFecha(Timestamp fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
