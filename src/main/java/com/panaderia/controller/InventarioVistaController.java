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

    @GetMapping("/inventario-vista")
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

        List<Map<String, Object>> inventario = jdbcTemplate.queryForList(sql);
        model.addAttribute("inventario", inventario);
        return "inventario"; // buscará templates/inventario.html
    }
}
