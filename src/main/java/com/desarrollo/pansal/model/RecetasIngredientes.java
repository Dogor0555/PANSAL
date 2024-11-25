package com.desarrollo.pansal.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "RecetasIngredientes")
public class RecetasIngredientes implements Serializable {

    @EmbeddedId
    private RecetasIngredientesKey id;

    @ManyToOne
    @MapsId("idReceta")
    @JoinColumn(name = "idReceta")
    private Recetas receta;

    @ManyToOne
    @MapsId("idMateriaPrima")
    @JoinColumn(name = "idMateriaPrima")
    private MateriaPrima materiaPrima;

    @Column(name = "cantidad", nullable = false)
    private Double cantidad;

    public RecetasIngredientesKey getId() {
        return id;
    }

    public void setId(RecetasIngredientesKey id) {
        this.id = id;
    }

    public Recetas getReceta() {
        return receta;
    }

    public void setReceta(Recetas receta) {
        this.receta = receta;
    }

    public MateriaPrima getMateriaPrima() {
        return materiaPrima;
    }

    public void setMateriaPrima(MateriaPrima materiaPrima) {
        this.materiaPrima = materiaPrima;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }
}
