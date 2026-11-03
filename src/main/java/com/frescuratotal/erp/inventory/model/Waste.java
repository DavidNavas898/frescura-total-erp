package com.frescuratotal.erp.inventory.model;

import java.sql.Date;

public class Waste {
    private int idRegistro;
    private int idLote;
    private int idInspeccion;
    private double cantidadPerdida;
    private String motivo;
    private Date fechaRegistro;
    private String usuarioRegistro;

    public Waste() {
    }

    public Waste(int idRegistro, int idLote, int idInspeccion, double cantidadPerdida, String motivo,
            Date fechaRegistro, String usuarioRegistro) {
        this.idRegistro = idRegistro;
        this.idLote = idLote;
        this.idInspeccion = idInspeccion;
        this.cantidadPerdida = cantidadPerdida;
        this.motivo = motivo;
        this.fechaRegistro = fechaRegistro;
        this.usuarioRegistro = usuarioRegistro;
    }

    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public int getIdLote() {
        return idLote;
    }

    public void setIdLote(int idLote) {
        this.idLote = idLote;
    }

    public int getIdInspeccion() {
        return idInspeccion;
    }

    public void setIdInspeccion(int idInspeccion) {
        this.idInspeccion = idInspeccion;
    }

    public double getCantidadPerdida() {
        return cantidadPerdida;
    }

    public void setCantidadPerdida(double cantidadPerdida) {
        this.cantidadPerdida = cantidadPerdida;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }
}
