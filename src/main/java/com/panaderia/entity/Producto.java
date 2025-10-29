package com.panaderia.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_producto;

    private String nombre;
    private Double precio_base;

    // ✅ Getters y Setters
    public Long getId_producto() {
        return id_producto;
    }

    public void setId_producto(Long id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio_base() {
        return precio_base;
    }

    public void setPrecio_base(Double precio_base) {
        this.precio_base = precio_base;
    }
}
