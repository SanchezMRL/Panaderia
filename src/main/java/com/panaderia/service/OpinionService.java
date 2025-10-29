package com.panaderia.service;

import com.panaderia.entity.OpinionPedido;
import com.panaderia.repository.OpinionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OpinionService {

    @Autowired
    private OpinionRepository opinionRepository;

    // ✅ Guardar o actualizar una opinión
    public void guardarOpinion(OpinionPedido opinion) {
        opinionRepository.save(opinion);
    }

    // ✅ Obtener todas las opiniones
    public List<OpinionPedido> listarOpiniones() {
        return opinionRepository.findAll();
    }

    // ✅ Buscar opiniones por cliente (cambiado Integer → Long)
    public List<OpinionPedido> buscarPorCliente(Long idCliente) {
        return opinionRepository.findByCliente_IdCliente(idCliente);
    }

    // ✅ Buscar opiniones por pedido (cambiado Integer → Long)
    public List<OpinionPedido> buscarPorPedido(Long idPedidoCliente) {
        return opinionRepository.findByPedidoCliente_IdPedidoCliente(idPedidoCliente);
    }
}
