package com.panaderia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class InventarioVistaController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/inventario")
    public String mostrarInventario(Model model) {
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

        // Ejecuta la consulta y obtiene los resultados
        List<Map<String, Object>> inventario = jdbcTemplate.queryForList(sql);

        // Pasa los datos al modelo para Thymeleaf
        model.addAttribute("inventario", inventario);

        // Retorna la plantilla inventario.html (ubicada en src/main/resources/templates)
        return "inventario";
    }
}

