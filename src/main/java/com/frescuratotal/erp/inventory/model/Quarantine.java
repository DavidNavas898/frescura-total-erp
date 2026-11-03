package com.frescuratotal.erp.inventory.model;

import java.sql.Date;

public class Quarantine {
    private int idRegistro;
    private int idLote;
    private String motivo;
    private String usuario;
    private Date fechaRegistro;
    private boolean resuelta;
    private Date fechaResolucion;
    private String notasResolucion;

    public Quarantine() {
    }

    public Quarantine(int idRegistro, int idLote, String motivo, String usuario, Date fechaRegistro,
            boolean resuelta, Date fechaResolucion, String notasResolucion) {
        this.idRegistro = idRegistro;
        this.idLote = idLote;
        this.motivo = motivo;
        this.usuario = usuario;
        this.fechaRegistro = fechaRegistro;
        this.resuelta = resuelta;
        this.fechaResolucion = fechaResolucion;
        this.notasResolucion = notasResolucion;
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

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public boolean isResuelta() {
        return resuelta;
    }

    public void setResuelta(boolean resuelta) {
        this.resuelta = resuelta;
    }

    public Date getFechaResolucion() {
        return fechaResolucion;
    }

    public void setFechaResolucion(Date fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    public String getNotasResolucion() {
        return notasResolucion;
    }

    public void setNotasResolucion(String notasResolucion) {
        this.notasResolucion = notasResolucion;
    }
}
