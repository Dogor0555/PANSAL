package com.desarrollo.pansal.controller;

import com.desarrollo.pansal.dto.ProveedorRequest;
import com.desarrollo.pansal.model.Proveedores;
import com.desarrollo.pansal.service.ProveedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public ResponseEntity<List<Proveedores>> getAllProveedores() {
        return ResponseEntity.ok(proveedorService.getAllProveedores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProveedorById(@PathVariable Long id) {
        return proveedorService.getProveedorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createProveedor(@Valid @RequestBody ProveedorRequest proveedorRequest) {
        try {
            Proveedores proveedor = convertToEntity(proveedorRequest);
            Proveedores newProveedor = proveedorService.createProveedor(proveedor);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Proveedor creado exitosamente");
            response.put("proveedor", newProveedor);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al crear el proveedor: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProveedor(@PathVariable Long id,
                                             @Valid @RequestBody ProveedorRequest proveedorRequest) {
        try {
            Proveedores proveedor = convertToEntity(proveedorRequest);
            Proveedores updatedProveedor = proveedorService.updateProveedor(id, proveedor);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Proveedor actualizado exitosamente");
            response.put("proveedor", updatedProveedor);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al actualizar el proveedor: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProveedor(@PathVariable Long id) {
        try {
            proveedorService.deleteProveedor(id);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Proveedor eliminado exitosamente");

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al eliminar el proveedor: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    private Proveedores convertToEntity(ProveedorRequest proveedorRequest) {
        Proveedores proveedor = new Proveedores();
        proveedor.setNombreProveedor(proveedorRequest.getNombreProveedor());
        proveedor.setTelefonoProveedor(proveedorRequest.getTelefonoProveedor());
        proveedor.setDireccion(proveedorRequest.getDireccion());
        proveedor.setEmail(proveedorRequest.getEmail());
        return proveedor;
    }
}
