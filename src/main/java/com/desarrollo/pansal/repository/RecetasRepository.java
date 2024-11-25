package com.desarrollo.pansal.repository;

import com.desarrollo.pansal.model.Recetas;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RecetasRepository extends JpaRepository<Recetas, Long> {
    Optional<Recetas> findByNombreReceta(String nombreReceta);
    boolean existsByNombreReceta(String nombreReceta);
}
