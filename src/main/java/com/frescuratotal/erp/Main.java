package com.frescuratotal.erp;

import com.frescuratotal.erp.suppliers.controller.SupplierController;
import com.frescuratotal.erp.suppliers.model.SupplierDao;
import com.frescuratotal.erp.suppliers.view.SupplierView;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SupplierController supplierController =
                new SupplierController(new SupplierDao(), new SupplierView(scanner));
        supplierController.iniciar();
    }
}
