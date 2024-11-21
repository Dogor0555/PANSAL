package com.desarrollo.pansal.service;

import com.desarrollo.pansal.model.MateriaPrima;
import com.desarrollo.pansal.repository.MateriaPrimaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MateriaPrimaService {

    @Autowired
    private MateriaPrimaRepository materiaPrimaRepository;

    @Transactional(readOnly = true)
    public List<MateriaPrima> getAllMateriasPrimas() {
        return materiaPrimaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<MateriaPrima> getMateriaPrimaById(Long id) {
        return materiaPrimaRepository.findById(id);
    }

    @Transactional
    public MateriaPrima createMateriaPrima(MateriaPrima materiaPrima) {
        if (materiaPrimaRepository.existsByNombreMateriaPrima(materiaPrima.getNombreMateriaPrima())) {
            throw new RuntimeException("Ya existe una materia prima con este nombre");
        }
        return materiaPrimaRepository.save(materiaPrima);
    }

    @Transactional
    public MateriaPrima updateMateriaPrima(Long id, MateriaPrima materiaPrima) {
        MateriaPrima existingMateriaPrima = materiaPrimaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Materia prima no encontrada"));

        // Verificar si el nuevo nombre ya existe y no pertenece a la materia prima actual
        if (!existingMateriaPrima.getNombreMateriaPrima().equals(materiaPrima.getNombreMateriaPrima()) &&
                materiaPrimaRepository.existsByNombreMateriaPrima(materiaPrima.getNombreMateriaPrima())) {
            throw new RuntimeException("Ya existe una materia prima con este nombre");
        }

        existingMateriaPrima.setNombreMateriaPrima(materiaPrima.getNombreMateriaPrima());
        existingMateriaPrima.setUnidadMedida(materiaPrima.getUnidadMedida());

        return materiaPrimaRepository.save(existingMateriaPrima);
    }

    @Transactional
    public void deleteMateriaPrima(Long id) {
        if (!materiaPrimaRepository.existsById(id)) {
            throw new RuntimeException("Materia prima no encontrada");
        }
        materiaPrimaRepository.deleteById(id);
    }
}
