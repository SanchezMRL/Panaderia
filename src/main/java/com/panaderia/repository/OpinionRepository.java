package com.panaderia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.panaderia.entity.OpinionPedido;
import java.util.List;

@Repository
public interface OpinionRepository extends JpaRepository<OpinionPedido, Long> {

    // 🔍 Buscar opiniones por pedido
    List<OpinionPedido> findByPedidoCliente_IdPedidoCliente(Long idPedidoCliente);

    // 🔍 Buscar opiniones por cliente
    List<OpinionPedido> findByCliente_IdCliente(Long idCliente);
}
