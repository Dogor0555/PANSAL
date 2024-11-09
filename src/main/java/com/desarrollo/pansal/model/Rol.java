package com.desarrollo.pansal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "RolesUsuario")  // Asegúrate de que el nombre de la tabla sea correcto en la base de datos
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRol")  // Aseguramos que coincide con la columna en la base de datos
    private Long idRol;

    @Column(name = "nombreRol", nullable = false)  // Aquí puedes agregar otras propiedades de Rol
    private String nombreRol;

    // Getters y setters
    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    @Override
    public String toString() {
        return "Rol{idRol=" + idRol + ", nombreRol='" + nombreRol + "'}";
    }
}
