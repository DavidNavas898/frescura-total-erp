package com.frescuratotal.erp.sales.model;

import java.sql.Timestamp;

public class Invoice {

    private int idFactura;
    private int idVenta;
    private String numeroFactura;
    private String cufe;
    private String enlaceQr;
    private Timestamp fechaEmision;
    private double subtotal;
    private double totalDescuento;
    private double montoImpuesto;
    private double montoTotal;

    public Invoice() {
    }

    public Invoice(int idFactura, int idVenta, String numeroFactura, String cufe, String enlaceQr,
            Timestamp fechaEmision, double subtotal, double totalDescuento, double montoImpuesto, double montoTotal) {
        this.idFactura = idFactura;
        this.idVenta = idVenta;
        this.numeroFactura = numeroFactura;
        this.cufe = cufe;
        this.enlaceQr = enlaceQr;
        this.fechaEmision = fechaEmision;
        this.subtotal = subtotal;
        this.totalDescuento = totalDescuento;
        this.montoImpuesto = montoImpuesto;
        this.montoTotal = montoTotal;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getCufe() {
        return cufe;
    }

    public void setCufe(String cufe) {
        this.cufe = cufe;
    }

    public String getEnlaceQr() {
        return enlaceQr;
    }

    public void setEnlaceQr(String enlaceQr) {
        this.enlaceQr = enlaceQr;
    }

    public Timestamp getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Timestamp fechaEmision) {
        this.fechaEmision = fechaEmision;
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

    public double getMontoImpuesto() {
        return montoImpuesto;
    }

    public void setMontoImpuesto(double montoImpuesto) {
        this.montoImpuesto = montoImpuesto;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }
}
