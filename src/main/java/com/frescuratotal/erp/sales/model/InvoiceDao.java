package com.frescuratotal.erp.sales.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.frescuratotal.erp.common.DatabaseConnection;

public class InvoiceDao {

    public void registrarFactura(Invoice factura) throws SQLException {
        String sql = "INSERT INTO facturas (id_venta, numero_factura, cufe, enlace_qr, subtotal, "
                + "total_descuento, monto_impuesto, monto_total) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, factura.getIdVenta());
            stmt.setString(2, factura.getNumeroFactura());
            stmt.setString(3, factura.getCufe());
            stmt.setString(4, factura.getEnlaceQr());
            stmt.setDouble(5, factura.getSubtotal());
            stmt.setDouble(6, factura.getTotalDescuento());
            stmt.setDouble(7, factura.getMontoImpuesto());
            stmt.setDouble(8, factura.getMontoTotal());
            stmt.executeUpdate();
        }
    }
}
