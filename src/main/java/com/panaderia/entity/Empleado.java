package com.panaderia.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "empleado")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_empleado;

    private String nombre;
    private String cargo;
    private String password;

    public Integer getId_empleado() { return id_empleado; }
    public void setId_empleado(Integer id_empleado) { this.id_empleado = id_empleado; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
