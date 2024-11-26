package com.desarrollo.pansal.repository;

import com.desarrollo.pansal.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    Optional<Inventario> findByNombreItem(String nombreItem);

    boolean existsByNombreItem(String nombreItem);

    Optional<Inventario> findByIdItem(Long idItem);
}