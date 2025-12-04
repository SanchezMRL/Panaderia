package com.panaderia.service;


import com.panaderia.entity.MetodoPagoUsoDto;
import com.panaderia.entity.PedidoCliente;
import com.panaderia.entity.ReporteGeneralDto;
import com.panaderia.repository.PedidoClienteRepository;
import com.panaderia.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

import java.util.List;

@Service
public class ReporteService {
    
    @Autowired
    private PedidoRepository pedidoRepository;

    public ReporteGeneralDto obtenerDatosReporte() {
        ReporteGeneralDto reporte = new ReporteGeneralDto();

        // Obtener los datos del repositorio (ahora no deberían ser nulos)
        BigDecimal totalVentas = pedidoRepository.calcularTotalVentas();
        Long totalPedidos = pedidoRepository.contarTotalPedidos();
        Long clientesAtendidos = pedidoRepository.contarClientesAtendidos();
        List<MetodoPagoUsoDto> metodosPago = pedidoRepository.findMetodosPagoMasUsados();

        // Establecer los datos en el DTO
        reporte.setTotalVentas(totalVentas);
        reporte.setTotalPedidos(totalPedidos);
        reporte.setClientesAtendidos(clientesAtendidos);
        reporte.setMetodosPagoPopulares(metodosPago);

        return reporte;
    }
}