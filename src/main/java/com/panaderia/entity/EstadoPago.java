package com.panaderia.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "estadopago")
public class EstadoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstadoPago;

    private String descripcion;

    // Getters y Setters
    public Long getIdEstadoPago() { return idEstadoPago; }
    public void setIdEstadoPago(Long idEstadoPago) { this.idEstadoPago = idEstadoPago; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
