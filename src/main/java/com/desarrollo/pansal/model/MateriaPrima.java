package com.desarrollo.pansal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "MateriaPrima")  // Nombre de la tabla en la base de datos
public class MateriaPrima {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY para auto-incrementar
    @Column(name = "idMateriaPrima")  // Aseguramos que coincide con la columna de la tabla
    private Long idMateriaPrima;

    @Column(name = "nombreMateriaPrima", nullable = false, length = 100) // Restricción y longitud
    private String nombreMateriaPrima;

    @Column(name = "unidadMedida", nullable = false, length = 50) // Restricción y longitud
    private String unidadMedida;

    // Getters y Setters
    public Long getIdMateriaPrima() {
        return idMateriaPrima;
    }

    public void setIdMateriaPrima(Long idMateriaPrima) {
        this.idMateriaPrima = idMateriaPrima;
    }

    public String getNombreMateriaPrima() {
        return nombreMateriaPrima;
    }

    public void setNombreMateriaPrima(String nombreMateriaPrima) {
        this.nombreMateriaPrima = nombreMateriaPrima;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    @Override
    public String toString() {
        return "MateriaPrima{" +
                "idMateriaPrima=" + idMateriaPrima +
                ", nombreMateriaPrima='" + nombreMateriaPrima + '\'' +
                ", unidadMedida='" + unidadMedida + '\'' +
                '}';
    }
}
