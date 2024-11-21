package com.desarrollo.pansal.controller;

import com.desarrollo.pansal.service.ReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/reports")
public class ReportController {
    private static final Logger log = LoggerFactory.getLogger(ReportController.class);
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/clientes")
    public ResponseEntity<?> generateReport(@RequestParam Map<String, String> params) {
        try {
            // Convertir parámetros a Map<String, Object>
            Map<String, Object> reportParameters = new HashMap<>(params);

            // Generar el reporte
            byte[] pdfData = reportService.generateClientePdfReport(reportParameters);

            // Preparar la respuesta
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=clientes_report.pdf")
                    .body(pdfData);

        } catch (Exception e) {
            log.error("Error al generar el reporte: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Error al generar el reporte: " + e.getMessage());
        }
    }


    @GetMapping("/proveedores")
    public ResponseEntity<?> generateProveedorReport(@RequestParam Map<String, String> params) {
        try {
            Map<String, Object> reportParameters = new HashMap<>(params);
            byte[] pdfData = reportService.generateProveedorPdfReport(reportParameters);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=proveedores_report.pdf")
                    .body(pdfData);

        } catch (Exception e) {
            log.error("Error al generar el reporte de proveedores: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Error al generar el reporte de proveedores: " + e.getMessage());
        }
    }

    @GetMapping("/categorias")
    public ResponseEntity<?> generateCategoriasReport(@RequestParam Map<String, String> params) {
        try {
            Map<String, Object> reportParameters = new HashMap<>(params);
            byte[] pdfData = reportService.generateCategoriasPdfReport(reportParameters);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=categorias_report.pdf")
                    .body(pdfData);

        } catch (Exception e) {
            log.error("Error al generar el reporte de categorias: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Error al generar el reporte de categorias: " + e.getMessage());
        }
    }

    @GetMapping("/materiasPrima")
    public ResponseEntity<?> generateMateriasPrimasReport(@RequestParam Map<String, String> params) {
        try {
            Map<String, Object> reportParameters = new HashMap<>(params);
            byte[] pdfData = reportService.generateMateriasPrimasPdfReport(reportParameters);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=materiasPrimas_report.pdf")
                    .body(pdfData);

        } catch (Exception e) {
            log.error("Error al generar el reporte de materias primas: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Error al generar el reporte de materias primas: " + e.getMessage());
        }
    }



}
