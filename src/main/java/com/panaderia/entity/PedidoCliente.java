package com.panaderia.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pedido_cliente")
public class PedidoCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_pedido_cliente;

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

    // Getters y Setters
}
