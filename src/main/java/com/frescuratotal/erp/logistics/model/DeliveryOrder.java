package com.frescuratotal.erp.logistics.model;

import java.sql.Timestamp;

public class DeliveryOrder {

    private int id;
    private int idRuta;
    private int idVenta;
    private int ordenEntrega;
    private String direccionDestino;
    private Timestamp fechaEntrega;
    private String recibidoPor;
    private String observaciones;
    private String numeroVenta;
    private String nombreCliente;
    private int idEstadoVenta;
    private String nombreEstadoVenta;

    public DeliveryOrder() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getOrdenEntrega() {
        return ordenEntrega;
    }

    public void setOrdenEntrega(int ordenEntrega) {
        this.ordenEntrega = ordenEntrega;
    }

    public String getDireccionDestino() {
        return direccionDestino;
    }

    public void setDireccionDestino(String direccionDestino) {
        this.direccionDestino = direccionDestino;
    }

    public Timestamp getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Timestamp fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getRecibidoPor() {
        return recibidoPor;
    }

    public void setRecibidoPor(String recibidoPor) {
        this.recibidoPor = recibidoPor;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getNumeroVenta() {
        return numeroVenta;
    }

    public void setNumeroVenta(String numeroVenta) {
        this.numeroVenta = numeroVenta;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public int getIdEstadoVenta() {
        return idEstadoVenta;
    }

    public void setIdEstadoVenta(int idEstadoVenta) {
        this.idEstadoVenta = idEstadoVenta;
    }

    public String getNombreEstadoVenta() {
        return nombreEstadoVenta;
    }

    public void setNombreEstadoVenta(String nombreEstadoVenta) {
        this.nombreEstadoVenta = nombreEstadoVenta;
    }
}
