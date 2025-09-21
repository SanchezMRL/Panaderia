package com.panaderia.models;

public class Producto {
    private int id;
    private String nombre;
    private double precio_base;

    public Producto() {}

    public Producto(int id, String nombre, double precio_base) {
        this.id = id;
        this.nombre = nombre;
        this.precio_base = precio_base;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio_base() { return precio_base; }
    public void setPrecio_base(double precio_base) { this.precio_base = precio_base; }
}
