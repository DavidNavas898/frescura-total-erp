package com.frescuratotal.erp.logistics.controller;

import com.frescuratotal.erp.common.LogManager;
import com.frescuratotal.erp.logistics.model.DeliveryOrder;
import com.frescuratotal.erp.logistics.model.DeliveryRoute;
import com.frescuratotal.erp.logistics.model.Driver;
import com.frescuratotal.erp.logistics.model.DriverDao;
import com.frescuratotal.erp.logistics.model.RouteDao;
import com.frescuratotal.erp.logistics.model.Vehicle;
import com.frescuratotal.erp.logistics.model.VehicleDao;
import com.frescuratotal.erp.logistics.view.LogisticsView;
import java.sql.SQLException;
import java.util.List;

public class LogisticsController {

    private DriverDao driverDao;
    private VehicleDao vehicleDao;
    private RouteDao routeDao;
    private LogisticsView logisticsView;

    public LogisticsController(DriverDao driverDao, VehicleDao vehicleDao, RouteDao routeDao,
            LogisticsView logisticsView) {
        this.driverDao = driverDao;
        this.vehicleDao = vehicleDao;
        this.routeDao = routeDao;
        this.logisticsView = logisticsView;
    }

    public void iniciar() {
        int opcion;
        do {
            opcion = logisticsView.mostrarMenu();
            switch (opcion) {
                case 1:
                    listarConductores();
                    break;
                case 2:
                    registrarConductor();
                    break;
                case 3:
                    listarVehiculos();
                    break;
                case 4:
                    registrarVehiculo();
                    break;
                case 5:
                    mostrarPanelDespachos();
                    break;
                case 6:
                    crearRuta();
                    break;
                case 7:
                    verRutas();
                    break;
                case 8:
                    iniciarRuta();
                    break;
                case 9:
                    registrarEntrega();
                    break;
                case 0:
                    logisticsView.mostrarMensaje("Regresando al menu principal...");
                    break;
                default:
                    logisticsView.mostrarMensaje("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    private void listarConductores() {
        try {
            logisticsView.mostrarConductores(driverDao.obtenerConductores());
        } catch (SQLException e) {
            logisticsView.mostrarMensaje("Error al listar conductores: " + e.getMessage());
        }
    }

    private void registrarConductor() {
        Driver conductor = logisticsView.pedirDatosConductor();
        if (conductor.getNombre().isEmpty() || conductor.getNumeroLicencia().isEmpty()) {
            logisticsView.mostrarMensaje("El nombre y la licencia son obligatorios. Conductor no registrado.");
            return;
        }
        try {
            driverDao.registrarConductor(conductor);
            logisticsView.mostrarMensaje("Conductor registrado exitosamente.");
        } catch (SQLException e) {
            logisticsView.mostrarMensaje("Error al registrar conductor: " + e.getMessage());
        }
    }

    private void listarVehiculos() {
        try {
            logisticsView.mostrarVehiculos(vehicleDao.obtenerVehiculos());
        } catch (SQLException e) {
            logisticsView.mostrarMensaje("Error al listar vehiculos: " + e.getMessage());
        }
    }

    private void registrarVehiculo() {
        Vehicle vehiculo = logisticsView.pedirDatosVehiculo();
        if (vehiculo.getPlaca().isEmpty()) {
            logisticsView.mostrarMensaje("La placa es obligatoria. Vehiculo no registrado.");
            return;
        }
        try {
            vehicleDao.registrarVehiculo(vehiculo);
            logisticsView.mostrarMensaje("Vehiculo registrado exitosamente.");
        } catch (SQLException e) {
            logisticsView.mostrarMensaje("Error al registrar vehiculo: " + e.getMessage());
        }
    }

    private void mostrarPanelDespachos() {
        try {
            logisticsView.mostrarPanelDespachos(routeDao.obtenerVentasEnPreparacion());
        } catch (SQLException e) {
            logisticsView.mostrarMensaje("Error al cargar el panel de despachos: " + e.getMessage());
        }
    }

    private void crearRuta() {
        try {
            // 1. Debe haber pedidos para despachar
            List<DeliveryOrder> pendientes = routeDao.obtenerVentasEnPreparacion();
            if (pendientes.isEmpty()) {
                logisticsView.mostrarMensaje("No hay pedidos en preparacion para despachar.");
                return;
            }

            List<DeliveryRoute> rutas = routeDao.obtenerRutas();

            // 2. Elegir vehiculo: debe existir, estar Activo y no estar en otra ruta sin terminar
            List<Vehicle> vehiculos = vehicleDao.obtenerVehiculos();
            logisticsView.mostrarVehiculos(vehiculos);
            int idVehiculo = logisticsView.pedirNumero("ID del vehiculo: ");
            Vehicle vehiculo = null;
            for (Vehicle v : vehiculos) {
                if (v.getIdVehiculo() == idVehiculo) {
                    vehiculo = v;
                }
            }
            if (vehiculo == null) {
                logisticsView.mostrarMensaje("El vehiculo no existe.");
                return;
            }
            if (!vehiculo.getEstado().equals("Activo")) {
                logisticsView.mostrarMensaje("El vehiculo no esta activo (estado: " + vehiculo.getEstado() + ").");
                return;
            }
            for (DeliveryRoute r : rutas) {
                if (r.getIdVehiculo() == idVehiculo && rutaSinTerminar(r)) {
                    logisticsView.mostrarMensaje("Ese vehiculo ya esta en la ruta " + r.getNombreRuta() + ".");
                    return;
                }
            }

            // 3. Elegir conductor: debe existir, estar activo y no estar en otra ruta sin terminar
            List<Driver> conductores = driverDao.obtenerConductores();
            logisticsView.mostrarConductores(conductores);
            int idConductor = logisticsView.pedirNumero("ID del conductor: ");
            Driver conductor = null;
            for (Driver c : conductores) {
                if (c.getIdConductor() == idConductor) {
                    conductor = c;
                }
            }
            if (conductor == null) {
                logisticsView.mostrarMensaje("El conductor no existe.");
                return;
            }
            if (!conductor.isActivo()) {
                logisticsView.mostrarMensaje("El conductor no esta activo.");
                return;
            }
            for (DeliveryRoute r : rutas) {
                if (r.getIdConductor() == idConductor && rutaSinTerminar(r)) {
                    logisticsView.mostrarMensaje("Ese conductor ya esta en la ruta " + r.getNombreRuta() + ".");
                    return;
                }
            }

            // 4. Elegir los pedidos hasta que escriba 0
            DeliveryRoute ruta = new DeliveryRoute();
            ruta.setIdVehiculo(idVehiculo);
            ruta.setIdConductor(idConductor);

            int idVenta;
            do {
                logisticsView.mostrarPanelDespachos(pendientes);
                idVenta = logisticsView.pedirNumero("ID de la venta a agregar (0 para terminar): ");
                if (idVenta != 0) {
                    agregarPedidoARuta(ruta, pendientes, idVenta);
                }
            } while (idVenta != 0);

            if (ruta.getPedidos().isEmpty()) {
                logisticsView.mostrarMensaje("La ruta no tiene pedidos. Ruta cancelada.");
                return;
            }

            // 5. Datos de la ruta y guardar todo en una transaccion
            ruta.setNombreRuta(logisticsView.pedirTexto("Nombre de la ruta (ej: Norte - Manana): "));
            ruta.setUsuarioCreacion(logisticsView.pedirTexto("Nombre del encargado de logistica: "));

            int idRuta = routeDao.crearRuta(ruta);
            logisticsView.mostrarMensaje("Ruta creada (ID " + idRuta + ") con " + ruta.getPedidos().size()
                    + " pedido(s), vehiculo " + vehiculo.getPlaca() + " y conductor " + conductor.getNombre() + ".");
            LogManager.registrar("INFO", "Ruta creada: ID " + idRuta + ", vehiculo " + vehiculo.getPlaca());
        } catch (SQLException e) {
            logisticsView.mostrarMensaje("Error al crear la ruta: " + e.getMessage());
            LogManager.registrar("ERROR", "Error al crear la ruta: " + e.getMessage());
        }
    }

    private void agregarPedidoARuta(DeliveryRoute ruta, List<DeliveryOrder> pendientes, int idVenta) {
        for (DeliveryOrder yaAgregado : ruta.getPedidos()) {
            if (yaAgregado.getIdVenta() == idVenta) {
                logisticsView.mostrarMensaje("Ese pedido ya esta en la ruta.");
                return;
            }
        }
        for (DeliveryOrder pedido : pendientes) {
            if (pedido.getIdVenta() == idVenta) {
                pedido.setOrdenEntrega(ruta.getPedidos().size() + 1);
                ruta.agregarPedido(pedido);
                pendientes.remove(pedido);
                logisticsView.mostrarMensaje("Pedido " + pedido.getNumeroVenta() + " agregado como entrega #"
                        + pedido.getOrdenEntrega() + ".");
                return;
            }
        }
        logisticsView.mostrarMensaje("Ese ID no esta en el panel de despachos.");
    }

    private boolean rutaSinTerminar(DeliveryRoute ruta) {
        return ruta.getEstado().equals("Planificada") || ruta.getEstado().equals("En Transito");
    }

    private void verRutas() {
        try {
            List<DeliveryRoute> rutas = routeDao.obtenerRutas();
            logisticsView.mostrarRutas(rutas);
            if (rutas.isEmpty()) {
                return;
            }
            int idRuta = logisticsView.pedirNumero("ID de la ruta para ver sus pedidos (0 para volver): ");
            if (idRuta != 0) {
                List<DeliveryOrder> pedidos = routeDao.obtenerPedidosDeRuta(idRuta);
                if (pedidos.isEmpty()) {
                    logisticsView.mostrarMensaje("No se encontraron pedidos para esa ruta.");
                } else {
                    logisticsView.mostrarPedidosDeRuta(pedidos);
                }
            }
        } catch (SQLException e) {
            logisticsView.mostrarMensaje("Error al listar rutas: " + e.getMessage());
        }
    }

    private void iniciarRuta() {
        try {
            List<DeliveryRoute> rutas = routeDao.obtenerRutas();
            logisticsView.mostrarRutas(rutas);
            int idRuta = logisticsView.pedirNumero("ID de la ruta que sale: ");

            DeliveryRoute ruta = buscarRuta(rutas, idRuta);
            if (ruta == null) {
                logisticsView.mostrarMensaje("La ruta no existe.");
                return;
            }
            if (!ruta.getEstado().equals("Planificada")) {
                logisticsView.mostrarMensaje("Solo se puede iniciar una ruta Planificada (estado actual: "
                        + ruta.getEstado() + ").");
                return;
            }

            routeDao.iniciarRuta(idRuta);
            logisticsView.mostrarMensaje("La ruta '" + ruta.getNombreRuta()
                    + "' salio. Sus pedidos pasaron a 'En Transito'.");
        } catch (SQLException e) {
            logisticsView.mostrarMensaje("Error al iniciar la ruta: " + e.getMessage());
        }
    }

    private void registrarEntrega() {
        try {
            List<DeliveryRoute> rutas = routeDao.obtenerRutas();
            logisticsView.mostrarRutas(rutas);
            int idRuta = logisticsView.pedirNumero("ID de la ruta: ");

            DeliveryRoute ruta = buscarRuta(rutas, idRuta);
            if (ruta == null) {
                logisticsView.mostrarMensaje("La ruta no existe.");
                return;
            }
            if (!ruta.getEstado().equals("En Transito")) {
                logisticsView.mostrarMensaje("Solo se registran entregas de rutas En Transito (estado actual: "
                        + ruta.getEstado() + ").");
                return;
            }

            List<DeliveryOrder> pedidos = routeDao.obtenerPedidosDeRuta(idRuta);
            logisticsView.mostrarPedidosDeRuta(pedidos);
            int idVenta = logisticsView.pedirNumero("ID de la venta entregada: ");

            DeliveryOrder pedido = null;
            for (DeliveryOrder p : pedidos) {
                if (p.getIdVenta() == idVenta) {
                    pedido = p;
                }
            }
            if (pedido == null) {
                logisticsView.mostrarMensaje("Esa venta no pertenece a la ruta.");
                return;
            }
            if (pedido.getFechaEntrega() != null) {
                logisticsView.mostrarMensaje("Ese pedido ya fue entregado.");
                return;
            }

            pedido.setRecibidoPor(logisticsView.pedirTexto("Recibido por: "));
            pedido.setObservaciones(logisticsView.pedirTexto("Observaciones: "));

            boolean rutaCompletada = routeDao.registrarEntrega(pedido);
            logisticsView.mostrarMensaje("Pedido " + pedido.getNumeroVenta() + " entregado.");
            if (rutaCompletada) {
                logisticsView.mostrarMensaje("Todos los pedidos fueron entregados. La ruta '"
                        + ruta.getNombreRuta() + "' quedo Completada.");
            }
        } catch (SQLException e) {
            logisticsView.mostrarMensaje("Error al registrar la entrega: " + e.getMessage());
        }
    }

    private DeliveryRoute buscarRuta(List<DeliveryRoute> rutas, int idRuta) {
        for (DeliveryRoute ruta : rutas) {
            if (ruta.getIdRuta() == idRuta) {
                return ruta;
            }
        }
        return null;
    }
}
