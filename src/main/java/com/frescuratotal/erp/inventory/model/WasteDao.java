package com.frescuratotal.erp.inventory.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import com.frescuratotal.erp.common.DatabaseConnection;

public class WasteDao {
    public void registrarMerma(Waste merma) throws SQLException{
        String sql = "INSERT INTO registro_mermas (id_lote, id_inspeccion, cantidad_perdida, motivo, usuario_registro) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, merma.getIdLote());
            if (merma.getIdInspeccion() == 0) {
                stmt.setNull(2, Types.INTEGER);
            } else {
                stmt.setInt(2, merma.getIdInspeccion());
            }
            stmt.setDouble(3, merma.getCantidadPerdida());
            stmt.setString(4, merma.getMotivo());
            stmt.setString(5, merma.getUsuarioRegistro());
            stmt.executeUpdate();
        }
    }

}
