package com.frescuratotal.erp.logistics.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DeliveryRoute {

    private int idRuta;
    private String nombreRuta;
    private Timestamp fechaCreacion;
    private String usuarioCreacion;
    private Timestamp horaSalidaEstimada;
    private Timestamp horaLlegadaEstimada;
    private Timestamp horaSalidaReal;
    private Timestamp horaLlegadaReal;
    private String estado;
    private int idVehiculo;
    private int idConductor;
    private String placaVehiculo;
    private String nombreConductor;
    private List<DeliveryOrder> pedidos = new ArrayList<>();

    public DeliveryRoute() {
    }

    public void agregarPedido(DeliveryOrder pedido) {
        pedidos.add(pedido);
    }

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Timestamp getHoraSalidaEstimada() {
        return horaSalidaEstimada;
    }

    public void setHoraSalidaEstimada(Timestamp horaSalidaEstimada) {
        this.horaSalidaEstimada = horaSalidaEstimada;
    }

    public Timestamp getHoraLlegadaEstimada() {
        return horaLlegadaEstimada;
    }

    public void setHoraLlegadaEstimada(Timestamp horaLlegadaEstimada) {
        this.horaLlegadaEstimada = horaLlegadaEstimada;
    }

    public Timestamp getHoraSalidaReal() {
        return horaSalidaReal;
    }

    public void setHoraSalidaReal(Timestamp horaSalidaReal) {
        this.horaSalidaReal = horaSalidaReal;
    }

    public Timestamp getHoraLlegadaReal() {
        return horaLlegadaReal;
    }

    public void setHoraLlegadaReal(Timestamp horaLlegadaReal) {
        this.horaLlegadaReal = horaLlegadaReal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public int getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(int idConductor) {
        this.idConductor = idConductor;
    }

    public String getPlacaVehiculo() {
        return placaVehiculo;
    }

    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
    }

    public String getNombreConductor() {
        return nombreConductor;
    }

    public void setNombreConductor(String nombreConductor) {
        this.nombreConductor = nombreConductor;
    }

    public List<DeliveryOrder> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<DeliveryOrder> pedidos) {
        this.pedidos = pedidos;
    }
}
