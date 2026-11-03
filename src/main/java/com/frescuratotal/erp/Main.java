package com.frescuratotal.erp;

import com.frescuratotal.erp.common.view.MainView;
import com.frescuratotal.erp.inventory.controller.ProductController;
import com.frescuratotal.erp.inventory.model.ProductDao;
import com.frescuratotal.erp.inventory.view.ProductView;
import com.frescuratotal.erp.suppliers.controller.SupplierController;
import com.frescuratotal.erp.suppliers.model.SupplierDao;
import com.frescuratotal.erp.suppliers.view.SupplierView;
import com.frescuratotal.erp.inventory.controller.LotController;
import com.frescuratotal.erp.inventory.model.LotDao;
import com.frescuratotal.erp.inventory.model.QuarantineDao;
import com.frescuratotal.erp.inventory.view.LotView;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MainView mainView = new MainView(scanner);
        SupplierController supplierController =
                new SupplierController(new SupplierDao(), new ProductDao(), new SupplierView(scanner));
        ProductController productController =
                new ProductController(new ProductDao(), new ProductView(scanner));
        LotController lotController =
                new LotController(new LotDao(), new LotView(scanner), new ProductDao(), new SupplierDao(),
                        new QuarantineDao());
        int opcion;
        do {
            opcion = mainView.mostrarMenu();
            switch (opcion) {
                case 1:
                    supplierController.iniciar();
                    break;
                case 2:
                    productController.iniciar();
                    break;
                case 3:
                    lotController.iniciar();
                    break;
                case 0:
                    mainView.mostrarMensaje("Hasta pronto.");
                    break;
                default:
                    mainView.mostrarMensaje("Opcion invalida.");
            }
        } while (opcion != 0);

        scanner.close();
    }
}
