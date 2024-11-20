package com.desarrollo.pansal.repository;

import com.desarrollo.pansal.model.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CategoriasRepository extends JpaRepository<Categorias, Long> {
    Optional<Categorias> findByNombreCategoria(String nombreCategoria);
    boolean existsByNombreCategoria(String nombreCategoria);
}