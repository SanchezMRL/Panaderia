package com.panaderia.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "metodopago")
public class MetodoPagoUsoDto {
    private String metodoPago;
    private Long cantidad;

    // Getters y Setters
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
    public Long getCantidad() { return cantidad; }
    public void setCantidad(Long cantidad) { this.cantidad = cantidad; }
}