package com.panaderia.service;

import com.panaderia.entity.OpinionPedido;
import com.panaderia.repository.OpinionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OpinionService {

    @Autowired
    private OpinionRepository opinionRepository;

    // ✅ Guardar o actualizar una opinión
    public void guardarOpinion(OpinionPedido opinion) {
        opinionRepository.save(opinion);
    }

    // ✅ Obtener todas las opiniones
    public java.util.List<OpinionPedido> listarOpiniones() {
        return opinionRepository.findAll();
    }

    // ✅ Buscar UNA sola opinión por cliente
    public Optional<OpinionPedido> buscarPorCliente(Long idCliente) {
        return opinionRepository.findByCliente_IdCliente(idCliente)
                .stream()
                .findFirst(); // 🔹 Devuelve la primera opinión (o vacía si no hay)
    }

    // ✅ Buscar UNA sola opinión por pedido
    public Optional<OpinionPedido> buscarPorPedido(Long idPedidoCliente) {
        return opinionRepository.findByPedidoCliente_IdPedidoCliente(idPedidoCliente)
                .stream()
                .findFirst(); // 🔹 Devuelve la primera opinión (o vacía si no hay)
    }
}
