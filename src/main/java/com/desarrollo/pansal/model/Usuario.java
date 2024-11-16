package com.desarrollo.pansal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Usuarios")  // Asegúrate de que el nombre de la tabla sea correcto en la base de datos
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")  // Aseguramos que coincide con la columna en la base de datos
    private Long idUsuario;

    @Column(name = "nombreUsuario", nullable = false, length = 100) // Asegúrate de usar el nombre exacto de la columna
    private String nombreUsuario;

    @Column(name = "contrasena", nullable = false) // Añadí 'nullable = false' para indicar que no debe ser nulo
    private String contrasena;

    @Column(name = "fotoPerfil")  // Si la foto es opcional, no es necesario nullable = false
    private String fotoPerfil;

    @ManyToOne(fetch = FetchType.EAGER)  // Cambié a EAGER para cargar el Rol junto con el Usuario
    @JoinColumn(name = "idRol", referencedColumnName = "idRol", nullable = false)  // Referencia explícita al nombre de la columna en la tabla 'Rol'
    private Rol rol;

    // Getters y setters
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    // Método toString para fácil visualización
    @Override
    public String toString() {
        return "Usuario{idUsuario=" + idUsuario + ", nombreUsuario='" + nombreUsuario + "', fotoPerfil='" + fotoPerfil + "', rol=" + rol + '}';
    }
}
