package com.panaderia.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "estadopago")
public class EstadoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEstadoPago;

    private String descripcion;

    // Getters y Setters
    public Integer getIdEstadoPago() { return idEstadoPago; }
    public void setIdEstadoPago(Integer idEstadoPago) { this.idEstadoPago = idEstadoPago; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
