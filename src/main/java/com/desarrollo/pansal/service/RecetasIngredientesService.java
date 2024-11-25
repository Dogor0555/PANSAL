package com.desarrollo.pansal.service;

import com.desarrollo.pansal.model.RecetasIngredientes;
import com.desarrollo.pansal.model.RecetasIngredientesKey;
import com.desarrollo.pansal.repository.RecetasIngredientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecetasIngredientesService {

    @Autowired
    private RecetasIngredientesRepository recetasIngredientesRepository;

    // Obtener por ID
    public Optional<RecetasIngredientes> obtenerPorId(RecetasIngredientesKey key) {
        return recetasIngredientesRepository.findById(key);
    }

    // Obtener todos los ingredientes de recetas
    public List<RecetasIngredientes> obtenerTodos() {
        return recetasIngredientesRepository.findAll();
    }

    // Crear o agregar un nuevo ingrediente a la receta (POST)
    public RecetasIngredientes crearRecetaIngrediente(RecetasIngredientes recetasIngredientes) {
        return recetasIngredientesRepository.save(recetasIngredientes);
    }

    // Actualizar un ingrediente de receta existente (PUT)
    public RecetasIngredientes actualizarRecetaIngrediente(RecetasIngredientes recetasIngredientes) {
        if (recetasIngredientesRepository.existsById(recetasIngredientes.getId())) {
            return recetasIngredientesRepository.save(recetasIngredientes);
        } else {
            throw new RuntimeException("RecetaIngrediente no encontrado");
        }
    }

    // Eliminar un ingrediente de receta (DELETE)
    public void eliminarRecetaIngrediente(RecetasIngredientesKey key) {
        if (recetasIngredientesRepository.existsById(key)) {
            recetasIngredientesRepository.deleteById(key);
        } else {
            throw new RuntimeException("RecetaIngrediente no encontrado");
        }
    }
}
