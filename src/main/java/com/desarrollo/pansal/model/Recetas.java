package com.desarrollo.pansal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Recetas")  // Asegúrate de que el nombre de la tabla sea correcto en la base de datos
public class Recetas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idReceta")  // Aseguramos que coincide con la columna en la base de datos
    private Long idReceta;

    @Column(name = "nombreReceta", nullable = false, length = 100) // Asegúrate de usar el nombre exacto de la columna
    private String nombreReceta;

    @Column(name = "descripcion", nullable = true, length = 255) // Si la descripción es opcional, no agregues "nullable = false"
    private String descripcion;

    public Long getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(Long idReceta) {
        this.idReceta = idReceta;
    }

    public String getNombreReceta() {
        return nombreReceta;
    }

    public void setNombreReceta(String nombreReceta) {
        this.nombreReceta = nombreReceta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Recetas{" +
                "idReceta=" + idReceta +
                ", nombreReceta='" + nombreReceta + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
