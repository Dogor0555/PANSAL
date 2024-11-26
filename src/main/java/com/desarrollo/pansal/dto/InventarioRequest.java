package com.desarrollo.pansal.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class InventarioRequest {
    @NotBlank(message = "El nombre del item es obligatorio")
    @Size(max = 100, message = "El nombre del item no puede exceder los 100 caracteres")
    private String nombreItem;

    @NotNull(message = "La cantidad es obligatoria")
    @DecimalMin(value = "0.0", message = "La cantidad debe ser mayor o igual a 0")
    @Digits(integer = 10, fraction = 2, message = "La cantidad debe tener como máximo 10 dígitos enteros y 2 decimales")
    private BigDecimal cantidad;

    @NotBlank(message = "El tipo de item es obligatorio")
    @Size(max = 50, message = "El tipo de item no puede exceder los 50 caracteres")
    private String tipoItem;

    private Long idProducto; // Este campo solo se utilizará si el tipoItem es "Producto"
    private Long idMateriaPrima; // Este campo solo se utilizará si el tipoItem es "MateriaPrima"

    // Getters y Setters
    public String getNombreItem() {
        return nombreItem;
    }

    public void setNombreItem(String nombreItem) {
        this.nombreItem = nombreItem;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipoItem() {
        return tipoItem;
    }

    public void setTipoItem(String tipoItem) {
        this.tipoItem = tipoItem;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public Long getIdMateriaPrima() {
        return idMateriaPrima;
    }

    public void setIdMateriaPrima(Long idMateriaPrima) {
        this.idMateriaPrima = idMateriaPrima;
    }
}
