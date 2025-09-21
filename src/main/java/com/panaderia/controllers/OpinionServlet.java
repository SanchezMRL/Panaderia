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

public class OpinionServlet extends HttpServlet {
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
            String comentario = (String) body.get("comentario");
            int calificacion = body.get("calificacion") == null ? 0 : ((Long)body.get("calificacion")).intValue();
            int satisfaccion = body.get("satisfaccion") == null ? 0 : ((Long)body.get("satisfaccion")).intValue();
            String fecha = (String) body.get("fecha");

            try (Connection conn = Database.getConnection()) {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO opiniones (id_pedido_cliente, comentario, calificacion, satisfaccion, fecha) VALUES (?, ?, ?, ?, ?)");
                ps.setInt(1, id_pedido_cliente);
                ps.setString(2, comentario);
                ps.setInt(3, calificacion);
                ps.setInt(4, satisfaccion);
                ps.setTimestamp(5, DateUtils.parseTimestamp(fecha));
                ps.executeUpdate();
                out.print("{\"ok\": true}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            try { if (resp.getWriter() != null) resp.getWriter().print("{\"ok\": false}"); } catch (Exception ex) {}
        }
    }
}
