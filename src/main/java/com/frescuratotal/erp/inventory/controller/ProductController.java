package com.frescuratotal.erp.inventory.controller;

import com.frescuratotal.erp.inventory.model.Category;
import com.frescuratotal.erp.inventory.model.Product;
import com.frescuratotal.erp.inventory.model.ProductDao;
import com.frescuratotal.erp.inventory.view.ProductView;
import java.sql.SQLException;
import java.util.List;

public class ProductController {

    private ProductDao productDao;
    private ProductView productView;

    public ProductController(ProductDao productDao, ProductView productView) {
        this.productDao = productDao;
        this.productView = productView;
    }

    public void iniciar() {
        int opcion;
        do {
            opcion = productView.mostrarMenu();
            switch (opcion) {
                case 1:
                    listarProductos();
                    break;
                case 2:
                    agregarProducto();
                    break;
                case 0:
                    productView.mostrarMensaje("Regresando al menu principal...");
                    break;
                default:
                    productView.mostrarMensaje("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    private void listarProductos() {
        try {
            List<Product> productos = productDao.obtenerProductos();
            productView.mostrarProductos(productos);
        } catch (SQLException e) {
            productView.mostrarMensaje("Error al listar productos: " + e.getMessage());
        }
    }

    private void agregarProducto() {
        try {
            List<Category> categorias = productDao.obtenerCategorias();
            productView.mostrarCategorias(categorias);

            Product producto = productView.pedirDatosProducto();

            boolean categoriaExiste = false;
            for (Category categoria : categorias) {
                if (categoria.getIdCategoria() == producto.getIdCategoria()) {
                    categoriaExiste = true;
                }
            }
            if (!categoriaExiste) {
                productView.mostrarMensaje("La categoria no existe. Producto no agregado.");
                return;
            }

            productDao.agregarProducto(producto);
            productView.mostrarMensaje("Producto agregado exitosamente.");
        } catch (SQLException e) {
            productView.mostrarMensaje("Error al agregar producto: " + e.getMessage());
        }
    }
}
