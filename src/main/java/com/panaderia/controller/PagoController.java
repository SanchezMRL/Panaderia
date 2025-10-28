package com.panaderia.controller;

import com.panaderia.entity.Pago;
import com.panaderia.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pago")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @PostMapping
    public String registrarPago(@RequestBody Pago pago) {
        try {
            pagoService.registrarPago(pago);
            return "{\"ok\": true}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"ok\": false}";
        }
    }
}
