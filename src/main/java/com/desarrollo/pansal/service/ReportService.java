package com.desarrollo.pansal.service;

import com.desarrollo.pansal.model.Clientes;
import com.desarrollo.pansal.repository.ClienteRepository;
import com.desarrollo.pansal.model.Proveedores;
import com.desarrollo.pansal.repository.ProveedorRepository;
import com.desarrollo.pansal.model.Categorias;
import com.desarrollo.pansal.repository.CategoriasRepository;
import com.desarrollo.pansal.model.MateriaPrima;
import com.desarrollo.pansal.repository.MateriaPrimaRepository;
import com.desarrollo.pansal.model.Recetas;
import com.desarrollo.pansal.repository.RecetasRepository;
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
    private final ProveedorRepository proveedorRepository; // Repositorio de proveedores
    private final CategoriasRepository categoriasRepository;
    private final MateriaPrimaRepository materiaPrimaRepository;
    private final RecetasRepository recetasRepository;

    public ReportService(ClienteRepository clienteRepository, ProveedorRepository proveedorRepository, CategoriasRepository categoriasRepository, MateriaPrimaRepository materiaPrimaRepository, RecetasRepository recetasRepository) {
        this.clienteRepository = clienteRepository;
        this.proveedorRepository = proveedorRepository;
        this.categoriasRepository = categoriasRepository;
        this.materiaPrimaRepository = materiaPrimaRepository;
        this.recetasRepository = recetasRepository;
    }

    public byte[] generateClientePdfReport(Map<String, Object> parameters) throws Exception {
        try {
            // Definir la ruta del archivo .jasper para clientes
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

            // Obtener los datos de clientes
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
            log.error("Error al generar el reporte de clientes: {}", e.getMessage(), e);
            throw new RuntimeException("Error al generar el reporte PDF de clientes: " + e.getMessage(), e);
        }
    }

    public byte[] generateProveedorPdfReport(Map<String, Object> parameters) throws Exception {
        try {
            // Definir la ruta del archivo .jasper para proveedores
            String jasperPath = "/reports/proveedores_report.jasper";
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
                String jrxmlPath = "/reports/proveedores_report.jrxml";
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

            // Obtener los datos de proveedores
            List<Proveedores> proveedores = proveedorRepository.findAll(); // Repositorio de proveedores
            if (proveedores.isEmpty()) {
                log.error("No hay proveedores para generar el reporte.");
                throw new IllegalStateException("No hay proveedores para generar el reporte.");
            }
            log.info("Procesando reporte con {} proveedores", proveedores.size());

            // Crear la fuente de datos
            JRDataSource dataSource = new JRBeanCollectionDataSource(proveedores);

            // Generar el reporte
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    dataSource
            );

            // Exportar a PDF
            byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);
            log.info("Reporte de proveedores generado exitosamente.");

            return pdfData;

        } catch (Exception e) {
            log.error("Error al generar el reporte de proveedores: {}", e.getMessage(), e);
            throw new RuntimeException("Error al generar el reporte PDF de proveedores: " + e.getMessage(), e);
        }
    }


    public byte[] generateCategoriasPdfReport(Map<String, Object> parameters) throws Exception {
        try {
            // Definir la ruta del archivo .jasper para categorias
            String jasperPath = "/reports/categorias_report.jasper";
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
                String jrxmlPath = "/reports/categorias_report.jrxml";
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

            // Obtener los datos de categorias
            List<Categorias> categorias = categoriasRepository.findAll(); // Repositorio de proveedores
            if (categorias.isEmpty()) {
                log.error("No hay categorias para generar el reporte.");
                throw new IllegalStateException("No hay categorias para generar el reporte.");
            }
            log.info("Procesando reporte con {} categorias", categorias.size());

            // Crear la fuente de datos
            JRDataSource dataSource = new JRBeanCollectionDataSource(categorias);

            // Generar el reporte
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    dataSource
            );

            // Exportar a PDF
            byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);
            log.info("Reporte de categorias generado exitosamente.");

            return pdfData;

        } catch (Exception e) {
            log.error("Error al generar el reporte de categorias: {}", e.getMessage(), e);
            throw new RuntimeException("Error al generar el reporte PDF de categorias: " + e.getMessage(), e);
        }
    }

    public byte[] generateMateriasPrimasPdfReport(Map<String, Object> parameters) throws Exception {
        try {
            // Definir la ruta del archivo .jasper para categorias
            String jasperPath = "/reports/materiaPrima_report.jasper";
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
                String jrxmlPath = "/reports/materiaPrima_report.jrxml";
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

            // Obtener los datos de categorias
            List<MateriaPrima> materiaPrimas = materiaPrimaRepository.findAll(); // Repositorio de proveedores
            if (materiaPrimas.isEmpty()) {
                log.error("No hay Materias Primas para generar el reporte.");
                throw new IllegalStateException("No hay Materias Primas para generar el reporte.");
            }
            log.info("Procesando reporte con {} materias primas", materiaPrimas.size());

            // Crear la fuente de datos
            JRDataSource dataSource = new JRBeanCollectionDataSource(materiaPrimas);

            // Generar el reporte
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    dataSource
            );

            // Exportar a PDF
            byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);
            log.info("Reporte de materias primas generado exitosamente.");

            return pdfData;

        } catch (Exception e) {
            log.error("Error al generar el reporte de materias primas: {}", e.getMessage(), e);
            throw new RuntimeException("Error al generar el reporte PDF de materias primas: " + e.getMessage(), e);
        }
    }


    public byte[] generateRecetasPdfReport(Map<String, Object> parameters) throws Exception {
        try {
            // Definir la ruta del archivo .jasper para recetas
            String jasperPath = "/reports/recetas_report.jasper";
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
                String jrxmlPath = "/reports/recetas_report.jrxml";
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

            // Obtener los datos de recetas
            List<Recetas> recetas = recetasRepository.findAll(); // Repositorio de recetas
            if (recetas.isEmpty()) {
                log.error("No hay recetas para generar el reporte.");
                throw new IllegalStateException("No hay recetas para generar el reporte.");
            }
            log.info("Procesando reporte con {} recetas", recetas.size());

            // Crear la fuente de datos
            JRDataSource dataSource = new JRBeanCollectionDataSource(recetas);

            // Generar el reporte
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    dataSource
            );

            // Exportar a PDF
            byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);
            log.info("Reporte de recetas generado exitosamente.");

            return pdfData;

        } catch (Exception e) {
            log.error("Error al generar el reporte de recetas: {}", e.getMessage(), e);
            throw new RuntimeException("Error al generar el reporte PDF de recetas: " + e.getMessage(), e);
        }
    }



}
