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
        // ✅ Consulta corregida: une producto e inventario
        String sql = """
            SELECT 
                p.id_producto,
                p.nombre,
                p.categoria,
                i.cantidad,
                p.unidad_medida,
                i.ultima_actualizacion
            FROM inventario i
            INNER JOIN producto p ON i.id_producto = p.id_producto
            ORDER BY p.id_producto;
        """;

        try {
            List<Map<String, Object>> inventario = jdbcTemplate.queryForList(sql);
            model.addAttribute("inventario", inventario);
        } catch (Exception e) {
            // Si hay un error (por ejemplo, la tabla no existe), agrega mensaje vacío
            model.addAttribute("inventario", List.of());
            System.err.println("⚠️ Error al cargar inventario: " + e.getMessage());
        }

        // Retorna la plantilla inventario.html
        return "inventario";
    }
}
