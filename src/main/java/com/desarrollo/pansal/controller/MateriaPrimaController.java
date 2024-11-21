package com.desarrollo.pansal.controller;

import com.desarrollo.pansal.dto.MateriaPrimaRequest;
import com.desarrollo.pansal.model.MateriaPrima;
import com.desarrollo.pansal.service.MateriaPrimaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/materiasPrimas")
public class MateriaPrimaController {

    @Autowired
    private MateriaPrimaService materiaPrimaService;

    @GetMapping
    public ResponseEntity<List<MateriaPrima>> getAllMateriasPrimas() {
        return ResponseEntity.ok(materiaPrimaService.getAllMateriasPrimas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMateriaPrimaById(@PathVariable Long id) {
        return materiaPrimaService.getMateriaPrimaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createMateriaPrima(@Valid @RequestBody MateriaPrimaRequest materiaPrimaRequest) {
        try {
            MateriaPrima materiaPrima = convertToEntity(materiaPrimaRequest);
            MateriaPrima newMateriaPrima = materiaPrimaService.createMateriaPrima(materiaPrima);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Materia prima creada exitosamente");
            response.put("materiaPrima", newMateriaPrima);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al crear la materia prima: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMateriaPrima(@PathVariable Long id,
                                                @Valid @RequestBody MateriaPrimaRequest materiaPrimaRequest) {
        try {
            MateriaPrima materiaPrima = convertToEntity(materiaPrimaRequest);
            MateriaPrima updatedMateriaPrima = materiaPrimaService.updateMateriaPrima(id, materiaPrima);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Materia prima actualizada exitosamente");
            response.put("materiaPrima", updatedMateriaPrima);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al actualizar la materia prima: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMateriaPrima(@PathVariable Long id) {
        try {
            materiaPrimaService.deleteMateriaPrima(id);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Materia prima eliminada exitosamente");

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al eliminar la materia prima: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    private MateriaPrima convertToEntity(MateriaPrimaRequest materiaPrimaRequest) {
        MateriaPrima materiaPrima = new MateriaPrima();
        materiaPrima.setNombreMateriaPrima(materiaPrimaRequest.getNombreMateriaPrima());
        materiaPrima.setUnidadMedida(materiaPrimaRequest.getUnidadMedida());
        return materiaPrima;
    }
}
