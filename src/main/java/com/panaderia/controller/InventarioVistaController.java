package com.panaderia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class InventarioVistaController {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    // Vista principal del inventario
    @GetMapping("/inventario")
    public String mostrarInventario(Model model) {

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
            // En caso de error, se envía una lista vacía para evitar fallos en la vista
            model.addAttribute("inventario", List.of());
            System.out.println("Error al cargar inventario: " + e.getMessage());
        }

        return "inventario";
    }


    // Guardar un nuevo producto
    @PostMapping("/inventario/guardar")
    public String guardarProducto(
            @RequestParam String nombre,
            @RequestParam String categoria,
            @RequestParam int cantidad,
            @RequestParam String unidad_medida
    ) {

        try {
            // Guardar el producto en la tabla producto
            String sqlProducto = """
                INSERT INTO producto (nombre, categoria, unidad_medida)
                VALUES (?, ?, ?)
                RETURNING id_producto;
            """;

            Integer idProducto = jdbcTemplate.queryForObject(
                    sqlProducto, Integer.class, nombre, categoria, unidad_medida
            );

            // Guardar el registro del inventario
            String sqlInventario = """
                INSERT INTO inventario (id_producto, cantidad, ultima_actualizacion)
                VALUES (?, ?, NOW());
            """;

            jdbcTemplate.update(sqlInventario, idProducto, cantidad);

        } catch (Exception e) {
            System.out.println("Error al guardar producto: " + e.getMessage());
        }

        return "redirect:/inventario";
    }


    // Actualizar producto existente
    @PostMapping("/inventario/actualizar")
    public String actualizarProducto(
            @RequestParam int id_producto,
            @RequestParam String nombre,
            @RequestParam String categoria,
            @RequestParam int cantidad,
            @RequestParam String unidad_medida
    ) {

        try {
            // Actualizar tabla producto
            String sqlProducto = """
                UPDATE producto
                SET nombre = ?, categoria = ?, unidad_medida = ?
                WHERE id_producto = ?;
            """;

            jdbcTemplate.update(sqlProducto, nombre, categoria, unidad_medida, id_producto);

            // Actualizar tabla inventario
            String sqlInventario = """
                UPDATE inventario
                SET cantidad = ?, ultima_actualizacion = NOW()
                WHERE id_producto = ?;
            """;

            jdbcTemplate.update(sqlInventario, cantidad, id_producto);

        } catch (Exception e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
        }

        return "redirect:/inventario";
    }


    // Eliminar producto e inventario
    @GetMapping("/inventario/eliminar/{id}")
    public String eliminarProducto(@PathVariable("id") int idProducto) {

        try {
            // Primero se elimina de inventario por clave foránea
            String sqlInventario = "DELETE FROM inventario WHERE id_producto = ?;";
            jdbcTemplate.update(sqlInventario, idProducto);

            // Luego se elimina el producto
            String sqlProducto = "DELETE FROM producto WHERE id_producto = ?;";
            jdbcTemplate.update(sqlProducto, idProducto);

        } catch (Exception e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
        }

        return "redirect:/inventario";
    }

}
