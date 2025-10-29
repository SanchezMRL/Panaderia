package com.panaderia.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    private String nombre;

    // 🔁 Cambio de tipo Double → BigDecimal (más preciso para dinero)
    @Column(name = "precio_base", precision = 10, scale = 2, nullable = false)
    @JsonProperty("precio_base")  // 👈 Así el JSON devolverá "precio_base"
    private BigDecimal precioUnitario;

    // ===== Getters y Setters =====
    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}
