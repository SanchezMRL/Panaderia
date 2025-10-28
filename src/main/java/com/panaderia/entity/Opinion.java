package com.panaderia.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "opiniones")
public class Opinion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_pedido_cliente")
    private int idPedidoCliente;

    private String comentario;
    private int calificacion;
    private int satisfaccion;
    private Timestamp fecha;

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
