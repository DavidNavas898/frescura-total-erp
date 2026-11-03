package com.frescuratotal.erp.inventory.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.frescuratotal.erp.common.DatabaseConnection;

public class QuarantineDao {

    public void registrarCuarentena(Quarantine cuarentena) throws SQLException {
        String sql = "INSERT INTO registro_cuarentena (id_lote, motivo, usuario) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cuarentena.getIdLote());
            stmt.setString(2, cuarentena.getMotivo());
            stmt.setString(3, cuarentena.getUsuario());
            stmt.executeUpdate();
        }
    }
}
