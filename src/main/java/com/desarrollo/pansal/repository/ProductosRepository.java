package com.desarrollo.pansal.repository;

import com.desarrollo.pansal.model.Productos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductosRepository extends JpaRepository<Productos, Long> {

    Optional<Productos> findByNombreProducto(String nombreProducto);

    boolean existsByNombreProducto(String nombreProducto);

}
