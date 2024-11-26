package com.desarrollo.pansal.controller;

import com.desarrollo.pansal.dto.VentasRequest;
import com.desarrollo.pansal.model.Clientes;
import com.desarrollo.pansal.model.Productos;
import com.desarrollo.pansal.model.Usuario;
import com.desarrollo.pansal.model.Ventas;
import com.desarrollo.pansal.service.VentasService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/ventas")
public class VentasController {

    @Autowired
    private VentasService ventasService;

    @GetMapping
    public ResponseEntity<List<Ventas>> getAllVentas() {
        return ResponseEntity.ok(ventasService.getAllVentas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ventas> getVentaById(@PathVariable Long id) {
        return ventasService.getVentaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createVenta(@Valid @RequestBody VentasRequest ventasRequest) {
        try {
            Ventas venta = convertToEntity(ventasRequest);
            Ventas newVenta = ventasService.createVenta(venta, ventasRequest.getDetalles());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Venta creada exitosamente");
            response.put("venta", newVenta);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            // Change this to Map<String, Object>
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error al crear la venta: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateVenta(@PathVariable Long id,
                                                           @Valid @RequestBody VentasRequest ventasRequest) {
        try {
            Ventas venta = convertToEntity(ventasRequest);
            Ventas updatedVenta = ventasService.updateVenta(id, venta, ventasRequest.getDetalles());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Venta actualizada exitosamente");
            response.put("venta", updatedVenta);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error al actualizar la venta: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteVenta(@PathVariable Long id) {
        try {
            ventasService.deleteVenta(id);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Venta eliminada exitosamente");

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error al eliminar la venta: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    private Ventas convertToEntity(VentasRequest ventasRequest) {
        Ventas venta = new Ventas();

        // Set Cliente
        Clientes cliente = new Clientes();
        cliente.setIdCliente(ventasRequest.getIdCliente());
        venta.setCliente(cliente);

        // Set Usuario
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(ventasRequest.getIdUsuario());
        venta.setUsuario(usuario);

        // Set other properties
        venta.setTotalVenta(ventasRequest.getTotalVenta());
        venta.setEstado(ventasRequest.getEstado());

        // If fechaVenta is not provided, use current date
        venta.setFechaVenta(ventasRequest.getFechaVenta() != null
                ? ventasRequest.getFechaVenta()
                : LocalDate.now());

        return venta;
    }
}