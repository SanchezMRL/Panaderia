package com.panaderia.controllers;

import com.panaderia.utils.Database;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/api/pedido/*")
public class PedidoGetServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        String path = req.getPathInfo(); // e.g. "/cliente/123"
        if (path == null || path.split("/").length < 3) {
            out.print("{\"error\":\"Ruta inválida. Use /api/pedido/{cliente|proveedor}/{id}\"}");
            return;
        }

        String[] parts = path.split("/");
        String tipo = parts[1]; // "cliente" o "proveedor"
        String idStr = parts[2];
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            out.print("{\"error\":\"ID inválido\"}");
            return;
        }

        try (Connection conn = Database.getConnection()) {
            String sqlPedido;
            if ("cliente".equalsIgnoreCase(tipo)) {
                sqlPedido =
                        "SELECT p.id_pedido_cliente AS id, p.fecha, p.estado, " +
                        "       e.nombre AS empleado_nombre, c.nombre AS cliente_nombre " +
                        "FROM Pedido_Cliente p " +
                        "LEFT JOIN Empleado e ON p.id_empleado = e.id_empleado " +
                        "LEFT JOIN Cliente c ON p.id_cliente = c.id_cliente " +
                        "WHERE p.id_pedido_cliente = ?";
            } else {
                sqlPedido =
                        "SELECT p.id_pedido_proveedor AS id, p.fecha, p.estado, " +
                        "       e.nombre AS empleado_nombre, pr.nombre AS proveedor_nombre " +
                        "FROM Pedido_Proveedor p " +
                        "LEFT JOIN Empleado e ON p.id_empleado = e.id_empleado " +
                        "LEFT JOIN Proveedor pr ON p.id_proveedor = pr.id_proveedor " +
                        "WHERE p.id_pedido_proveedor = ?";
            }

            PreparedStatement psPedido = conn.prepareStatement(sqlPedido);
            psPedido.setInt(1, id);
            ResultSet rsPedido = psPedido.executeQuery();

            if (!rsPedido.next()) {
                out.print("{\"error\":\"Pedido no encontrado\"}");
                return;
            }

            JSONObject respuesta = new JSONObject();
            JSONObject pedidoJson = new JSONObject();
            pedidoJson.put("id", rsPedido.getInt("id"));
            Timestamp ts = rsPedido.getTimestamp("fecha");
            pedidoJson.put("fecha", ts == null ? null : ts.toString());
            pedidoJson.put("estado", rsPedido.getString("estado"));
            pedidoJson.put("empleado_nombre", rsPedido.getString("empleado_nombre"));

            if ("cliente".equalsIgnoreCase(tipo)) {
                pedidoJson.put("cliente_nombre", rsPedido.getString("cliente_nombre"));
            } else {
                pedidoJson.put("proveedor_nombre", rsPedido.getString("proveedor_nombre"));
            }

            // 2) Detalles del pedido
            String sqlDetalles;
            if ("cliente".equalsIgnoreCase(tipo)) {
                sqlDetalles =
                        "SELECT d.id_producto, d.cantidad, d.precio_unitario, pr.nombre AS producto_nombre " +
                        "FROM Detalle_Pedido_Cliente d " +
                        "LEFT JOIN Producto pr ON d.id_producto = pr.id_producto " +
                        "WHERE d.id_pedido_cliente = ?";
            } else {
                sqlDetalles =
                        "SELECT d.id_producto, d.cantidad, d.costo_unitario AS precio_unitario, pr.nombre AS producto_nombre " +
                        "FROM Detalle_Pedido_Proveedor d " +
                        "LEFT JOIN Producto pr ON d.id_producto = pr.id_producto " +
                        "WHERE d.id_pedido_proveedor = ?";
            }

            PreparedStatement psDet = conn.prepareStatement(sqlDetalles);
            psDet.setInt(1, id);
            ResultSet rsDet = psDet.executeQuery();

            JSONArray detallesArr = new JSONArray();
            while (rsDet.next()) {
                JSONObject det = new JSONObject();
                det.put("id_producto", rsDet.getInt("id_producto"));
                det.put("producto_nombre", rsDet.getString("producto_nombre"));
                det.put("cantidad", rsDet.getInt("cantidad"));
                double precio = rsDet.getDouble("precio_unitario");
                if (rsDet.wasNull()) det.put("precio_unitario", null);
                else det.put("precio_unitario", precio);
                detallesArr.add(det);
            }
            pedidoJson.put("detalles", detallesArr);

            respuesta.put("pedido", pedidoJson);

            // 3) Opinión (solo para cliente)
            if ("cliente".equalsIgnoreCase(tipo)) {
                String sqlOpinion =
                        "SELECT comentario, calificacion, satisfaccion, fecha " +
                        "FROM Opinion_Pedido " +
                        "WHERE id_pedido_cliente = ? " +
                        "ORDER BY fecha DESC LIMIT 1";
                PreparedStatement psOp = conn.prepareStatement(sqlOpinion);
                psOp.setInt(1, id);
                ResultSet rsOp = psOp.executeQuery();
                if (rsOp.next()) {
                    JSONObject op = new JSONObject();
                    op.put("comentario", rsOp.getString("comentario"));
                    op.put("calificacion", rsOp.getInt("calificacion"));
                    op.put("satisfaccion", rsOp.getInt("satisfaccion"));
                    Timestamp tsOp = rsOp.getTimestamp("fecha");
                    op.put("fecha", tsOp == null ? null : tsOp.toString());
                    respuesta.put("opinion", op);
                }
            }

            out.print(respuesta.toJSONString());

        } catch (Exception ex) {
            ex.printStackTrace();
            out.print("{\"error\":\"Error en el servidor: " + ex.getMessage().replace("\"", "") + "\"}");
        }
    }
}
