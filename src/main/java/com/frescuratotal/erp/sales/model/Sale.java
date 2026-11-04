package com.frescuratotal.erp.sales.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Sale {

    private int idVenta;
    private String numeroVenta;
    private int idCliente;
    private Timestamp fechaVenta;
    private double subtotal;
    private double totalDescuento;
    private double totalImpuesto;
    private double montoTotal;
    private int idEstado;
    private String usuarioRegistro;
    private String nombreCliente;
    private String nombreEstado;
    private List<SaleDetail> detalles = new ArrayList<>();

    public Sale() {
    }

    public Sale(int idVenta, String numeroVenta, int idCliente, Timestamp fechaVenta, double subtotal,
            double totalDescuento, double totalImpuesto, double montoTotal, int idEstado, String usuarioRegistro,
            String nombreCliente, String nombreEstado) {
        this.idVenta = idVenta;
        this.numeroVenta = numeroVenta;
        this.idCliente = idCliente;
        this.fechaVenta = fechaVenta;
        this.subtotal = subtotal;
        this.totalDescuento = totalDescuento;
        this.totalImpuesto = totalImpuesto;
        this.montoTotal = montoTotal;
        this.idEstado = idEstado;
        this.usuarioRegistro = usuarioRegistro;
        this.nombreCliente = nombreCliente;
        this.nombreEstado = nombreEstado;
    }

    public void agregarDetalle(SaleDetail detalle) {
        detalles.add(detalle);
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getNumeroVenta() {
        return numeroVenta;
    }

    public void setNumeroVenta(String numeroVenta) {
        this.numeroVenta = numeroVenta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Timestamp getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Timestamp fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getTotalDescuento() {
        return totalDescuento;
    }

    public void setTotalDescuento(double totalDescuento) {
        this.totalDescuento = totalDescuento;
    }

    public double getTotalImpuesto() {
        return totalImpuesto;
    }

    public void setTotalImpuesto(double totalImpuesto) {
        this.totalImpuesto = totalImpuesto;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public List<SaleDetail> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<SaleDetail> detalles) {
        this.detalles = detalles;
    }
}
