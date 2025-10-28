package com.panaderia.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pedido_cliente")
public class PedidoCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPedidoCliente;

    private LocalDate fecha;
    private String estado;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_empleado")
    private Empleado empleado;

    @OneToMany(mappedBy = "pedidoCliente", cascade = CascadeType.ALL)
    private List<DetallePedidoCliente> detalles;

    // Getters y setters
    public Integer getIdPedidoCliente() { return idPedidoCliente; }
    public void setIdPedidoCliente(Integer idPedidoCliente) { this.idPedidoCliente = idPedidoCliente; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Empleado getEmpleado() { return empleado; }
    public void setEmpleado(Empleado empleado) { this.empleado = empleado; }

    public List<DetallePedidoCliente> getDetalles() { return detalles; }
    public void setDetalles(List<DetallePedidoCliente> detalles) { this.detalles = detalles; }
}
