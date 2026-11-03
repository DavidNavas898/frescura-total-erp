package com.frescuratotal.erp.inventory.view;



import com.frescuratotal.erp.inventory.model.Lot;
import com.frescuratotal.erp.inventory.model.Product;
import com.frescuratotal.erp.suppliers.model.Supplier;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class LotView {

    private Scanner scanner;

    public LotView(Scanner scanner){
        this.scanner = scanner;
    }

    public int mostrarMenu(){
        System.out.println();
        System.out.println("--- MENU DE LOTES ---");
        System.out.println("1. Listar lotes");
        System.out.println("2. Agregar lotes");
        System.out.println("0. Volver al menu principal");
        return leerNumero("Seleccione una opcion: ");
    }

    public void mostrarLotes(List<Lot> lotes) {
        System.out.println();
        System.out.println("--- LISTA DE LOTES ---");
        if (lotes.isEmpty()) {
            System.out.println("No hay lotes registrados.");
            return;
        }
        for (Lot lote : lotes) {
            System.out.println("Lote: " + lote.getNumeroLote()
                    + " | Producto: " + lote.getNombreProducto()
                    + " | Proveedor: " + lote.getNombreProveedor()
                    + " | Cantidad: " + lote.getCantidad() + " de " + lote.getCantidadInicial()
                    + " | Vence: " + lote.getFechaVencimiento()
                    + " | Ubicacion: " + lote.getPasillo() + "-" + lote.getEstante()
                    + " | Estado: " + lote.getNombreEstado());
        }
    }

    public void mostrarProductos(List<Product> productos) {
        System.out.println();
        System.out.println("--- PRODUCTOS DISPONIBLES ---");
        for (Product producto : productos) {
            System.out.println(producto.getIdProducto() + ". " + producto.getNombre());
        }
    }

    public void mostrarProveedores(List<Supplier> proveedores) {
        System.out.println();
        System.out.println("--- PROVEEDORES ---");
        for (Supplier proveedor : proveedores) {
            System.out.println(proveedor.getIdProveedor() + ". " + proveedor.getNombre());
        }
    }


    public Lot pedirDatosLote() {
        System.out.println();
        System.out.println("--- REGISTRAR RECEPCION DE MERCANCIA ---");
        Lot lote = new Lot();

        lote.setIdProducto(leerNumero("ID del producto: "));
        lote.setIdProveedor(leerNumero("ID del proveedor: "));

        lote.setFechaVencimiento(leerFecha("Fecha de vencimiento (AAAA-MM-DD): "));

        double cantidad = leerDecimal("Cantidad recibida: ");
        lote.setCantidadInicial(cantidad);
        lote.setCantidad(cantidad);

        lote.setPrecioCompra(leerDecimal("Precio de compra: "));
        lote.setPrecioVenta(leerDecimal("Precio de venta: "));

        System.out.print("Pasillo: ");
        lote.setPasillo(scanner.nextLine());

        System.out.print("Estante: ");
        lote.setEstante(scanner.nextLine());

        return lote;
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

    private double leerDecimal(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine();
            try {
                return Double.parseDouble(texto);
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un valor numerico (ej: 2500.50).");
            }
        }
    }

    private Date leerFecha(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine();
            try {
                return Date.valueOf(texto);
            } catch (IllegalArgumentException e) {
                System.out.println("Fecha invalida. Use el formato AAAA-MM-DD (ej: 2026-11-20).");
            }
        }
    }

}
