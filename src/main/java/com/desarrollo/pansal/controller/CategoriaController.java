package com.desarrollo.pansal.controller;

import com.desarrollo.pansal.dto.CategoriasRequest;
import com.desarrollo.pansal.model.Categorias;
import com.desarrollo.pansal.service.CategoriasService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/categorias")
public class CategoriaController {

    @Autowired
    private CategoriasService categoriasService;

    @GetMapping
    public ResponseEntity<List<Categorias>> getAllCategorias() {
        return ResponseEntity.ok(categoriasService.getAllCategorias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoriaById(@PathVariable Long id) {
        return categoriasService.getCategoriaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createCategoria(@Valid @RequestBody CategoriasRequest categoriasRequest) {
        try {
            Categorias categorias = convertToEntity(categoriasRequest);
            Categorias newCategoria = categoriasService.createCategoria(categorias);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Categoria creada exitosamente");
            response.put("categoria", newCategoria);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al crear el cliente: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategoria(@PathVariable Long id,
                                           @Valid @RequestBody CategoriasRequest categoriasRequest) {
        try {
            Categorias categorias = convertToEntity(categoriasRequest);
            Categorias updatedCategoria = categoriasService.updateCategoria(id, categorias);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Categoria actualizada exitosamente");
            response.put("categoria", updatedCategoria);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al actualizar el categoria: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoria(@PathVariable Long id) {
        try {
            categoriasService.deleteCategoria(id);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Categoria eliminada exitosamente");

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al eliminar el categoria: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    private Categorias convertToEntity(CategoriasRequest categoriasRequest) {
        Categorias categorias = new Categorias();
        categorias.setNombreCategoria(categoriasRequest.getNombreCategoria());

        return categorias;
    }
}