package com.panaderia.controllers;

import com.panaderia.utils.Database;
import com.panaderia.utils.DateUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class PagoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = req.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) sb.append(line);
            }
            JSONObject body = (JSONObject) new JSONParser().parse(sb.toString());
            int id_pedido_cliente = ((Long)body.get("id_pedido_cliente")).intValue();
            String metodo = (String) body.get("metodo");
            double monto = ((Number)body.get("monto")).doubleValue();
            String fecha = (String) body.get("fecha");
            String estado = (String) body.get("estado");

            try (Connection conn = Database.getConnection()) {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO pagos (id_pedido_cliente, metodo, monto, fecha, estado) VALUES (?, ?, ?, ?, ?)");
                ps.setInt(1, id_pedido_cliente);
                ps.setString(2, metodo);
                ps.setDouble(3, monto);
                ps.setTimestamp(4, DateUtils.parseTimestamp(fecha));
                ps.setString(5, estado);
                ps.executeUpdate();
                out.print("{\"ok\": true}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            try { if (resp.getWriter() != null) resp.getWriter().print("{\"ok\": false}"); } catch (Exception ex) {}
        }
    }
}
