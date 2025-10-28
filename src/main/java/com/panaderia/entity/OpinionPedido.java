package com.panaderia.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "opinion_pedido")
public class OpinionPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOpinion;

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

    // Getters y setters
    public Integer getIdOpinion() { return idOpinion; }
    public void setIdOpinion(Integer idOpinion) { this.idOpinion = idOpinion; }

    public PedidoCliente getPedidoCliente() { return pedidoCliente; }
    public void setPedidoCliente(PedidoCliente pedidoCliente) { this.pedidoCliente = pedidoCliente; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public Integer getCalificacion() { return calificacion; }
    public void setCalificacion(Integer calificacion) { this.calificacion = calificacion; }

    public Integer getSatisfaccion() { return satisfaccion; }
    public void setSatisfaccion(Integer satisfaccion) { this.satisfaccion = satisfaccion; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
}
