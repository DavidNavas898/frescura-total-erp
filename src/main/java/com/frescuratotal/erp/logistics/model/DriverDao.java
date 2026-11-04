package com.frescuratotal.erp.logistics.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.frescuratotal.erp.common.DatabaseConnection;

public class DriverDao {

    public List<Driver> obtenerConductores() throws SQLException {
        List<Driver> conductores = new ArrayList<>();
        String sql = "SELECT * FROM conductores ORDER BY nombre";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Driver conductor = new Driver();
                conductor.setIdConductor(rs.getInt("id_conductor"));
                conductor.setNombre(rs.getString("nombre"));
                conductor.setNumeroLicencia(rs.getString("numero_licencia"));
                conductor.setTelefono(rs.getString("telefono"));
                conductor.setEmail(rs.getString("email"));
                conductor.setActivo(rs.getBoolean("activo"));
                conductores.add(conductor);
            }
        }
        return conductores;
    }

    public void registrarConductor(Driver conductor) throws SQLException {
        String sql = "INSERT INTO conductores (nombre, numero_licencia, telefono, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, conductor.getNombre());
            stmt.setString(2, conductor.getNumeroLicencia());
            stmt.setString(3, conductor.getTelefono());
            stmt.setString(4, conductor.getEmail());
            stmt.executeUpdate();
        }
    }
}
