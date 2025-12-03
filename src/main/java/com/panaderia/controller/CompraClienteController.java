package com.panaderia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CompraClienteController {

    @GetMapping("/cliente/compra")
    public String getMethodName() {
        return "cliente/compra";
    }
    
    
}
