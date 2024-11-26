package com.desarrollo.pansal.repository;

import com.desarrollo.pansal.model.Ventas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentasRepository extends JpaRepository<Ventas, Long> {
}