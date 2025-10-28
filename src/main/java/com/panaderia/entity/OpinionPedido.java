package com.panaderia.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "opinion_pedido")
public class OpinionPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_opinion;

    @ManyToOne
    @JoinColumn(name = "id_pedido_cliente")
    private PedidoCliente pedidoCliente;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    private String comentario;
    private Integer calificacion;
    private Integer satisfaccion;
    private LocalDate fecha;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getIdPedidoCliente() { return idPedidoCliente; }
    public void setIdPedidoCliente(int idPedidoCliente) { this.idPedidoCliente = idPedidoCliente; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public int getCalificacion() { return calificacion; }
    public void setCalificacion(int calificacion) { this.calificacion = calificacion; }

    public int getSatisfaccion() { return satisfaccion; }
    public void setSatisfaccion(int satisfaccion) { this.satisfaccion = satisfaccion; }

    public Timestamp getFecha() { return fecha; }
    public void setFecha(Timestamp fecha) { this.fecha = fecha; }
}
