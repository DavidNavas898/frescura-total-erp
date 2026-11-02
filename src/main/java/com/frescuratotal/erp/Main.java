package com.frescuratotal.erp;

import com.frescuratotal.erp.suppliers.model.Supplier;
import com.frescuratotal.erp.suppliers.model.SupplierDao;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        SupplierDao supplierDao = new SupplierDao();
        try {
            List<Supplier> proveedores = supplierDao.obtenerProveedores();
            System.out.println("Proveedores activos: " + proveedores.size());
            for (Supplier proveedor : proveedores) {
                System.out.println(proveedor);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener proveedores: " + e.getMessage());
        }
    }
}
