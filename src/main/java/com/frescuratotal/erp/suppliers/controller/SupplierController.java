package com.frescuratotal.erp.suppliers.controller;

import com.frescuratotal.erp.common.LogManager;
import com.frescuratotal.erp.inventory.model.Lot;
import com.frescuratotal.erp.inventory.model.LotDao;
import com.frescuratotal.erp.inventory.model.Product;
import com.frescuratotal.erp.inventory.model.ProductDao;
import com.frescuratotal.erp.suppliers.model.Supplier;
import com.frescuratotal.erp.suppliers.model.SupplierDao;
import com.frescuratotal.erp.suppliers.view.SupplierView;
import java.sql.SQLException;
import java.util.List;

public class SupplierController {

    private SupplierDao supplierDao;
    private ProductDao productDao;
    private SupplierView supplierView;
    private LotDao lotDao;

    public SupplierController(SupplierDao supplierDao, ProductDao productDao, SupplierView supplierView,
            LotDao lotDao) {
        this.supplierDao = supplierDao;
        this.productDao = productDao;
        this.supplierView = supplierView;
        this.lotDao = lotDao;
    }

    public void iniciar() {
        int opcion;
        do {
            opcion = supplierView.mostrarMenu();
            switch (opcion) {
                case 1:
                    listarProveedores();
                    break;
                case 2:
                    agregarProveedor();
                    break;
                case 3:
                    actualizarProveedor();
                    break;
                case 4:
                    verCatalogo();
                    break;
                case 5:
                    agregarProductoAlCatalogo();
                    break;
                case 6:
                    verHistorial();
                    break;
                case 0:
                    supplierView.mostrarMensaje("Regresando al menu principal...");
                    break;
                default:
                    supplierView.mostrarMensaje("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    private void listarProveedores() {
        try {
            List<Supplier> proveedores = supplierDao.obtenerProveedores();
            supplierView.mostrarProveedores(proveedores);
        } catch (SQLException e) {
            supplierView.mostrarMensaje("Error al listar proveedores: " + e.getMessage());
        }
    }

    private void agregarProveedor() {
        Supplier proveedor = supplierView.pedirDatosProveedor();
        try {
            supplierDao.agregarProveedor(proveedor);
            supplierView.mostrarMensaje("Proveedor agregado exitosamente.");
            LogManager.registrar("INFO", "Proveedor agregado: " + proveedor.getNombre());
        } catch (SQLException e) {
            supplierView.mostrarMensaje("Error al agregar proveedor: " + e.getMessage());
            LogManager.registrar("ERROR", "Error al agregar proveedor: " + e.getMessage());
        }
    }

    private void actualizarProveedor() {
        int id = supplierView.pedirId();
        try {
            Supplier proveedor = supplierDao.obtenerProveedorPorId(id);
            if (proveedor == null) {
                supplierView.mostrarMensaje("Proveedor no encontrado.");
                return;
            }
            supplierView.pedirCambiosProveedor(proveedor);
            supplierDao.actualizarProveedor(proveedor);
            supplierView.mostrarMensaje("Proveedor actualizado exitosamente.");
        } catch (SQLException e) {
            supplierView.mostrarMensaje("Error al actualizar proveedor: " + e.getMessage());
        }
    }

    private void verCatalogo() {
        int id = supplierView.pedirId();
        try {
            Supplier proveedor = supplierDao.obtenerProveedorPorId(id);
            if (proveedor == null) {
                supplierView.mostrarMensaje("Proveedor no encontrado.");
                return;
            }
            List<Product> catalogo = supplierDao.obtenerCatalogo(id);
            supplierView.mostrarCatalogo(proveedor.getNombre(), catalogo);
        } catch (SQLException e) {
            supplierView.mostrarMensaje("Error al consultar el catalogo: " + e.getMessage());
        }
    }

    private void verHistorial() {
        int id = supplierView.pedirId();
        try {
            Supplier proveedor = supplierDao.obtenerProveedorPorId(id);
            if (proveedor == null) {
                supplierView.mostrarMensaje("Proveedor no encontrado.");
                return;
            }
            List<Lot> lotes = lotDao.obtenerLotesPorProveedor(id);
            supplierView.mostrarHistorial(proveedor.getNombre(), lotes);
        } catch (SQLException e) {
            supplierView.mostrarMensaje("Error al consultar el historial: " + e.getMessage());
        }
    }

    private void agregarProductoAlCatalogo() {
        int idProveedor = supplierView.pedirId();
        try {
            Supplier proveedor = supplierDao.obtenerProveedorPorId(idProveedor);
            if (proveedor == null) {
                supplierView.mostrarMensaje("Proveedor no encontrado.");
                return;
            }

            List<Product> productos = productDao.obtenerProductos();
            if (productos.isEmpty()) {
                supplierView.mostrarMensaje("No hay productos registrados. Primero agregue productos.");
                return;
            }
            supplierView.mostrarProductosDisponibles(productos);
            int idProducto = supplierView.pedirIdProducto();

            boolean productoExiste = productos.stream()
                    .anyMatch(producto -> producto.getIdProducto() == idProducto);
            if (!productoExiste) {
                supplierView.mostrarMensaje("El producto no existe.");
                return;
            }

            List<Product> catalogo = supplierDao.obtenerCatalogo(idProveedor);
            for (Product producto : catalogo) {
                if (producto.getIdProducto() == idProducto) {
                    supplierView.mostrarMensaje("Ese producto ya esta en el catalogo de " + proveedor.getNombre() + ".");
                    return;
                }
            }

            supplierDao.agregarProductoAlCatalogo(idProveedor, idProducto);
            supplierView.mostrarMensaje("Producto agregado al catalogo de " + proveedor.getNombre() + ".");
        } catch (SQLException e) {
            supplierView.mostrarMensaje("Error al agregar al catalogo: " + e.getMessage());
        }
    }
}
