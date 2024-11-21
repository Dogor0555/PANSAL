package com.desarrollo.pansal.repository;

import com.desarrollo.pansal.model.MateriaPrima;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MateriaPrimaRepository extends JpaRepository<MateriaPrima, Long> {
    Optional<MateriaPrima> findByNombreMateriaPrima(String nombreMateriaPrima);
    boolean existsByNombreMateriaPrima(String nombreMateriaPrima);
}
