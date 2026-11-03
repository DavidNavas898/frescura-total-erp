package com.frescuratotal.erp.inventory.model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.frescuratotal.erp.common.DatabaseConnection;

public class InspectionDao {
    public int registrarInspeccion(Inspection inspeccion) throws SQLException{
        String sql = "INSERT INTO inspecciones_calidad (id_lote, resultado, nombre_inspector, motivo_rechazo,notas) VALUES (?, ?, ?,?,?)";
        try(Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, inspeccion.getIdLote());
            stmt.setString(2, inspeccion.getResultado());
            stmt.setString(3, inspeccion.getNombreInspector());
            stmt.setString(4, inspeccion.getMotivoRechazo());
            stmt.setString(5, inspeccion.getNotas());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

}
