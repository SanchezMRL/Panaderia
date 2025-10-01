package com.panaderia.controllers;

import com.panaderia.utils.Database;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProductoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json;charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            if (pathInfo == null || pathInfo.equals("/")) {
                out.print("{\"error\": \"Producto no especificado\"}");
                return;
            }

            String idStr = pathInfo.substring(1);
            int id;
            try {
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                out.print("{\"error\": \"ID inválido\"}");
                return;
            }

            try (Connection conn = Database.getConnection()) {
                // ✅ Consulta usando tabla en minúsculas (producto)
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
                e.printStackTrace(out);
                out.print("{\"error\": \"Error al consultar BD\"}");
            }
        }
    }
}
