package com.desarrollo.pansal.service;

import com.desarrollo.pansal.dto.VentasRequest;
import com.desarrollo.pansal.model.DetallesVentas;
import com.desarrollo.pansal.model.Productos;
import com.desarrollo.pansal.model.Ventas;
import com.desarrollo.pansal.repository.ClienteRepository;
import com.desarrollo.pansal.repository.ProductosRepository;
import com.desarrollo.pansal.repository.UsuarioRepository;
import com.desarrollo.pansal.repository.VentasRepository;
import com.desarrollo.pansal.repository.DetallesVentasRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VentasService {

    @Autowired
    private VentasRepository ventasRepository;

    @Autowired
    private DetallesVentasRepository detallesVentasRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductosRepository productosRepository;

    public List<Ventas> getAllVentas() {
        return ventasRepository.findAll();
    }

    public Optional<Ventas> getVentaById(Long id) {
        return ventasRepository.findById(id);
    }

    @Transactional
    public Ventas createVenta(Ventas venta, List<VentasRequest.DetallesVentaItem> detallesRequest) {
        // Validate cliente and usuario exist
        clienteRepository.findById(venta.getCliente().getIdCliente())
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        usuarioRepository.findById(venta.getUsuario().getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        // Validate and prepare detalles
        List<DetallesVentas> detallesVentas = prepareDetallesVentas(venta, detallesRequest);

        // Recalculate total sale
        BigDecimal totalCalculado = calculateTotalVenta(detallesVentas);
        venta.setTotalVenta(totalCalculado);

        // Save venta
        Ventas savedVenta = ventasRepository.save(venta);

        // Save detalles
        detallesVentas.forEach(detalle -> {
            detalle.setVenta(savedVenta);
            detallesVentasRepository.save(detalle);
        });

        return savedVenta;
    }

    @Transactional
    public Ventas updateVenta(Long id, Ventas venta, List<VentasRequest.DetallesVentaItem> detallesRequest) {
        // Find existing venta
        Ventas existingVenta = ventasRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Venta no encontrada"));

        // Validate cliente and usuario exist
        clienteRepository.findById(venta.getCliente().getIdCliente())
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        usuarioRepository.findById(venta.getUsuario().getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        // Update venta fields
        existingVenta.setCliente(venta.getCliente());
        existingVenta.setUsuario(venta.getUsuario());
        existingVenta.setEstado(venta.getEstado());
        existingVenta.setFechaVenta(venta.getFechaVenta());

        // Remove existing detalles
        detallesVentasRepository.deleteByVenta(existingVenta);

        // Prepare and save new detalles
        List<DetallesVentas> detallesVentas = prepareDetallesVentas(existingVenta, detallesRequest);

        // Recalculate total sale
        BigDecimal totalCalculado = calculateTotalVenta(detallesVentas);
        existingVenta.setTotalVenta(totalCalculado);

        // Save updated venta
        Ventas updatedVenta = ventasRepository.save(existingVenta);

        // Save new detalles
        detallesVentas.forEach(detalle -> {
            detalle.setVenta(updatedVenta);
            detallesVentasRepository.save(detalle);
        });

        return updatedVenta;
    }

    @Transactional
    public void deleteVenta(Long id) {
        Ventas venta = ventasRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Venta no encontrada"));

        // First, delete all related detalles
        detallesVentasRepository.deleteByVenta(venta);

        // Then delete the venta
        ventasRepository.delete(venta);
    }

    private List<DetallesVentas> prepareDetallesVentas(Ventas venta, List<VentasRequest.DetallesVentaItem> detallesRequest) {
        return detallesRequest.stream().map(detalle -> {
            // Validate producto exists
            Productos producto = productosRepository.findById(detalle.getIdProducto())
                    .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado: " + detalle.getIdProducto()));

            DetallesVentas detalleVenta = new DetallesVentas();
            detalleVenta.setVenta(venta);
            detalleVenta.setProducto(producto);
            detalleVenta.setCantidad(detalle.getCantidad());
            detalleVenta.setPrecioUnitario(detalle.getPrecioUnitario());

            return detalleVenta;
        }).collect(Collectors.toList());
    }

    private BigDecimal calculateTotalVenta(List<DetallesVentas> detalles) {
        return detalles.stream()
                .map(detalle -> detalle.getPrecioUnitario().multiply(detalle.getCantidad()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}