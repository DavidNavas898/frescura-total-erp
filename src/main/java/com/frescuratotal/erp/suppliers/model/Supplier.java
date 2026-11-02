package com.frescuratotal.erp.suppliers.model;

import java.time.LocalDateTime;

public class Supplier {

    private int idProveedor;
    private String nombre;
    private String nit;
    private String personaContacto;
    private String email;
    private String telefono;
    private String direccion;
    private boolean activo;
    private LocalDateTime fechaRegistro;

    public Supplier() {
    }

    public Supplier(int idProveedor, String nombre, String nit, String personaContacto,
                    String email, String telefono, String direccion, boolean activo,
                    LocalDateTime fechaRegistro) {
        this.idProveedor = idProveedor;
        this.nombre = nombre;
        this.nit = nit;
        this.personaContacto = personaContacto;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.activo = activo;
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getPersonaContacto() {
        return personaContacto;
    }

    public void setPersonaContacto(String personaContacto) {
        this.personaContacto = personaContacto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public String toString() {
        return idProveedor + " | " + nombre + " | NIT " + nit + " | " + email;
    }
}
