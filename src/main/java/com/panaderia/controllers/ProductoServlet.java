package com.panaderia.controllers;

import com.panaderia.utils.Database;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Servlet para obtener información de un producto por su ID.
 * Ejemplo de URL: /api/producto/5
 */
@WebServlet("/api/producto/*")
public class ProductoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json;charset=UTF-8");
        String pathInfo = req.getPathInfo();

        try (PrintWriter out = resp.getWriter()) {

            if (pathInfo == null || pathInfo.equals("/")) {
                out.print("{\"error\": \"Producto no especificado\"}");
                return;
            }

            // Extrae el ID de la URL
            String idStr = pathInfo.substring(1);
            int id;
            try {
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                out.print("{\"error\": \"ID inválido\"}");
                return;
            }

            // Consulta la BD
            try (Connection conn = Database.getConnection()) {
                PreparedStatement ps = conn.prepareStatement(
                    "SELECT id_producto, nombre, precio_base FROM producto WHERE id_producto = ?"
                );
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    JSONObject obj = new JSONObject();
                    obj.put("id_producto", rs.getInt("id_producto"));
                    obj.put("nombre", rs.getString("nombre"));
                    obj.put("precio_base", rs.getDouble("precio_base"));
                    out.print(obj.toJSONString());
                } else {
                    out.print("{\"error\": \"Producto no encontrado\"}");
                }

            } catch (Exception e) {
                e.printStackTrace();
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("{\"error\": \"Error al consultar la base de datos: " +
                          e.getMessage().replace("\"", "") + "\"}");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            resp.getWriter().print("{\"error\": \"Error interno en el servidor\"}");
        }
    }
}
