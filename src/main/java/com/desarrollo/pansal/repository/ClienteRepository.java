package com.desarrollo.pansal.repository;

import com.desarrollo.pansal.model.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Clientes, Long> {
    Optional<Clientes> findByEmail(String email);
    boolean existsByEmail(String email);
}