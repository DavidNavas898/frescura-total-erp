package com.frescuratotal.erp.sales.controller;

import com.frescuratotal.erp.inventory.model.Lot;
import com.frescuratotal.erp.inventory.model.LotDao;
import com.frescuratotal.erp.inventory.model.Product;
import com.frescuratotal.erp.inventory.model.ProductDao;
import com.frescuratotal.erp.sales.model.Customer;
import com.frescuratotal.erp.sales.model.CustomerDao;
import com.frescuratotal.erp.sales.model.Sale;
import com.frescuratotal.erp.sales.model.SaleDao;
import com.frescuratotal.erp.sales.model.SaleDetail;
import com.frescuratotal.erp.sales.view.SaleView;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SaleController {

    private static final double DESCUENTO_PROXIMO_A_VENCER = 0.50;
    private static final double TASA_IVA = 0.19;

    private SaleDao saleDao;
    private SaleView saleView;
    private CustomerDao customerDao;
    private ProductDao productDao;
    private LotDao lotDao;

    public SaleController(SaleDao saleDao, SaleView saleView, CustomerDao customerDao, ProductDao productDao,
            LotDao lotDao) {
        this.saleDao = saleDao;
        this.saleView = saleView;
        this.customerDao = customerDao;
        this.productDao = productDao;
        this.lotDao = lotDao;
    }

    public void iniciar() {
        int opcion;
        do {
            opcion = saleView.mostrarMenu();
            switch (opcion) {
                case 1:
                    nuevaVenta();
                    break;
                case 0:
                    saleView.mostrarMensaje("Regresando al menu principal...");
                    break;
                default:
                    saleView.mostrarMensaje("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    private void nuevaVenta() {
        try {
            List<Customer> clientes = customerDao.obtenerClientes();
            if (clientes.isEmpty()) {
                saleView.mostrarMensaje("No hay clientes registrados. Primero registre un cliente.");
                return;
            }
            saleView.mostrarClientes(clientes);
            int idCliente = saleView.pedirIdCliente();

            boolean clienteExiste = false;
            for (Customer cliente : clientes) {
                if (cliente.getIdCliente() == idCliente) {
                    clienteExiste = true;
                }
            }
            if (!clienteExiste) {
                saleView.mostrarMensaje("El cliente no existe. Venta cancelada.");
                return;
            }

            Sale venta = new Sale();
            venta.setIdCliente(idCliente);

            List<Product> productos = productDao.obtenerProductos();
            int idProducto;
            do {
                saleView.mostrarProductos(productos);
                idProducto = saleView.pedirIdProducto();
                if (idProducto != 0) {
                    agregarProducto(venta, idProducto);
                }
            } while (idProducto != 0);

            if (venta.getDetalles().isEmpty()) {
                saleView.mostrarMensaje("El pedido esta vacio. Venta cancelada.");
                return;
            }

            calcularTotales(venta);
            saleView.mostrarResumen(venta);

            if (!saleView.confirmar("Confirmar la venta?")) {
                saleView.mostrarMensaje("Venta cancelada.");
                return;
            }

            // 4. Guardar todo en una transaccion
            LocalDateTime ahora = LocalDateTime.now();
            venta.setNumeroVenta("V-" + ahora.format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")));
            venta.setIdEstado(1);
            venta.setUsuarioRegistro(saleView.pedirVendedor());

            int idVenta = saleDao.registrarVenta(venta);
            saleView.mostrarMensaje("Venta " + venta.getNumeroVenta() + " registrada con exito (ID " + idVenta + ").");
        } catch (SQLException e) {
            saleView.mostrarMensaje("Error al registrar la venta: " + e.getMessage());
        }
    }

    private void agregarProducto(Sale venta, int idProducto) throws SQLException {
        List<Lot> lotes = lotDao.obtenerLotesDisponiblesPorProducto(idProducto);
        if (lotes.isEmpty()) {
            saleView.mostrarMensaje("No hay stock disponible de ese producto.");
            return;
        }

        double stockTotal = 0;
        for (Lot lote : lotes) {
            lote.setCantidad(lote.getCantidad() - cantidadYaEnPedido(venta, lote.getIdLote()));
            stockTotal = stockTotal + lote.getCantidad();
        }

        saleView.mostrarLotesDisponibles(lotes);
        double cantidad = saleView.pedirCantidad();
        if (cantidad <= 0) {
            saleView.mostrarMensaje("La cantidad debe ser mayor que 0.");
            return;
        }
        if (cantidad > stockTotal) {
            saleView.mostrarMensaje("Stock insuficiente. Solo hay " + stockTotal + " disponibles.");
            return;
        }

       
        double restante = cantidad;
        for (Lot lote : lotes) {
            if (restante <= 0) {
                break;
            }
            if (lote.getCantidad() <= 0) {
                continue;
            }

            double tomar;
            if (restante <= lote.getCantidad()) {
                tomar = restante;
            } else {
                tomar = lote.getCantidad();
            }

            venta.agregarDetalle(crearDetalle(lote, tomar));
            restante = restante - tomar;
        }
        saleView.mostrarMensaje("Producto agregado al pedido.");
    }

    private SaleDetail crearDetalle(Lot lote, double cantidad) {
        SaleDetail detalle = new SaleDetail();
        detalle.setIdLote(lote.getIdLote());
        detalle.setNumeroLote(lote.getNumeroLote());
        detalle.setNombreProducto(lote.getNombreProducto());
        detalle.setCantidadVendida(cantidad);
        detalle.setPrecioUnitario(lote.getPrecioVenta());

        double subtotal = redondear(cantidad * lote.getPrecioVenta());

        double descuento = 0;
        if (lote.getIdEstado() == 2) {
            descuento = DESCUENTO_PROXIMO_A_VENCER;
        }
        double montoDescuento = redondear(subtotal * descuento);
        double montoImpuesto = redondear((subtotal - montoDescuento) * TASA_IVA);
        double totalLinea = redondear(subtotal - montoDescuento + montoImpuesto);

        detalle.setSubtotal(subtotal);
        detalle.setDescuentoAplicado(descuento);
        detalle.setMontoDescuento(montoDescuento);
        detalle.setTasaImpuesto(TASA_IVA);
        detalle.setMontoImpuesto(montoImpuesto);
        detalle.setTotalLinea(totalLinea);
        return detalle;
    }

    private double cantidadYaEnPedido(Sale venta, int idLote) {
        double cantidad = 0;
        for (SaleDetail detalle : venta.getDetalles()) {
            if (detalle.getIdLote() == idLote) {
                cantidad = cantidad + detalle.getCantidadVendida();
            }
        }
        return cantidad;
    }

    private void calcularTotales(Sale venta) {
        double subtotal = 0;
        double totalDescuento = 0;
        double totalImpuesto = 0;
        double montoTotal = 0;
        for (SaleDetail detalle : venta.getDetalles()) {
            subtotal = subtotal + detalle.getSubtotal();
            totalDescuento = totalDescuento + detalle.getMontoDescuento();
            totalImpuesto = totalImpuesto + detalle.getMontoImpuesto();
            montoTotal = montoTotal + detalle.getTotalLinea();
        }
        venta.setSubtotal(redondear(subtotal));
        venta.setTotalDescuento(redondear(totalDescuento));
        venta.setTotalImpuesto(redondear(totalImpuesto));
        venta.setMontoTotal(redondear(montoTotal));
    }

    private double redondear(double valor) {
        return Math.round(valor * 100) / 100.0;
    }
}
