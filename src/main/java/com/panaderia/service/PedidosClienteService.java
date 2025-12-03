package com.panaderia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panaderia.entity.PedidoCliente;
import com.panaderia.repository.PedidoClienteRepository;

@Service
public class PedidosClienteService {
    
    @Autowired
    private PedidoClienteRepository pedidoClienteRepository;

    public List<PedidoCliente> pedidosDeCliente(Long id){
        return pedidoClienteRepository.findByCliente_IdCliente(id);
    }

}
