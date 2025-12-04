package com.panaderia.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "reporte_general")
public class ReporteGeneralDto {
    private BigDecimal totalVentas;
    private Long totalPedidos;
    private Long clientesAtendidos;
    private List<MetodoPagoUsoDto> metodosPagoPopulares;

    // Getters y Setters
    public BigDecimal getTotalVentas() { return totalVentas; }
    public void setTotalVentas(BigDecimal totalVentas) { this.totalVentas = totalVentas; }
    public Long getTotalPedidos() { return totalPedidos; }
    public void setTotalPedidos(Long totalPedidos) { this.totalPedidos = totalPedidos; }
    public Long getClientesAtendidos() { return clientesAtendidos; }
    public void setClientesAtendidos(Long clientesAtendidos) { this.clientesAtendidos = clientesAtendidos; }
    public List<MetodoPagoUsoDto> getMetodosPagoPopulares() { return metodosPagoPopulares; }
    public void setMetodosPagoPopulares(List<MetodoPagoUsoDto> metodosPagoPopulares) { this.metodosPagoPopulares = metodosPagoPopulares; }
}