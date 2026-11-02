package com.frescuratotal.erp;

import com.frescuratotal.erp.common.view.MainView;
import com.frescuratotal.erp.inventory.controller.ProductController;
import com.frescuratotal.erp.inventory.model.ProductDao;
import com.frescuratotal.erp.inventory.view.ProductView;
import com.frescuratotal.erp.suppliers.controller.SupplierController;
import com.frescuratotal.erp.suppliers.model.SupplierDao;
import com.frescuratotal.erp.suppliers.view.SupplierView;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MainView mainView = new MainView(scanner);
        SupplierController supplierController =
                new SupplierController(new SupplierDao(), new SupplierView(scanner));
        ProductController productController =
                new ProductController(new ProductDao(), new ProductView(scanner));

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
