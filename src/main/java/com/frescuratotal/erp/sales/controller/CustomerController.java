package com.frescuratotal.erp.sales.controller;

import com.frescuratotal.erp.common.LogManager;
import com.frescuratotal.erp.sales.model.Customer;
import com.frescuratotal.erp.sales.model.CustomerDao;
import com.frescuratotal.erp.sales.view.CustomerView;
import java.sql.SQLException;
import java.util.List;

public class CustomerController {

    private CustomerDao customerDao;
    private CustomerView customerView;

    public CustomerController(CustomerDao customerDao, CustomerView customerView) {
        this.customerDao = customerDao;
        this.customerView = customerView;
    }

    public void iniciar() {
        int opcion;
        do {
            opcion = customerView.mostrarMenu();
            switch (opcion) {
                case 1:
                    listarClientes();
                    break;
                case 2:
                    registrarCliente();
                    break;
                case 0:
                    customerView.mostrarMensaje("Regresando al menu principal...");
                    break;
                default:
                    customerView.mostrarMensaje("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    private void listarClientes() {
        try {
            List<Customer> clientes = customerDao.obtenerClientes();
            customerView.mostrarClientes(clientes);
        } catch (SQLException e) {
            customerView.mostrarMensaje("Error al listar clientes: " + e.getMessage());
        }
    }

    private void registrarCliente() {
        Customer cliente = customerView.pedirDatosCliente();

        if (cliente.getNombre().isEmpty() || cliente.getDocumento().isEmpty()) {
            customerView.mostrarMensaje("El nombre y el documento son obligatorios. Cliente no registrado.");
            return;
        }

        try {
            customerDao.registrarCliente(cliente);
            customerView.mostrarMensaje("Cliente registrado exitosamente.");
            LogManager.registrar("INFO", "Cliente registrado: " + cliente.getNombre());
        } catch (SQLException e) {
            customerView.mostrarMensaje("Error al registrar cliente: " + e.getMessage());
            LogManager.registrar("ERROR", "Error al registrar cliente: " + e.getMessage());
        }
    }
}
