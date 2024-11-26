package com.desarrollo.pansal.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Ventas")
public class Ventas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idVenta")
    private Long idVenta;

    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Clientes cliente;

    @Column(name = "totalVenta", precision = 10, scale = 2)
    private BigDecimal totalVenta;

    @Column(name = "estado", length = 50)
    private String estado;

    @Column(name = "fechaVenta")
    private LocalDate fechaVenta;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    // Getters and Setters
    public Long getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Long idVenta) {
        this.idVenta = idVenta;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Ventas{" +
                "idVenta=" + idVenta +
                ", totalVenta=" + totalVenta +
                ", estado='" + estado + '\'' +
                ", fechaVenta=" + fechaVenta +
                '}';
    }
}

