package com.frescuratotal.erp.suppliers.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.frescuratotal.erp.common.DatabaseConnection;
import com.frescuratotal.erp.inventory.model.Product;

public class SupplierDao {
    public List<Supplier> obtenerProveedores() throws SQLException {
        List<Supplier> proveedores = new ArrayList<>();
        String sql = "SELECT * FROM proveedores ORDER BY nombre";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Supplier supplier = new Supplier();
                supplier.setIdProveedor(rs.getInt("id_proveedor"));
                supplier.setNombre(rs.getString("nombre"));
                supplier.setNit(rs.getString("nit"));
                supplier.setPersonaContacto(rs.getString("persona_contacto"));
                supplier.setEmail(rs.getString("email"));
                supplier.setTelefono(rs.getString("telefono"));
                supplier.setDireccion(rs.getString("direccion"));
                supplier.setActivo(rs.getBoolean("activo"));
                supplier.setFechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime());
                proveedores.add(supplier);
            }
        }
        return proveedores;
    }

    public Supplier obtenerProveedorPorId(int id) throws SQLException {
        String sql = "SELECT * FROM proveedores WHERE id_proveedor = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Supplier supplier = new Supplier();
                supplier.setIdProveedor(rs.getInt("id_proveedor"));
                supplier.setNombre(rs.getString("nombre"));
                supplier.setNit(rs.getString("nit"));
                supplier.setPersonaContacto(rs.getString("persona_contacto"));
                supplier.setEmail(rs.getString("email"));
                supplier.setTelefono(rs.getString("telefono"));
                supplier.setDireccion(rs.getString("direccion"));
                supplier.setActivo(rs.getBoolean("activo"));
                supplier.setFechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime());
                return supplier;
            }
        }
        return null;
    }

    public void agregarProveedor(Supplier supplier) throws SQLException {
        String sql = "INSERT INTO proveedores (nombre, nit, persona_contacto, email, telefono, direccion, activo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, supplier.getNombre());
            stmt.setString(2, supplier.getNit());
            stmt.setString(3, supplier.getPersonaContacto());
            stmt.setString(4, supplier.getEmail());
            stmt.setString(5, supplier.getTelefono());
            stmt.setString(6, supplier.getDireccion());
            stmt.setBoolean(7, supplier.isActivo());
            stmt.executeUpdate();
        }
    }
    public void actualizarProveedor(Supplier supplier) throws SQLException {
        String sql = "UPDATE proveedores SET nombre = ?, nit = ?, persona_contacto = ?, email = ?, telefono = ?, direccion = ?, activo = ? WHERE id_proveedor = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, supplier.getNombre());
            stmt.setString(2, supplier.getNit());
            stmt.setString(3, supplier.getPersonaContacto());
            stmt.setString(4, supplier.getEmail());
            stmt.setString(5, supplier.getTelefono());
            stmt.setString(6, supplier.getDireccion());
            stmt.setBoolean(7, supplier.isActivo());
            stmt.setInt(8, supplier.getIdProveedor());
            stmt.executeUpdate();
        }
    }

    public List<Product> obtenerCatalogo(int idProveedor) throws SQLException {
        List<Product> productos = new ArrayList<>();
        String sql = "SELECT p.*, c.nombre AS nombre_categoria "
                + "FROM productos_proveedor pp "
                + "JOIN productos p ON pp.id_producto = p.id_producto "
                + "JOIN categorias_producto c ON p.id_categoria = c.id_categoria "
                + "WHERE pp.id_proveedor = ? "
                + "ORDER BY p.nombre";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProveedor);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product producto = new Product();
                    producto.setIdProducto(rs.getInt("id_producto"));
                    producto.setNombre(rs.getString("nombre"));
                    producto.setDescripcion(rs.getString("descripcion"));
                    producto.setIdCategoria(rs.getInt("id_categoria"));
                    producto.setUnidadMedida(rs.getString("unidad_medida"));
                    producto.setPrecioBase(rs.getDouble("precio_base"));
                    producto.setNombreCategoria(rs.getString("nombre_categoria"));
                    productos.add(producto);
                }
            }
        }
        return productos;
    }

    public void agregarProductoAlCatalogo(int idProveedor, int idProducto) throws SQLException {
        String sql = "INSERT INTO productos_proveedor (id_proveedor, id_producto) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProveedor);
            stmt.setInt(2, idProducto);
            stmt.executeUpdate();
        }
    }
}
