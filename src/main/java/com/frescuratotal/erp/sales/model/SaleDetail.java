package com.frescuratotal.erp.sales.model;

public class SaleDetail {

    private int idDetalle;
    private int idVenta;
    private int idLote;
    private double cantidadVendida;
    private double precioUnitario;
    private double descuentoAplicado;
    private double montoDescuento;
    private double tasaImpuesto;
    private double montoImpuesto;
    private double subtotal;
    private double totalLinea;
    private String numeroLote;
    private String nombreProducto;

    public SaleDetail() {
    }

    public SaleDetail(int idDetalle, int idVenta, int idLote, double cantidadVendida, double precioUnitario,
            double descuentoAplicado, double montoDescuento, double tasaImpuesto, double montoImpuesto,
            double subtotal, double totalLinea, String numeroLote, String nombreProducto) {
        this.idDetalle = idDetalle;
        this.idVenta = idVenta;
        this.idLote = idLote;
        this.cantidadVendida = cantidadVendida;
        this.precioUnitario = precioUnitario;
        this.descuentoAplicado = descuentoAplicado;
        this.montoDescuento = montoDescuento;
        this.tasaImpuesto = tasaImpuesto;
        this.montoImpuesto = montoImpuesto;
        this.subtotal = subtotal;
        this.totalLinea = totalLinea;
        this.numeroLote = numeroLote;
        this.nombreProducto = nombreProducto;
    }

    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdLote() {
        return idLote;
    }

    public void setIdLote(int idLote) {
        this.idLote = idLote;
    }

    public double getCantidadVendida() {
        return cantidadVendida;
    }

    public void setCantidadVendida(double cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getDescuentoAplicado() {
        return descuentoAplicado;
    }

    public void setDescuentoAplicado(double descuentoAplicado) {
        this.descuentoAplicado = descuentoAplicado;
    }

    public double getMontoDescuento() {
        return montoDescuento;
    }

    public void setMontoDescuento(double montoDescuento) {
        this.montoDescuento = montoDescuento;
    }

    public double getTasaImpuesto() {
        return tasaImpuesto;
    }

    public void setTasaImpuesto(double tasaImpuesto) {
        this.tasaImpuesto = tasaImpuesto;
    }

    public double getMontoImpuesto() {
        return montoImpuesto;
    }

    public void setMontoImpuesto(double montoImpuesto) {
        this.montoImpuesto = montoImpuesto;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getTotalLinea() {
        return totalLinea;
    }

    public void setTotalLinea(double totalLinea) {
        this.totalLinea = totalLinea;
    }

    public String getNumeroLote() {
        return numeroLote;
    }

    public void setNumeroLote(String numeroLote) {
        this.numeroLote = numeroLote;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }
}
