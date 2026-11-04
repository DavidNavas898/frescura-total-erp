package com.frescuratotal.erp.common;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class LogManager {

    public static void registrar(String tipo, String mensaje) {
        try {
            FileWriter writer = new FileWriter("erp.log", true);
            writer.write(LocalDateTime.now() + " | " + tipo + " | " + mensaje + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("No se pudo escribir en el log.");
        }
    }
}
