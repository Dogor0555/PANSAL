package com.desarrollo.pansal.service;

import com.desarrollo.pansal.model.Recetas;
import com.desarrollo.pansal.repository.RecetasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RecetasService {

    @Autowired
    private RecetasRepository recetasRepository;

    @Transactional(readOnly = true)
    public List<Recetas> getAllRecetas() {
        return recetasRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Recetas> getRecetaById(Long id) {
        return recetasRepository.findById(id);
    }

    @Transactional
    public Recetas createReceta(Recetas receta) {
        if (recetasRepository.existsByNombreReceta(receta.getNombreReceta())) {
            throw new RuntimeException("Ya existe una receta con este nombre");
        }
        return recetasRepository.save(receta);
    }

    @Transactional
    public Recetas updateReceta(Long id, Recetas receta) {
        Recetas existingReceta = recetasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

        // Verificar si el nuevo nombre ya existe y no pertenece a la receta actual
        if (!existingReceta.getNombreReceta().equals(receta.getNombreReceta()) &&
                recetasRepository.existsByNombreReceta(receta.getNombreReceta())) {
            throw new RuntimeException("Ya existe una receta con este nombre");
        }

        existingReceta.setNombreReceta(receta.getNombreReceta());
        existingReceta.setDescripcion(receta.getDescripcion());

        return recetasRepository.save(existingReceta);
    }

    @Transactional
    public void deleteReceta(Long id) {
        if (!recetasRepository.existsById(id)) {
            throw new RuntimeException("Receta no encontrada");
        }
        recetasRepository.deleteById(id);
    }
}
