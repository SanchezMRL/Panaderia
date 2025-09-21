package com.panaderia.models;

public class Pedido {
    private int id;
    private Integer id_cliente;
    private Integer id_proveedor;
    private int id_empleado;
    private String fecha;
    private String estado;

    public Pedido() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Integer getId_cliente() { return id_cliente; }
    public void setId_cliente(Integer id_cliente) { this.id_cliente = id_cliente; }

    public Integer getId_proveedor() { return id_proveedor; }
    public void setId_proveedor(Integer id_proveedor) { this.id_proveedor = id_proveedor; }

    public int getId_empleado() { return id_empleado; }
    public void setId_empleado(int id_empleado) { this.id_empleado = id_empleado; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
