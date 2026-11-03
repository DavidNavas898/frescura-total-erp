package com.frescuratotal.erp.inventory.controller;

import com.frescuratotal.erp.inventory.model.Category;
import com.frescuratotal.erp.inventory.model.Lot;
import com.frescuratotal.erp.inventory.model.LotDao;
import com.frescuratotal.erp.inventory.model.Product;
import com.frescuratotal.erp.inventory.model.ProductDao;
import com.frescuratotal.erp.inventory.view.LotView;
import com.frescuratotal.erp.suppliers.model.Supplier;
import com.frescuratotal.erp.suppliers.model.SupplierDao;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LotController {

    private LotDao lotDao;
    private LotView lotView;
    private ProductDao productDao;
    private SupplierDao supplierDao;


    public LotController(LotDao lotDao, LotView lotView, ProductDao productDao, SupplierDao supplierDao) {
        this.lotDao = lotDao;
        this.lotView = lotView;
        this.productDao = productDao;
        this.supplierDao = supplierDao;
    }

    public void iniciar(){
        int opcion;
        do {
            opcion = lotView.mostrarMenu();
            switch (opcion) {
                case 1:
                    listarLotes();
                    break;
                case 2:
                    registrarLote();
                    break;
                case 0:
                    lotView.mostrarMensaje("Regresando al menu principal...");
                    break;
                default:
                    lotView.mostrarMensaje("Opcion invalida.");
            }
        } while (opcion != 0);
    }
    

      private void listarLotes() {
        try {
            List<Lot> lotes = lotDao.obtenerLotes();
            lotView.mostrarLotes(lotes);
        } catch (SQLException e) {
            lotView.mostrarMensaje("Error al listar los lotes: " + e.getMessage());
        }
    }

    private void registrarLote(){
        try{
            List<Product> productos = productDao.obtenerProductos();
            lotView.mostrarProductos(productos);

            List<Supplier> proveedores = supplierDao.obtenerProveedores();
            lotView.mostrarProveedores(proveedores);

            Lot lote = lotView.pedirDatosLote();

            LocalDateTime ahora = LocalDateTime.now();
            String numeroLote = "L-" + ahora.format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
            lote.setNumeroLote(numeroLote);

            lote.setIdEstado(1);

            lotDao.registrarLote(lote);
            lotView.mostrarMensaje("Lote registrado correctamente con numero: " + numeroLote);
        } catch (SQLException e) {
            lotView.mostrarMensaje("Error al registrar el lote: " + e.getMessage());
        }
    }

    

}
