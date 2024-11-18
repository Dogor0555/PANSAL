package com.desarrollo.pansal.utils;

import net.sf.jasperreports.engine.JasperCompileManager;
import java.io.File;

public class JasperReportCompiler {

    public static void main(String[] args) {
        try {
            // Usar File para manejar las rutas
            File jrxmlFile = new File("src/main/resources/reports/clientes_report.jrxml");
            File jasperFile = new File("src/main/resources/reports/clientes_report.jasper");

            // Verificar si el archivo existe
            if (!jrxmlFile.exists()) {
                System.err.println("El archivo .jrxml no existe en: " + jrxmlFile.getAbsolutePath());
                return;
            }

            // Compilar usando rutas absolutas
            JasperCompileManager.compileReportToFile(
                    jrxmlFile.getAbsolutePath(),
                    jasperFile.getAbsolutePath()
            );

            System.out.println("Reporte compilado exitosamente en: " + jasperFile.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("Error al compilar el reporte:");
            e.printStackTrace();
        }
    }
}