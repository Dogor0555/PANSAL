package com.desarrollo.pansal.controller;

import com.desarrollo.pansal.dto.RecetasRequest;
import com.desarrollo.pansal.model.Recetas;
import com.desarrollo.pansal.service.RecetasService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/recetas")
public class RecetasController {

    @Autowired
    private RecetasService recetasService;

    @GetMapping
    public ResponseEntity<List<Recetas>> getAllRecetas() {
        return ResponseEntity.ok(recetasService.getAllRecetas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRecetaById(@PathVariable Long id) {
        return recetasService.getRecetaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createReceta(@Valid @RequestBody RecetasRequest recetasRequest) {
        try {
            Recetas receta = convertToEntity(recetasRequest);
            Recetas newReceta = recetasService.createReceta(receta);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Receta creada exitosamente");
            response.put("receta", newReceta);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al crear la receta: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReceta(@PathVariable Long id,
                                          @Valid @RequestBody RecetasRequest recetasRequest) {
        try {
            Recetas receta = convertToEntity(recetasRequest);
            Recetas updatedReceta = recetasService.updateReceta(id, receta);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Receta actualizada exitosamente");
            response.put("receta", updatedReceta);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al actualizar la receta: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReceta(@PathVariable Long id) {
        try {
            recetasService.deleteReceta(id);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Receta eliminada exitosamente");

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al eliminar la receta: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    private Recetas convertToEntity(RecetasRequest recetasRequest) {
        Recetas receta = new Recetas();
        receta.setNombreReceta(recetasRequest.getNombreReceta());
        receta.setDescripcion(recetasRequest.getDescripcion());

        return receta;
    }
}
