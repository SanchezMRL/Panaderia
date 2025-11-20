package com.panaderia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.panaderia.entity.PedidoCliente;
import java.util.List;

@Repository
public interface PedidoClienteRepository extends JpaRepository<PedidoCliente, Long> {

    // Buscar pedidos por estado (opcional)
    List<PedidoCliente> findByEstado(String estado);

    // Buscar pedidos de un cliente específico
    List<PedidoCliente> findByCliente_IdCliente(Long idCliente);

    // Buscar pedidos de un empleado (si aplica)
    List<PedidoCliente> findByEmpleado_IdEmpleado(Long idEmpleado);
}
