package com.frescuratotal.erp.suppliers.controller;

import com.frescuratotal.erp.suppliers.model.Supplier;
import com.frescuratotal.erp.suppliers.model.SupplierDao;
import com.frescuratotal.erp.suppliers.view.SupplierView;
import java.sql.SQLException;
import java.util.List;

public class SupplierController {

    private SupplierDao supplierDao;
    private SupplierView supplierView;

    public SupplierController(SupplierDao supplierDao, SupplierView supplierView) {
        this.supplierDao = supplierDao;
        this.supplierView = supplierView;
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
                    desactivarProveedor();
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
        } catch (SQLException e) {
            supplierView.mostrarMensaje("Error al agregar proveedor: " + e.getMessage());
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

    private void desactivarProveedor() {
        int id = supplierView.pedirId();
        try {
            Supplier proveedor = supplierDao.obtenerProveedorPorId(id);
            if (proveedor == null) {
                supplierView.mostrarMensaje("Proveedor no encontrado.");
                return;
            }
            if (!proveedor.isActivo()) {
                supplierView.mostrarMensaje("El proveedor ya esta inactivo.");
                return;
            }
            boolean confirmado = supplierView.confirmar("Desea desactivar a " + proveedor.getNombre() + "?");
            if (confirmado) {
                supplierDao.desactivarProveedor(id);
                supplierView.mostrarMensaje("Proveedor desactivado exitosamente.");
            } else {
                supplierView.mostrarMensaje("Operacion cancelada.");
            }
        } catch (SQLException e) {
            supplierView.mostrarMensaje("Error al desactivar proveedor: " + e.getMessage());
        }
    }
}
