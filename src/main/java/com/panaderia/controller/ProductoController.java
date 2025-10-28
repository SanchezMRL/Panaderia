package com.panaderia.controller;

import com.panaderia.entity.Producto;
import com.panaderia.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/producto")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepo;

    @GetMapping("/{id}")
    public ResponseEntity<?> getProducto(@PathVariable Integer id) {
        return productoRepo.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Producto no encontrado"));
    }
}
