package com.panaderia.controller;

import com.panaderia.entity.Producto;
import com.panaderia.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inventario")
public class InventarioController {

    @Autowired
    private ProductoRepository productoRepo;

    // 📦 Endpoint para listar todos los productos del inventario
    @GetMapping
    public List<Map<String, Object>> listarInventario() {
        // Recupera todos los productos
        List<Producto> productos = productoRepo.findAll();

        // Transforma a formato JSON plano compatible con el frontend
        return productos.stream().map(p -> Map.of(
                "id_producto", p.getIdProducto(),
                "nombre", p.getNombre(),
                "categoria", "Panadería", // Puedes reemplazar con p.getCategoria() si existe
                "cantidad", 50, // Si tienes campo real en BD, reemplázalo
                "unidad_medida", "unidad", // idem
                "ultima_actualizacion", java.time.LocalDate.now()
        )).toList();
    }
}
