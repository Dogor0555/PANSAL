package com.desarrollo.pansal.service;

import com.desarrollo.pansal.model.Categorias;
import com.desarrollo.pansal.repository.CategoriasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriasService {

    @Autowired
    private CategoriasRepository categoriasRepository;

    @Transactional(readOnly = true)
    public List<Categorias> getAllCategorias() {
        return categoriasRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Categorias> getCategoriaById(Long id) {
        return categoriasRepository.findById(id);
    }

    @Transactional
    public Categorias createCategoria(Categorias categorias) {
        if (categoriasRepository.existsByNombreCategoria(categorias.getNombreCategoria())) {
            throw new RuntimeException("Ya existe una categoria con este nombre");
        }
        return categoriasRepository.save(categorias);
    }

    @Transactional
    public Categorias updateCategoria(Long id, Categorias categorias) {
        Categorias existingCategoria = categoriasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        // Verificar si el nuevo nombre ya existe y no pertenece a la categoria actual
        if (!existingCategoria.getNombreCategoria().equals(categorias.getNombreCategoria()) &&
                categoriasRepository.existsByNombreCategoria(categorias.getNombreCategoria())) {
            throw new RuntimeException("Ya existe una cliente con este nombre");
        }

        existingCategoria.setNombreCategoria(categorias.getNombreCategoria());


        return categoriasRepository.save(existingCategoria);
    }

    @Transactional
    public void deleteCategoria(Long id) {
        if (!categoriasRepository.existsById(id)) {
            throw new RuntimeException("Categoria no encontrada");
        }
        categoriasRepository.deleteById(id);
    }
}