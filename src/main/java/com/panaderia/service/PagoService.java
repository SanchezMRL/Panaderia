package com.panaderia.service;

import com.panaderia.entity.Pago;
import com.panaderia.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    public void registrarPago(Pago pago) {
        pagoRepository.save(pago);
    }
}
