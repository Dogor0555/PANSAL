package com.desarrollo.pansal.controller;

import com.desarrollo.pansal.dto.InventarioRequest;
import com.desarrollo.pansal.model.Inventario;
import com.desarrollo.pansal.service.InventarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    public ResponseEntity<List<Inventario>> getAllInventario() {
        return ResponseEntity.ok(inventarioService.getAllInventario());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInventarioById(@PathVariable Long id) {
        return inventarioService.getInventarioById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/item/{idItem}")
    public ResponseEntity<?> getInventarioByIdItem(@PathVariable Long idItem) {
        return inventarioService.getInventarioByIdItem(idItem)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createInventario(@Valid @RequestBody InventarioRequest inventarioRequest) {
        try {
            Inventario inventario = convertToEntity(inventarioRequest);
            Inventario newInventario = inventarioService.createInventario(inventario);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Item de inventario creado exitosamente");
            response.put("inventario", newInventario);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al crear el item de inventario: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateInventario(@PathVariable Long id,
                                              @Valid @RequestBody InventarioRequest inventarioRequest) {
        try {
            Inventario inventario = convertToEntity(inventarioRequest);
            Inventario updatedInventario = inventarioService.updateInventario(id, inventario);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Item de inventario actualizado exitosamente");
            response.put("inventario", updatedInventario);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al actualizar el item de inventario: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInventario(@PathVariable Long id) {
        try {
            inventarioService.deleteInventario(id);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Item de inventario eliminado exitosamente");

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al eliminar el item de inventario: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    private Inventario convertToEntity(InventarioRequest inventarioRequest) {
        Inventario inventario = new Inventario();
        inventario.setNombreItem(inventarioRequest.getNombreItem());
        inventario.setCantidad(inventarioRequest.getCantidad());
        inventario.setTipoItem(inventarioRequest.getTipoItem());

        // Si es producto o materia prima, asignar el id correspondiente
        if ("Producto".equalsIgnoreCase(inventarioRequest.getTipoItem())) {
            inventario.setIdProducto(inventarioRequest.getIdProducto());
        } else if ("MateriaPrima".equalsIgnoreCase(inventarioRequest.getTipoItem())) {
            inventario.setIdMateriaPrima(inventarioRequest.getIdMateriaPrima());
        }

        return inventario;
    }
}