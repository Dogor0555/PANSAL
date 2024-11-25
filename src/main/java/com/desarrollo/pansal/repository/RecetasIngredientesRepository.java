package com.desarrollo.pansal.repository;

import com.desarrollo.pansal.model.RecetasIngredientes;
import com.desarrollo.pansal.model.RecetasIngredientesKey;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RecetasIngredientesRepository extends JpaRepository<RecetasIngredientes, RecetasIngredientesKey> {
    Optional<RecetasIngredientes> findById(RecetasIngredientesKey id);
}
