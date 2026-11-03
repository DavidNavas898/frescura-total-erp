package com.frescuratotal.erp.inventory.model;

import java.sql.Date;

public class Lot {
    private int idLote;
    private String numeroLote;
    private int idProducto;
    private int idProveedor;
    private Date fechaIngreso;
    private Date fechaVencimiento;
    private double cantidadInicial;
    private double cantidad;
    private double precioCompra;
    private double precioVenta;
    private String pasillo;
    private String estante;
    private int idEstado;
    private String nombreProducto;
    private String nombreProveedor;
    private String nombreEstado;

    public Lot() {
    }

    public Lot(int idLote, String numeroLote, int idProducto, int idProveedor, Date fechaIngreso, Date fechaVencimiento,
            double cantidadInicial, double cantidad, double precioCompra, double precioVenta, String pasillo,
            String estante, int idEstado, String nombreProducto, String nombreProveedor, String nombreEstado) {
        this.idLote = idLote;
        this.numeroLote = numeroLote;
        this.idProducto = idProducto;
        this.idProveedor = idProveedor;
        this.fechaIngreso = fechaIngreso;
        this.fechaVencimiento = fechaVencimiento;
        this.cantidadInicial = cantidadInicial;
        this.cantidad = cantidad;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.pasillo = pasillo;
        this.estante = estante;
        this.idEstado = idEstado;
        this.nombreProducto = nombreProducto;
        this.nombreProveedor = nombreProveedor;
        this.nombreEstado = nombreEstado;
    }

    public int getIdLote() {
        return idLote;
    }

    public void setIdLote(int idLote) {
        this.idLote = idLote;
    }

    public String getNumeroLote() {
        return numeroLote;
    }

    public void setNumeroLote(String numeroLote) {
        this.numeroLote = numeroLote;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public double getCantidadInicial() {
        return cantidadInicial;
    }

    public void setCantidadInicial(double cantidadInicial) {
        this.cantidadInicial = cantidadInicial;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String getPasillo() {
        return pasillo;
    }

    public void setPasillo(String pasillo) {
        this.pasillo = pasillo;
    }

    public String getEstante() {
        return estante;
    }

    public void setEstante(String estante) {
        this.estante = estante;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }
}
