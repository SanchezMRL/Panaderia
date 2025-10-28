package com.panaderia.controller;

import com.panaderia.entity.Producto;
import com.panaderia.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/producto")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepo;

    @GetMapping("/{id}")
    public ResponseEntity<?> getProducto(@PathVariable Long id) { // Cambiado a Long
        Optional<Producto> producto = productoRepo.findById(id);
        if (producto.isPresent()) {
            return ResponseEntity.ok(producto.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Producto no encontrado");
        }
    }
}
