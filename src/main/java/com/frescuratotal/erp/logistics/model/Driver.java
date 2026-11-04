package com.frescuratotal.erp.logistics.model;

public class Driver {

    private int idConductor;
    private String nombre;
    private String numeroLicencia;
    private String telefono;
    private String email;
    private boolean activo;

    public Driver() {
    }

    public Driver(int idConductor, String nombre, String numeroLicencia, String telefono, String email,
            boolean activo) {
        this.idConductor = idConductor;
        this.nombre = nombre;
        this.numeroLicencia = numeroLicencia;
        this.telefono = telefono;
        this.email = email;
        this.activo = activo;
    }

    public int getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(int idConductor) {
        this.idConductor = idConductor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumeroLicencia() {
        return numeroLicencia;
    }

    public void setNumeroLicencia(String numeroLicencia) {
        this.numeroLicencia = numeroLicencia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
