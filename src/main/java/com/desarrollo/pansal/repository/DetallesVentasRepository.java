package com.desarrollo.pansal.repository;

import com.desarrollo.pansal.model.DetallesVentas;
import com.desarrollo.pansal.model.Ventas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallesVentasRepository extends JpaRepository<DetallesVentas, Long> {
    void deleteByVenta(Ventas venta);
}