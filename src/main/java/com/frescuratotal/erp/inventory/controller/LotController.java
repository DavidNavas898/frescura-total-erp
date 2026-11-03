package com.frescuratotal.erp.inventory.controller;

import com.frescuratotal.erp.inventory.model.Lot;
import com.frescuratotal.erp.inventory.model.LotDao;
import com.frescuratotal.erp.inventory.model.Product;
import com.frescuratotal.erp.inventory.model.ProductDao;
import com.frescuratotal.erp.inventory.model.Quarantine;
import com.frescuratotal.erp.inventory.model.QuarantineDao;
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
    private QuarantineDao quarantineDao;


    public LotController(LotDao lotDao, LotView lotView, ProductDao productDao, SupplierDao supplierDao,
            QuarantineDao quarantineDao) {
        this.lotDao = lotDao;
        this.lotView = lotView;
        this.productDao = productDao;
        this.supplierDao = supplierDao;
        this.quarantineDao = quarantineDao;
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
                case 3:
                    buscarLote();
                    break;
                case 4:
                    revisarVencimientos();
                    break;
                case 5:
                    ponerEnCuarentena();
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

    private void buscarLote() {
        try {
            String numeroLote = lotView.pedirNumeroLote();
            Lot lote = lotDao.obtenerLotePorNumero(numeroLote);
            if (lote == null) {
                lotView.mostrarMensaje("No se encontro ningun lote con el numero " + numeroLote);
            } else {
                lotView.mostrarDetalleLote(lote);
            }
        } catch (SQLException e) {
            lotView.mostrarMensaje("Error al buscar el lote: " + e.getMessage());
        }
    }

    private void revisarVencimientos() {
        try {
            int actualizados = lotDao.actualizarEstadosPorVencimiento();
            lotView.mostrarMensaje("Revision completada. Se actualizaron " + actualizados + " lotes.");
        } catch (SQLException e) {
            lotView.mostrarMensaje("Error al revisar vencimientos: " + e.getMessage());
        }
    }

    private void ponerEnCuarentena() {
        try {
            String numeroLote = lotView.pedirNumeroLote();
            Lot lote = lotDao.obtenerLotePorNumero(numeroLote);
            if (lote == null) {
                lotView.mostrarMensaje("No se encontro ningun lote con el numero " + numeroLote);
                return;
            }
            if (lote.getIdEstado() == 3 || lote.getIdEstado() == 4) {
                lotView.mostrarMensaje("No se puede poner en cuarentena un lote en estado " + lote.getNombreEstado() + ".");
                return;
            }

            Quarantine cuarentena = lotView.pedirDatosCuarentena();
            cuarentena.setIdLote(lote.getIdLote());

            quarantineDao.registrarCuarentena(cuarentena);
            lotDao.cambiarEstado(lote.getIdLote(), 4);
            lotView.mostrarMensaje("El lote " + numeroLote + " quedo En Cuarentena y bloqueado para la venta.");
        } catch (SQLException e) {
            lotView.mostrarMensaje("Error al poner el lote en cuarentena: " + e.getMessage());
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
