package com.desarrollo.pansal.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class VentasRequest {
    @NotNull(message = "El cliente es obligatorio")
    private Long idCliente;

    @NotNull(message = "El usuario es obligatorio")
    private Long idUsuario;

    @NotBlank(message = "El estado de la venta es obligatorio")
    @Size(max = 50, message = "El estado no puede exceder los 50 caracteres")
    private String estado;

    private LocalDate fechaVenta;

    @NotNull(message = "El total de venta es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El total de venta debe ser mayor a 0")
    @Digits(integer = 10, fraction = 2, message = "El total de venta debe tener como máximo 10 dígitos enteros y 2 decimales")
    private BigDecimal totalVenta;

    @NotEmpty(message = "Debe incluir al menos un detalle de venta")
    @Valid
    private List<DetallesVentaItem> detalles;

    public static class DetallesVentaItem {
        @NotNull(message = "El producto es obligatorio")
        private Long idProducto;

        @NotNull(message = "La cantidad es obligatoria")
        @DecimalMin(value = "0.0", inclusive = false, message = "La cantidad debe ser mayor a 0")
        @Digits(integer = 10, fraction = 2, message = "La cantidad debe tener como máximo 10 dígitos enteros y 2 decimales")
        private BigDecimal cantidad;

        @NotNull(message = "El precio unitario es obligatorio")
        @DecimalMin(value = "0.0", inclusive = false, message = "El precio unitario debe ser mayor a 0")
        @Digits(integer = 10, fraction = 2, message = "El precio unitario debe tener como máximo 10 dígitos enteros y 2 decimales")
        private BigDecimal precioUnitario;

        // Getters
        public Long getIdProducto() {
            return idProducto;
        }

        public BigDecimal getCantidad() {
            return cantidad;
        }

        public BigDecimal getPrecioUnitario() {
            return precioUnitario;
        }

        // Setters
        public void setIdProducto(Long idProducto) {
            this.idProducto = idProducto;
        }

        public void setCantidad(BigDecimal cantidad) {
            this.cantidad = cantidad;
        }

        public void setPrecioUnitario(BigDecimal precioUnitario) {
            this.precioUnitario = precioUnitario;
        }
    }

    // Getters
    public Long getIdCliente() {
        return idCliente;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public String getEstado() {
        return estado;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    public List<DetallesVentaItem> getDetalles() {
        return detalles;
    }

    // Setters
    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

    public void setDetalles(List<DetallesVentaItem> detalles) {
        this.detalles = detalles;
    }
}