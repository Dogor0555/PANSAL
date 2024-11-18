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
            byte[] pdfData = reportService.generatePdfReport(reportParameters);

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
}
