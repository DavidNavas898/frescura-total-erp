package com.frescuratotal.erp.inventory.view;

import com.frescuratotal.erp.inventory.model.Category;
import com.frescuratotal.erp.inventory.model.Product;
import java.util.List;
import java.util.Scanner;

public class ProductView {

    private Scanner scanner;

    public ProductView(Scanner scanner) {
        this.scanner = scanner;
    }

    public int mostrarMenu() {
        System.out.println();
        System.out.println("--- MENU DE PRODUCTOS ---");
        System.out.println("1. Listar productos");
        System.out.println("2. Agregar producto");
        System.out.println("0. Volver al menu principal");
        return leerNumero("Seleccione una opcion: ");
    }

    public void mostrarProductos(List<Product> productos) {
        System.out.println();
        System.out.println("--- LISTA DE PRODUCTOS ---");
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }
        for (Product producto : productos) {
            System.out.println("ID: " + producto.getIdProducto()
                    + " | Nombre: " + producto.getNombre()
                    + " | Categoria: " + producto.getNombreCategoria()
                    + " | Unidad: " + producto.getUnidadMedida()
                    + " | Precio base: " + producto.getPrecioBase());
        }
    }

    public void mostrarCategorias(List<Category> categorias) {
        System.out.println();
        System.out.println("--- CATEGORIAS ---");
        for (Category categoria : categorias) {
            System.out.println(categoria.getIdCategoria() + " - " + categoria.getNombre());
        }
    }

    public Product pedirDatosProducto() {
        System.out.println();
        System.out.println("--- AGREGAR NUEVO PRODUCTO ---");
        Product producto = new Product();

        System.out.print("Nombre del producto: ");
        producto.setNombre(scanner.nextLine());

        System.out.print("Descripcion: ");
        producto.setDescripcion(scanner.nextLine());

        System.out.print("Unidad de medida (kg, litro, unidad): ");
        producto.setUnidadMedida(scanner.nextLine());

        producto.setIdCategoria(leerNumero("ID de la categoria: "));
        producto.setPrecioBase(leerDecimal("Precio base: "));

        return producto;
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
}
