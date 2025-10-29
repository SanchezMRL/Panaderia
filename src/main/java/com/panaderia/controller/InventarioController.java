package com.panaderia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;   // ✅ Este es el que faltaba
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public List<Map<String, Object>> obtenerInventario() {
        String sql = """
            SELECT 
                p.id_producto,
                p.nombre,
                c.nombre AS categoria,
                p.cantidad,
                p.unidad_medida,
                p.ultima_actualizacion
            FROM producto p
            LEFT JOIN categoria c ON p.id_categoria = c.id_categoria
            ORDER BY p.id_producto;
        """;

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> inventario =
                (List<Map<String, Object>>) (List<?>) jdbcTemplate.queryForList(sql);

        return inventario;
    }
}
