package com.panaderia.repository;

import com.panaderia.entity.MetodoPagoUsoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.panaderia.entity.PedidoCliente;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoCliente, Long> {

   // 1. Total acumulado de ventas
    @Query("SELECT COALESCE(SUM(p.total), 0) FROM Pedido p")
    BigDecimal calcularTotalVentas();

    // 2. Total de pedidos
    @Query("SELECT COUNT(p.id) FROM Pedido p")
    Long contarTotalPedidos();

    // 3. Clientes únicos atendidos
    @Query("SELECT COUNT(DISTINCT p.idCliente) FROM Pedido p")
    Long contarClientesAtendidos();

    // 4. Métodos de pago más usados
    // ¡ASEGÚRATE DE QUE LA RUTA AQUÍ SEA CORRECTA!
    @Query("SELECT new com.panaderia.entity.MetodoPagoUsoDto(p.metodoPago, COUNT(p.metodoPago)) " +
           "FROM Pedido p " +
           "GROUP BY p.metodoPago " +
           "ORDER BY COUNT(p.metodoPago) DESC")
    List<MetodoPagoUsoDto> findMetodosPagoMasUsados();
}