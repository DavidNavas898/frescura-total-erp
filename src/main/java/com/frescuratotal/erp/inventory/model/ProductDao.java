package com.frescuratotal.erp.inventory.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.frescuratotal.erp.common.DatabaseConnection;

public class ProductDao {
    public List<Product> obtenerProductos() throws SQLException{
        List<Product> productos = new ArrayList<>();
        String sql = "SELECT p.*, c.nombre AS nombre_categoria "
                + "FROM productos p "
                + "JOIN categorias_producto c ON p.id_categoria = c.id_categoria "
                + "ORDER BY p.nombre";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
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
        return productos;
    }

    public void agregarProducto(Product producto) throws SQLException {
        String sql = "INSERT INTO productos (nombre, descripcion, id_categoria, unidad_medida, precio_base) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setInt(3, producto.getIdCategoria());
            stmt.setString(4, producto.getUnidadMedida());
            stmt.setDouble(5, producto.getPrecioBase());
            stmt.executeUpdate();
        }
    }

    public List<Category> obtenerCategorias() throws SQLException {
        List<Category> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categorias_producto ORDER BY nombre";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Category categoria = new Category();
                categoria.setIdCategoria(rs.getInt("id_categoria"));
                categoria.setNombre(rs.getString("nombre"));
                categoria.setDescripcion(rs.getString("descripcion"));
                categorias.add(categoria);
            }
        }
        return categorias;
    }
}
