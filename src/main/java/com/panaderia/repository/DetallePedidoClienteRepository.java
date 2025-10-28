package com.panaderia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.panaderia.entity.DetallePedidoCliente;

public interface DetallePedidoClienteRepository extends JpaRepository<DetallePedidoCliente, Integer> {
}
