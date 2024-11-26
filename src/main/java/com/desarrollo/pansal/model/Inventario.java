package com.desarrollo.pansal.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Inventario")
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idInventario")
    private Long idInventario;

    @Column(name = "idItem")
    private Long idItem;

    @Column(name = "nombreItem", nullable = false, length = 100)
    private String nombreItem;

    @Column(name = "cantidad", precision = 10, scale = 2)
    private BigDecimal cantidad;

    @Column(name = "tipoItem", nullable = false, length = 50)
    private String tipoItem;

    @Column(name = "idProducto")
    private Long idProducto;

    @Column(name = "idMateriaPrima")
    private Long idMateriaPrima;

    // Getters y Setters
    public Long getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(Long idInventario) {
        this.idInventario = idInventario;
    }

    public Long getIdItem() {
        return idItem;
    }

    public void setIdItem(Long idItem) {
        this.idItem = idItem;
    }

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

    @Override
    public String toString() {
        return "Inventario{" +
                "idInventario=" + idInventario +
                ", idItem=" + idItem +
                ", nombreItem='" + nombreItem + '\'' +
                ", cantidad=" + cantidad +
                ", tipoItem='" + tipoItem + '\'' +
                ", idProducto=" + idProducto +
                ", idMateriaPrima=" + idMateriaPrima +
                '}';
    }
}
