package com.frescuratotal.erp.inventory.model;

import java.sql.Date;

public class Inspection {
    private int idInspeccion;
    private int idLote;
    private Date fechaInspeccion;
    private String resultado;
    private String nombreInspector;
    private String motivoRechazo;
    private String notas;

    public Inspection() {
    }

    public Inspection(int idInspeccion, int idLote, Date fechaInspeccion, String resultado, String nombreInspector,
            String motivoRechazo, String notas) {
        this.idInspeccion = idInspeccion;
        this.idLote = idLote;
        this.fechaInspeccion = fechaInspeccion;
        this.resultado = resultado;
        this.nombreInspector = nombreInspector;
        this.motivoRechazo = motivoRechazo;
        this.notas = notas;
    }

    public int getIdInspeccion() {
        return idInspeccion;
    }

    public void setIdInspeccion(int idInspeccion) {
        this.idInspeccion = idInspeccion;
    }

    public int getIdLote() {
        return idLote;
    }

    public void setIdLote(int idLote) {
        this.idLote = idLote;
    }

    public Date getFechaInspeccion() {
        return fechaInspeccion;
    }

    public void setFechaInspeccion(Date fechaInspeccion) {
        this.fechaInspeccion = fechaInspeccion;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getNombreInspector() {
        return nombreInspector;
    }

    public void setNombreInspector(String nombreInspector) {
        this.nombreInspector = nombreInspector;
    }

    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    

    
    
    
}
