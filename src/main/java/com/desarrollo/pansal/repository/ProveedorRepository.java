package com.desarrollo.pansal.repository;

import com.desarrollo.pansal.model.Proveedores;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProveedorRepository extends JpaRepository<Proveedores, Long> {
    Optional<Proveedores> findByEmail(String email);
    boolean existsByEmail(String email);
}
