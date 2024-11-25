package com.desarrollo.pansal.controller;

import com.desarrollo.pansal.model.RecetasIngredientes;
import com.desarrollo.pansal.model.RecetasIngredientesKey;
import com.desarrollo.pansal.service.RecetasIngredientesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth/recetas-ingredientes")
public class RecetasIngredientesController {

    @Autowired
    private RecetasIngredientesService recetasIngredientesService;

    // Obtener un ingrediente de receta por ID (GET)
    @GetMapping("/{idReceta}/{idMateriaPrima}")
    public ResponseEntity<RecetasIngredientes> obtenerRecetaIngrediente(
            @PathVariable Long idReceta,
            @PathVariable Long idMateriaPrima) {

        RecetasIngredientesKey key = new RecetasIngredientesKey();
        key.setIdReceta(idReceta);
        key.setIdMateriaPrima(idMateriaPrima);

        Optional<RecetasIngredientes> recetaIngrediente = recetasIngredientesService.obtenerPorId(key);

        if (recetaIngrediente.isPresent()) {
            return new ResponseEntity<>(recetaIngrediente.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Obtener todos los ingredientes de receta (GET)
    @GetMapping
    public ResponseEntity<List<RecetasIngredientes>> obtenerTodosRecetasIngredientes() {
        List<RecetasIngredientes> recetasIngredientes = recetasIngredientesService.obtenerTodos();

        if (recetasIngredientes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(recetasIngredientes, HttpStatus.OK);
    }

    // Crear un nuevo ingrediente de receta (POST)
    @PostMapping
    public ResponseEntity<RecetasIngredientes> crearRecetaIngrediente(@RequestBody RecetasIngredientes recetasIngredientes) {
        RecetasIngredientes nuevoRecetaIngrediente = recetasIngredientesService.crearRecetaIngrediente(recetasIngredientes);
        return new ResponseEntity<>(nuevoRecetaIngrediente, HttpStatus.CREATED);
    }

    // Actualizar un ingrediente de receta existente (PUT)
    @PutMapping
    public ResponseEntity<RecetasIngredientes> actualizarRecetaIngrediente(@RequestBody RecetasIngredientes recetasIngredientes) {
        try {
            RecetasIngredientes actualizado = recetasIngredientesService.actualizarRecetaIngrediente(recetasIngredientes);
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar un ingrediente de receta (DELETE)
    @DeleteMapping("/{idReceta}/{idMateriaPrima}")
    public ResponseEntity<Void> eliminarRecetaIngrediente(
            @PathVariable Long idReceta,
            @PathVariable Long idMateriaPrima) {

        RecetasIngredientesKey key = new RecetasIngredientesKey();
        key.setIdReceta(idReceta);
        key.setIdMateriaPrima(idMateriaPrima);

        try {
            recetasIngredientesService.eliminarRecetaIngrediente(key);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
