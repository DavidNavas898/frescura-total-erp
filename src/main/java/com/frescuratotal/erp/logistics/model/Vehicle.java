package com.frescuratotal.erp.logistics.model;

public class Vehicle {

    private int idVehiculo;
    private String placa;
    private String modelo;
    private double capacidad;
    private String estado;

    public Vehicle() {
    }

    public Vehicle(int idVehiculo, String placa, String modelo, double capacidad, String estado) {
        this.idVehiculo = idVehiculo;
        this.placa = placa;
        this.modelo = modelo;
        this.capacidad = capacidad;
        this.estado = estado;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(double capacidad) {
        this.capacidad = capacidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
