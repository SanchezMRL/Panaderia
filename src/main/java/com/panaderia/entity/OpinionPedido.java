package com.panaderia.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "opinion_pedido")
public class OpinionPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOpinion;

    @ManyToOne
    @JoinColumn(name = "id_pedido_cliente")
    private PedidoCliente pedidoCliente;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    private String comentario;
    private Integer calificacion;
    private Integer satisfaccion;
    private LocalDateTime fecha;

    // Getters y setters
    public Long getIdOpinion() { return idOpinion; }
    public void setIdOpinion(Long idOpinion) { this.idOpinion = idOpinion; }

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

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
