package com.panaderia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;   
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class InventarioController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/inventario")
    public List<Map<String, Object>> obtenerInventario() {
        String sql = """
            SELECT
                p.id_producto,
                p.nombre,
                p.categoria,
                p.cantidad,
                p.unidad_medida,
                p.ultima_actualizacion
            FROM producto p
            ORDER BY p.id_producto;
        """;

        return jdbcTemplate.queryForList(sql);
    }
}
