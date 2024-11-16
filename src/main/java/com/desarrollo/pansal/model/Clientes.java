package com.desarrollo.pansal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Clientes")  // Asegúrate de que el nombre de la tabla sea correcto en la base de datos

public class Clientes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCliente")  // Aseguramos que coincide con la columna en la base de datos
    private Long idCliente;

    @Column(name = "nombreCliente", nullable = false, length = 100) // Asegúrate de usar el nombre exacto de la columna
    private String nombreCliente;

    @Column(name = "telefonoCliente", nullable = false, length = 15) // Asegúrate de usar el nombre exacto de la columna
    private String telefonoCliente;

    @Column(name = "direccion", nullable = false, length = 255) // Asegúrate de usar el nombre exacto de la columna
    private String direccion;

    @Column(name = "email", nullable = false, length = 100) // Asegúrate de usar el nombre exacto de la columna
    private String email;

    @Column(name = "diasCredito", nullable = true) // Puedes cambiar nullable a false si es obligatorio
    private Integer diasCredito;

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getDiasCredito() {
        return diasCredito;
    }

    public void setDiasCredito(Integer diasCredito) {
        this.diasCredito = diasCredito;
    }

    @Override
    public String toString() {
        return "Clientes{" +
                "idCliente=" + idCliente +
                ", nombreCliente='" + nombreCliente + '\'' +
                ", telefonoCliente='" + telefonoCliente + '\'' +
                ", direccion='" + direccion + '\'' +
                ", email='" + email + '\'' +
                ", diasCredito=" + diasCredito +
                '}';
    }
}
