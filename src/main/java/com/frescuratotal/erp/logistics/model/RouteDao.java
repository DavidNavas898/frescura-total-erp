package com.frescuratotal.erp.logistics.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.frescuratotal.erp.common.DatabaseConnection;

public class RouteDao {

    // Panel de despachos: ventas "En Preparacion" (estado 2) que aun no estan en ninguna ruta
    public List<DeliveryOrder> obtenerVentasEnPreparacion() throws SQLException {
        List<DeliveryOrder> pedidos = new ArrayList<>();
        String sql = "SELECT v.id_venta, v.numero_venta, v.id_estado, c.nombre AS nombre_cliente, "
                + "c.direccion, e.nombre AS nombre_estado "
                + "FROM ventas v "
                + "JOIN clientes c ON v.id_cliente = c.id_cliente "
                + "JOIN estados_venta e ON v.id_estado = e.id_estado "
                + "WHERE v.id_estado = 2 "
                + "AND v.id_venta NOT IN (SELECT id_venta FROM ventas_ruta) "
                + "ORDER BY v.fecha_venta";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                DeliveryOrder pedido = new DeliveryOrder();
                pedido.setIdVenta(rs.getInt("id_venta"));
                pedido.setNumeroVenta(rs.getString("numero_venta"));
                pedido.setIdEstadoVenta(rs.getInt("id_estado"));
                pedido.setNombreCliente(rs.getString("nombre_cliente"));
                pedido.setDireccionDestino(rs.getString("direccion"));
                pedido.setNombreEstadoVenta(rs.getString("nombre_estado"));
                pedidos.add(pedido);
            }
        }
        return pedidos;
    }

    // Crea la ruta, su asignacion (vehiculo + conductor) y los pedidos, todo en una transaccion
    public int crearRuta(DeliveryRoute ruta) throws SQLException {
        String sqlRuta = "INSERT INTO rutas_entrega (nombre_ruta, usuario_creacion) VALUES (?, ?)";
        String sqlAsignacion = "INSERT INTO asignaciones_entrega (id_ruta, id_vehiculo, id_conductor, "
                + "usuario_asignacion) VALUES (?, ?, ?, ?)";
        String sqlPedido = "INSERT INTO ventas_ruta (id_ruta, id_venta, orden_entrega, direccion_destino) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                int idRuta = -1;
                try (PreparedStatement stmt = conn.prepareStatement(sqlRuta, Statement.RETURN_GENERATED_KEYS)) {
                    stmt.setString(1, ruta.getNombreRuta());
                    stmt.setString(2, ruta.getUsuarioCreacion());
                    stmt.executeUpdate();
                    try (ResultSet rs = stmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            idRuta = rs.getInt(1);
                        }
                    }
                }

                try (PreparedStatement stmt = conn.prepareStatement(sqlAsignacion)) {
                    stmt.setInt(1, idRuta);
                    stmt.setInt(2, ruta.getIdVehiculo());
                    stmt.setInt(3, ruta.getIdConductor());
                    stmt.setString(4, ruta.getUsuarioCreacion());
                    stmt.executeUpdate();
                }

                for (DeliveryOrder pedido : ruta.getPedidos()) {
                    try (PreparedStatement stmt = conn.prepareStatement(sqlPedido)) {
                        stmt.setInt(1, idRuta);
                        stmt.setInt(2, pedido.getIdVenta());
                        stmt.setInt(3, pedido.getOrdenEntrega());
                        stmt.setString(4, pedido.getDireccionDestino());
                        stmt.executeUpdate();
                    }
                }

                conn.commit();
                return idRuta;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public List<DeliveryRoute> obtenerRutas() throws SQLException {
        List<DeliveryRoute> rutas = new ArrayList<>();
        String sql = "SELECT r.*, a.id_vehiculo, a.id_conductor, v.placa, c.nombre AS nombre_conductor "
                + "FROM rutas_entrega r "
                + "JOIN asignaciones_entrega a ON r.id_ruta = a.id_ruta "
                + "JOIN vehiculos v ON a.id_vehiculo = v.id_vehiculo "
                + "JOIN conductores c ON a.id_conductor = c.id_conductor "
                + "ORDER BY r.fecha_creacion DESC";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                DeliveryRoute ruta = new DeliveryRoute();
                ruta.setIdRuta(rs.getInt("id_ruta"));
                ruta.setNombreRuta(rs.getString("nombre_ruta"));
                ruta.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
                ruta.setUsuarioCreacion(rs.getString("usuario_creacion"));
                ruta.setHoraSalidaEstimada(rs.getTimestamp("hora_salida_estimada"));
                ruta.setHoraLlegadaEstimada(rs.getTimestamp("hora_llegada_estimada"));
                ruta.setHoraSalidaReal(rs.getTimestamp("hora_salida_real"));
                ruta.setHoraLlegadaReal(rs.getTimestamp("hora_llegada_real"));
                ruta.setEstado(rs.getString("estado"));
                ruta.setIdVehiculo(rs.getInt("id_vehiculo"));
                ruta.setIdConductor(rs.getInt("id_conductor"));
                ruta.setPlacaVehiculo(rs.getString("placa"));
                ruta.setNombreConductor(rs.getString("nombre_conductor"));
                rutas.add(ruta);
            }
        }
        return rutas;
    }

    public List<DeliveryOrder> obtenerPedidosDeRuta(int idRuta) throws SQLException {
        List<DeliveryOrder> pedidos = new ArrayList<>();
        String sql = "SELECT vr.*, v.numero_venta, v.id_estado, c.nombre AS nombre_cliente, "
                + "e.nombre AS nombre_estado "
                + "FROM ventas_ruta vr "
                + "JOIN ventas v ON vr.id_venta = v.id_venta "
                + "JOIN clientes c ON v.id_cliente = c.id_cliente "
                + "JOIN estados_venta e ON v.id_estado = e.id_estado "
                + "WHERE vr.id_ruta = ? "
                + "ORDER BY vr.orden_entrega";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idRuta);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DeliveryOrder pedido = new DeliveryOrder();
                pedido.setId(rs.getInt("id"));
                pedido.setIdRuta(rs.getInt("id_ruta"));
                pedido.setIdVenta(rs.getInt("id_venta"));
                pedido.setOrdenEntrega(rs.getInt("orden_entrega"));
                pedido.setDireccionDestino(rs.getString("direccion_destino"));
                pedido.setFechaEntrega(rs.getTimestamp("fecha_entrega"));
                pedido.setRecibidoPor(rs.getString("recibido_por"));
                pedido.setObservaciones(rs.getString("observaciones"));
                pedido.setNumeroVenta(rs.getString("numero_venta"));
                pedido.setIdEstadoVenta(rs.getInt("id_estado"));
                pedido.setNombreCliente(rs.getString("nombre_cliente"));
                pedido.setNombreEstadoVenta(rs.getString("nombre_estado"));
                pedidos.add(pedido);
            }
        }
        return pedidos;
    }

    // La ruta sale: ruta "En Transito" y todos sus pedidos pasan a "En Transito" (estado 3)
    public void iniciarRuta(int idRuta) throws SQLException {
        String sqlRuta = "UPDATE rutas_entrega SET estado = 'En Transito', hora_salida_real = NOW() "
                + "WHERE id_ruta = ?";
        String sqlVentas = "UPDATE ventas SET id_estado = 3 "
                + "WHERE id_venta IN (SELECT id_venta FROM ventas_ruta WHERE id_ruta = ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement stmt = conn.prepareStatement(sqlRuta)) {
                    stmt.setInt(1, idRuta);
                    stmt.executeUpdate();
                }
                try (PreparedStatement stmt = conn.prepareStatement(sqlVentas)) {
                    stmt.setInt(1, idRuta);
                    stmt.executeUpdate();
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    // Entrega de un pedido. Devuelve true si con esta entrega la ruta quedo completada.
    public boolean registrarEntrega(DeliveryOrder pedido) throws SQLException {
        String sqlPedido = "UPDATE ventas_ruta SET fecha_entrega = NOW(), recibido_por = ?, observaciones = ? "
                + "WHERE id_ruta = ? AND id_venta = ?";
        String sqlVenta = "UPDATE ventas SET id_estado = 4 WHERE id_venta = ?";
        String sqlPendientes = "SELECT COUNT(*) AS pendientes FROM ventas_ruta "
                + "WHERE id_ruta = ? AND fecha_entrega IS NULL";
        String sqlCompletar = "UPDATE rutas_entrega SET estado = 'Completada', hora_llegada_real = NOW() "
                + "WHERE id_ruta = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement stmt = conn.prepareStatement(sqlPedido)) {
                    stmt.setString(1, pedido.getRecibidoPor());
                    stmt.setString(2, pedido.getObservaciones());
                    stmt.setInt(3, pedido.getIdRuta());
                    stmt.setInt(4, pedido.getIdVenta());
                    stmt.executeUpdate();
                }
                try (PreparedStatement stmt = conn.prepareStatement(sqlVenta)) {
                    stmt.setInt(1, pedido.getIdVenta());
                    stmt.executeUpdate();
                }

                int pendientes = 0;
                try (PreparedStatement stmt = conn.prepareStatement(sqlPendientes)) {
                    stmt.setInt(1, pedido.getIdRuta());
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            pendientes = rs.getInt("pendientes");
                        }
                    }
                }

                boolean rutaCompletada = false;
                if (pendientes == 0) {
                    try (PreparedStatement stmt = conn.prepareStatement(sqlCompletar)) {
                        stmt.setInt(1, pedido.getIdRuta());
                        stmt.executeUpdate();
                    }
                    rutaCompletada = true;
                }

                conn.commit();
                return rutaCompletada;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }
}
