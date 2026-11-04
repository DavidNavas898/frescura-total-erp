package com.frescuratotal.erp.logistics.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.frescuratotal.erp.common.DatabaseConnection;

public class VehicleDao {

    public List<Vehicle> obtenerVehiculos() throws SQLException {
        List<Vehicle> vehiculos = new ArrayList<>();
        String sql = "SELECT * FROM vehiculos ORDER BY placa";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Vehicle vehiculo = new Vehicle();
                vehiculo.setIdVehiculo(rs.getInt("id_vehiculo"));
                vehiculo.setPlaca(rs.getString("placa"));
                vehiculo.setModelo(rs.getString("modelo"));
                vehiculo.setCapacidad(rs.getDouble("capacidad"));
                vehiculo.setEstado(rs.getString("estado"));
                vehiculos.add(vehiculo);
            }
        }
        return vehiculos;
    }

    public void registrarVehiculo(Vehicle vehiculo) throws SQLException {
        String sql = "INSERT INTO vehiculos (placa, modelo, capacidad) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, vehiculo.getPlaca());
            stmt.setString(2, vehiculo.getModelo());
            stmt.setDouble(3, vehiculo.getCapacidad());
            stmt.executeUpdate();
        }
    }
}
