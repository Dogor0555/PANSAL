package com.desarrollo.pansal.service;

import com.desarrollo.pansal.model.Clientes;
import com.desarrollo.pansal.repository.ClienteRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import net.sf.jasperreports.engine.util.JRLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {
    private static final Logger log = LoggerFactory.getLogger(ReportService.class);
    private final ClienteRepository clienteRepository;

    public ReportService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public byte[] generatePdfReport(Map<String, Object> parameters) throws Exception {
        try {
            // Definir la ruta del archivo .jasper
            String jasperPath = "/reports/clientes_report.jasper";
            ClassPathResource jasperResource = new ClassPathResource(jasperPath);

            JasperReport jasperReport;

            if (jasperResource.exists()) {
                log.info("El archivo .jasper encontrado en: {}", jasperPath);
                try (InputStream jasperStream = jasperResource.getInputStream()) {
                    if (jasperStream.available() == 0) {
                        log.error("El archivo jasper está vacío.");
                        throw new IllegalStateException("El archivo .jasper está vacío.");
                    }
                    jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
                    log.info("Archivo .jasper cargado correctamente.");
                }
            } else {
                log.warn("No se encontró el archivo .jasper, intentando compilar el archivo .jrxml.");
                // Si no existe el .jasper, intentamos compilar el .jrxml
                String jrxmlPath = "/reports/clientes_report.jrxml";
                ClassPathResource jrxmlResource = new ClassPathResource(jrxmlPath);

                if (!jrxmlResource.exists()) {
                    log.error("No se encontraron los archivos del reporte: {} ni {}", jasperPath, jrxmlPath);
                    throw new IllegalStateException("No se encontraron los archivos del reporte: " + jasperPath + " ni " + jrxmlPath);
                }

                log.info("Compilando el archivo .jrxml desde: {}", jrxmlPath);
                try (InputStream jrxmlStream = jrxmlResource.getInputStream()) {
                    jasperReport = JasperCompileManager.compileReport(jrxmlStream);
                    log.info("Archivo .jrxml compilado correctamente.");
                } catch (JRException e) {
                    log.error("Error al compilar el archivo .jrxml: {}", e.getMessage(), e);
                    throw new RuntimeException("Error al compilar el archivo .jrxml: " + e.getMessage(), e);
                }
            }

            // Obtener los datos
            List<Clientes> clientes = clienteRepository.findAll();
            if (clientes.isEmpty()) {
                log.error("No hay clientes para generar el reporte.");
                throw new IllegalStateException("No hay clientes para generar el reporte.");
            }
            log.info("Procesando reporte con {} clientes", clientes.size());

            // Crear la fuente de datos
            JRDataSource dataSource = new JRBeanCollectionDataSource(clientes);

            // Generar el reporte
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    dataSource
            );

            // Exportar a PDF
            byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);
            log.info("Reporte generado exitosamente.");

            return pdfData;

        } catch (Exception e) {
            log.error("Error al generar el reporte: {}", e.getMessage(), e);
            throw new RuntimeException("Error al generar el reporte PDF: " + e.getMessage(), e);
        }
    }
}
