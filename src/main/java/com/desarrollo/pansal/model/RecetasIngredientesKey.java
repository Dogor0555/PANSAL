package com.desarrollo.pansal.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RecetasIngredientesKey implements Serializable {

    private Long idReceta;
    private Long idMateriaPrima;

    public Long getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(Long idReceta) {
        this.idReceta = idReceta;
    }

    public Long getIdMateriaPrima() {
        return idMateriaPrima;
    }

    public void setIdMateriaPrima(Long idMateriaPrima) {
        this.idMateriaPrima = idMateriaPrima;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecetasIngredientesKey that = (RecetasIngredientesKey) o;
        return Objects.equals(idReceta, that.idReceta) && Objects.equals(idMateriaPrima, that.idMateriaPrima);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReceta, idMateriaPrima);
    }
}
