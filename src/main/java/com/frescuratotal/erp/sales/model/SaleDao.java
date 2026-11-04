package com.frescuratotal.erp.sales.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.frescuratotal.erp.common.DatabaseConnection;

public class SaleDao {

    public int registrarVenta(Sale venta) throws SQLException {
        String sqlVenta = "INSERT INTO ventas (numero_venta, id_cliente, subtotal, total_descuento, total_impuesto, "
                + "monto_total, id_estado, usuario_registro) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO detalles_venta (id_venta, id_lote, cantidad_vendida, precio_unitario, "
                + "descuento_aplicado, monto_descuento, tasa_impuesto, monto_impuesto, subtotal, total_linea) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlLote = "UPDATE lotes_inventario SET cantidad = cantidad - ? WHERE id_lote = ? AND cantidad >= ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                int idVenta = -1;
                try (PreparedStatement stmt = conn.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS)) {
                    stmt.setString(1, venta.getNumeroVenta());
                    stmt.setInt(2, venta.getIdCliente());
                    stmt.setDouble(3, venta.getSubtotal());
                    stmt.setDouble(4, venta.getTotalDescuento());
                    stmt.setDouble(5, venta.getTotalImpuesto());
                    stmt.setDouble(6, venta.getMontoTotal());
                    stmt.setInt(7, venta.getIdEstado());
                    stmt.setString(8, venta.getUsuarioRegistro());
                    stmt.executeUpdate();
                    try (ResultSet rs = stmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            idVenta = rs.getInt(1);
                        }
                    }
                }

                for (SaleDetail detalle : venta.getDetalles()) {
                    try (PreparedStatement stmt = conn.prepareStatement(sqlDetalle)) {
                        stmt.setInt(1, idVenta);
                        stmt.setInt(2, detalle.getIdLote());
                        stmt.setDouble(3, detalle.getCantidadVendida());
                        stmt.setDouble(4, detalle.getPrecioUnitario());
                        stmt.setDouble(5, detalle.getDescuentoAplicado());
                        stmt.setDouble(6, detalle.getMontoDescuento());
                        stmt.setDouble(7, detalle.getTasaImpuesto());
                        stmt.setDouble(8, detalle.getMontoImpuesto());
                        stmt.setDouble(9, detalle.getSubtotal());
                        stmt.setDouble(10, detalle.getTotalLinea());
                        stmt.executeUpdate();
                    }

                    try (PreparedStatement stmt = conn.prepareStatement(sqlLote)) {
                        stmt.setDouble(1, detalle.getCantidadVendida());
                        stmt.setInt(2, detalle.getIdLote());
                        stmt.setDouble(3, detalle.getCantidadVendida());
                        int filas = stmt.executeUpdate();
                        if (filas == 0) {
                            throw new SQLException("Stock insuficiente en el lote " + detalle.getNumeroLote());
                        }
                    }
                }

                conn.commit();
                return idVenta;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }
}
