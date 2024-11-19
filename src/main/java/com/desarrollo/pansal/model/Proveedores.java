package com.desarrollo.pansal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Proveedores")  // Asegúrate de que coincide con el nombre de la tabla en la base de datos

public class Proveedores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProveedor")  // Asegúrate de que coincide con la columna en la base de datos
    private Long idProveedor;

    @Column(name = "nombreProveedor", nullable = false, length = 100)  // Asegúrate de usar el nombre exacto de la columna
    private String nombreProveedor;

    @Column(name = "telefonoProveedor", nullable = false, length = 15)  // Asegúrate de usar el nombre exacto de la columna
    private String telefonoProveedor;

    @Column(name = "direccion", nullable = false, length = 255)  // Asegúrate de usar el nombre exacto de la columna
    private String direccion;

    @Column(name = "email", nullable = false, length = 100)  // Asegúrate de usar el nombre exacto de la columna
    private String email;

    public Long getIdProveedor() {
        return idProveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public String getTelefonoProveedor() {
        return telefonoProveedor;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setIdProveedor(Long idProveedor) {
        this.idProveedor = idProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public void setTelefonoProveedor(String telefonoProveedor) {
        this.telefonoProveedor = telefonoProveedor;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Proveedores{" +
                "idProveedor=" + idProveedor +
                ", nombreProveedor='" + nombreProveedor + '\'' +
                ", telefonoProveedor='" + telefonoProveedor + '\'' +
                ", direccion='" + direccion + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
