package com.desarrollo.pansal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Categorias")  // Asegúrate de que el nombre de la tabla sea correcto en la base de datos

public class Categorias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCategoria")  // Aseguramos que coincide con la columna en la base de datos
    private Long idCategoria;

    @Column(name = "nombreCategoria", nullable = false, length = 50) // Asegúrate de usar el nombre exacto de la columna
    private String nombreCategoria;

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    @Override
    public String toString() {
        return "Categorias{" +
                "idCategoria=" + idCategoria +
                ", nombreCategoria='" + nombreCategoria + '\'' +
                '}';
    }
}
