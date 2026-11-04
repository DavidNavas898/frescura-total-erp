package com.frescuratotal.erp.logistics.view;

import com.frescuratotal.erp.logistics.model.DeliveryOrder;
import com.frescuratotal.erp.logistics.model.DeliveryRoute;
import com.frescuratotal.erp.logistics.model.Driver;
import com.frescuratotal.erp.logistics.model.Vehicle;
import java.util.List;
import java.util.Scanner;

public class LogisticsView {

    private Scanner scanner;

    public LogisticsView(Scanner scanner) {
        this.scanner = scanner;
    }

    public int mostrarMenu() {
        System.out.println();
        System.out.println("--- MENU DE LOGISTICA ---");
        System.out.println("1. Listar conductores");
        System.out.println("2. Registrar conductor");
        System.out.println("3. Listar vehiculos");
        System.out.println("4. Registrar vehiculo");
        System.out.println("5. Panel de despachos (pedidos en preparacion)");
        System.out.println("6. Crear ruta de entrega");
        System.out.println("7. Ver rutas");
        System.out.println("8. Iniciar ruta (salida del vehiculo)");
        System.out.println("9. Registrar entrega de un pedido");
        System.out.println("0. Volver al menu principal");
        return leerNumero("Seleccione una opcion: ");
    }

    public void mostrarConductores(List<Driver> conductores) {
        System.out.println();
        System.out.println("--- CONDUCTORES ---");
        if (conductores.isEmpty()) {
            System.out.println("No hay conductores registrados.");
            return;
        }
        for (Driver conductor : conductores) {
            String activo = "No";
            if (conductor.isActivo()) {
                activo = "Si";
            }
            System.out.println("ID: " + conductor.getIdConductor()
                    + " | Nombre: " + conductor.getNombre()
                    + " | Licencia: " + conductor.getNumeroLicencia()
                    + " | Telefono: " + conductor.getTelefono()
                    + " | Activo: " + activo);
        }
    }

    public Driver pedirDatosConductor() {
        System.out.println();
        System.out.println("--- REGISTRAR CONDUCTOR ---");
        Driver conductor = new Driver();
        System.out.print("Nombre: ");
        conductor.setNombre(scanner.nextLine());
        System.out.print("Numero de licencia: ");
        conductor.setNumeroLicencia(scanner.nextLine());
        System.out.print("Telefono: ");
        conductor.setTelefono(scanner.nextLine());
        System.out.print("Email: ");
        conductor.setEmail(scanner.nextLine());
        return conductor;
    }

    public void mostrarVehiculos(List<Vehicle> vehiculos) {
        System.out.println();
        System.out.println("--- VEHICULOS ---");
        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehiculos registrados.");
            return;
        }
        for (Vehicle vehiculo : vehiculos) {
            System.out.println("ID: " + vehiculo.getIdVehiculo()
                    + " | Placa: " + vehiculo.getPlaca()
                    + " | Modelo: " + vehiculo.getModelo()
                    + " | Capacidad: " + vehiculo.getCapacidad() + " kg"
                    + " | Estado: " + vehiculo.getEstado());
        }
    }

    public Vehicle pedirDatosVehiculo() {
        System.out.println();
        System.out.println("--- REGISTRAR VEHICULO ---");
        Vehicle vehiculo = new Vehicle();
        System.out.print("Placa: ");
        vehiculo.setPlaca(scanner.nextLine());
        System.out.print("Modelo: ");
        vehiculo.setModelo(scanner.nextLine());
        vehiculo.setCapacidad(leerDecimal("Capacidad (kg): "));
        return vehiculo;
    }

    public void mostrarPanelDespachos(List<DeliveryOrder> pedidos) {
        System.out.println();
        System.out.println("--- PANEL DE DESPACHOS: PEDIDOS EN PREPARACION ---");
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos pendientes de despacho.");
            return;
        }
        for (DeliveryOrder pedido : pedidos) {
            System.out.println("ID venta: " + pedido.getIdVenta()
                    + " | Venta: " + pedido.getNumeroVenta()
                    + " | Cliente: " + pedido.getNombreCliente()
                    + " | Direccion: " + pedido.getDireccionDestino());
        }
    }

    public void mostrarRutas(List<DeliveryRoute> rutas) {
        System.out.println();
        System.out.println("--- RUTAS DE ENTREGA ---");
        if (rutas.isEmpty()) {
            System.out.println("No hay rutas registradas.");
            return;
        }
        for (DeliveryRoute ruta : rutas) {
            System.out.println("ID: " + ruta.getIdRuta()
                    + " | Ruta: " + ruta.getNombreRuta()
                    + " | Vehiculo: " + ruta.getPlacaVehiculo()
                    + " | Conductor: " + ruta.getNombreConductor()
                    + " | Estado: " + ruta.getEstado()
                    + " | Salida: " + ruta.getHoraSalidaReal()
                    + " | Llegada: " + ruta.getHoraLlegadaReal());
        }
    }

    public void mostrarPedidosDeRuta(List<DeliveryOrder> pedidos) {
        System.out.println();
        System.out.println("--- PEDIDOS DE LA RUTA ---");
        for (DeliveryOrder pedido : pedidos) {
            System.out.println(pedido.getOrdenEntrega() + ". ID venta: " + pedido.getIdVenta()
                    + " | Venta: " + pedido.getNumeroVenta()
                    + " | Cliente: " + pedido.getNombreCliente()
                    + " | Direccion: " + pedido.getDireccionDestino()
                    + " | Estado: " + pedido.getNombreEstadoVenta()
                    + " | Entregado: " + pedido.getFechaEntrega()
                    + " | Recibio: " + pedido.getRecibidoPor());
        }
    }

    public String pedirTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    public int pedirNumero(String mensaje) {
        return leerNumero(mensaje);
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    private int leerNumero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine();
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un numero.");
            }
        }
    }

    private double leerDecimal(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine();
            try {
                return Double.parseDouble(texto);
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un valor numerico (ej: 1500).");
            }
        }
    }
}
