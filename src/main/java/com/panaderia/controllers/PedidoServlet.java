package com.panaderia.controllers;

import com.panaderia.utils.Database;
import com.panaderia.utils.DateUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class PedidoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = req.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) sb.append(line);
            }

            JSONObject body = (JSONObject) new JSONParser().parse(sb.toString());
            Integer id_cliente = body.get("id_cliente") == null ? null : ((Long)body.get("id_cliente")).intValue();
            Integer id_proveedor = body.get("id_proveedor") == null ? null : ((Long)body.get("id_proveedor")).intValue();
            int id_empleado = ((Long)body.get("id_empleado")).intValue();
            String fecha = (String) body.get("fecha");
            String estado = (String) body.get("estado");
            JSONArray detalles = (JSONArray) body.get("detalles");

            try (Connection conn = Database.getConnection()) {
                conn.setAutoCommit(false);
                String sql = "INSERT INTO pedidos (id_cliente, id_proveedor, id_empleado, fecha, estado, observaciones) VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
                PreparedStatement ps = conn.prepareStatement(sql);
                if (id_cliente == null) ps.setNull(1, Types.INTEGER); else ps.setInt(1, id_cliente);
                if (id_proveedor == null) ps.setNull(2, Types.INTEGER); else ps.setInt(2, id_proveedor);
                ps.setInt(3, id_empleado);
                ps.setTimestamp(4, DateUtils.parseTimestamp(fecha));
                ps.setString(5, estado);
                ps.setNull(6, Types.VARCHAR);
                ResultSet rs = ps.executeQuery();
                int id_pedido = -1;
                if (rs.next()) id_pedido = rs.getInt(1);

                if (detalles != null) {
                    PreparedStatement psDet = conn.prepareStatement("INSERT INTO pedido_detalles (id_pedido, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)");
                    for (Object o : detalles) {
                        JSONObject det = (JSONObject) o;
                        int id_producto = ((Long)det.get("id_producto")).intValue();
                        int cantidad = ((Long)det.get("cantidad")).intValue();
                        double precio_unitario = det.containsKey("precio_unitario") ? ((Number)det.get("precio_unitario")).doubleValue() : 0.0;
                        psDet.setInt(1, id_pedido);
                        psDet.setInt(2, id_producto);
                        psDet.setInt(3, cantidad);
                        psDet.setDouble(4, precio_unitario);
                        psDet.addBatch();
                    }
                    psDet.executeBatch();
                }
                conn.commit();

                JSONObject res = new JSONObject();
                res.put("id_pedido_cliente", id_pedido);
                out.print(res.toJSONString());
            } catch (Exception e) {
                e.printStackTrace(out);
                out.print("{\"error\": \"Error al insertar pedido\"}");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            resp.getWriter().print("{\"error\": \"JSON inválido\"}");
        }
    }
}
