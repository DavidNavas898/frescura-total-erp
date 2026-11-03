package com.frescuratotal.erp.suppliers.view;

import com.frescuratotal.erp.inventory.model.Lot;
import com.frescuratotal.erp.inventory.model.Product;
import com.frescuratotal.erp.suppliers.model.Supplier;
import java.util.List;
import java.util.Scanner;

public class SupplierView {

    private Scanner scanner;

    public SupplierView(Scanner scanner) {
        this.scanner = scanner;
    }

    public int mostrarMenu() {
        System.out.println();
        System.out.println("--- MENU DE PROVEEDORES ---");
        System.out.println("1. Listar proveedores");
        System.out.println("2. Agregar proveedor");
        System.out.println("3. Actualizar proveedor");
        System.out.println("4. Ver catalogo de un proveedor");
        System.out.println("5. Agregar producto al catalogo");
        System.out.println("6. Ver historial de compras");
        System.out.println("0. Volver al menu principal");
        return leerNumero("Seleccione una opcion: ");
    }

    public void mostrarProveedores(List<Supplier> proveedores) {
        System.out.println();
        System.out.println("--- LISTA DE PROVEEDORES ---");
        if (proveedores.isEmpty()) {
            System.out.println("No hay proveedores registrados.");
            return;
        }
        for (Supplier proveedor : proveedores) {
            System.out.println("ID: " + proveedor.getIdProveedor()
                    + " | Nombre: " + proveedor.getNombre()
                    + " | NIT: " + proveedor.getNit()
                    + " | Telefono: " + proveedor.getTelefono()
                    + " | Email: " + proveedor.getEmail());
        }
    }

    public Supplier pedirDatosProveedor() {
        System.out.println();
        System.out.println("--- AGREGAR NUEVO PROVEEDOR ---");
        Supplier proveedor = new Supplier();

        System.out.print("Nombre del proveedor: ");
        proveedor.setNombre(scanner.nextLine());

        System.out.print("NIT: ");
        proveedor.setNit(scanner.nextLine());

        System.out.print("Persona de contacto: ");
        proveedor.setPersonaContacto(scanner.nextLine());

        System.out.print("Email: ");
        proveedor.setEmail(scanner.nextLine());

        System.out.print("Telefono: ");
        proveedor.setTelefono(scanner.nextLine());

        System.out.print("Direccion: ");
        proveedor.setDireccion(scanner.nextLine());

        proveedor.setActivo(true);
        return proveedor;
    }

    public void pedirCambiosProveedor(Supplier proveedor) {
        System.out.println("Editando proveedor: " + proveedor.getNombre());
        System.out.println("Deje en blanco para mantener el valor actual.");

        System.out.print("Nuevo nombre (" + proveedor.getNombre() + "): ");
        String nombre = scanner.nextLine();
        if (!nombre.isEmpty()) {
            proveedor.setNombre(nombre);
        }

        System.out.print("Nuevo NIT (" + proveedor.getNit() + "): ");
        String nit = scanner.nextLine();
        if (!nit.isEmpty()) {
            proveedor.setNit(nit);
        }

        System.out.print("Nueva persona de contacto (" + proveedor.getPersonaContacto() + "): ");
        String personaContacto = scanner.nextLine();
        if (!personaContacto.isEmpty()) {
            proveedor.setPersonaContacto(personaContacto);
        }

        System.out.print("Nuevo email (" + proveedor.getEmail() + "): ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) {
            proveedor.setEmail(email);
        }

        System.out.print("Nuevo telefono (" + proveedor.getTelefono() + "): ");
        String telefono = scanner.nextLine();
        if (!telefono.isEmpty()) {
            proveedor.setTelefono(telefono);
        }

        System.out.print("Nueva direccion (" + proveedor.getDireccion() + "): ");
        String direccion = scanner.nextLine();
        if (!direccion.isEmpty()) {
            proveedor.setDireccion(direccion);
        }
    }

    public void mostrarCatalogo(String nombreProveedor, List<Product> productos) {
        System.out.println();
        System.out.println("--- CATALOGO DE " + nombreProveedor + " ---");
        if (productos.isEmpty()) {
            System.out.println("Este proveedor no tiene productos en su catalogo.");
            return;
        }
        for (Product producto : productos) {
            System.out.println("ID: " + producto.getIdProducto()
                    + " | Nombre: " + producto.getNombre()
                    + " | Categoria: " + producto.getNombreCategoria()
                    + " | Unidad: " + producto.getUnidadMedida());
        }
    }

    public void mostrarHistorial(String nombreProveedor, List<Lot> lotes) {
        System.out.println();
        System.out.println("--- HISTORIAL DE COMPRAS DE " + nombreProveedor + " ---");
        if (lotes.isEmpty()) {
            System.out.println("Este proveedor no tiene entregas registradas");
            return;
        }
        for (Lot lote : lotes) {
            System.out.println("Lote: " + lote.getNumeroLote()
                    + " | Producto: " + lote.getNombreProducto()
                    + " | Ingreso: " + lote.getFechaIngreso()
                    + " | Cantidad recibida: " + lote.getCantidadInicial()
                    + " | Estado: " + lote.getNombreEstado());
        }
    }

    public void mostrarProductosDisponibles(List<Product> productos) {
        System.out.println();
        System.out.println("--- PRODUCTOS DISPONIBLES ---");
        for (Product producto : productos) {
            System.out.println(producto.getIdProducto() + " - " + producto.getNombre()
                    + " (" + producto.getNombreCategoria() + ")");
        }
    }

    public int pedirId() {
        return leerNumero("Ingrese el ID del proveedor: ");
    }

    public int pedirIdProducto() {
        return leerNumero("Ingrese el ID del producto: ");
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    private int leerNumero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine();
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un numero.");
            }
        }
    }
}
