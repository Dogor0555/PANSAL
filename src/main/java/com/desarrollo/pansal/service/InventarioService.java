package com.desarrollo.pansal.service;

import com.desarrollo.pansal.model.Inventario;
import com.desarrollo.pansal.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Transactional(readOnly = true)
    public List<Inventario> getAllInventario() {
        return inventarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Inventario> getInventarioById(Long id) {
        return inventarioRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Inventario> getInventarioByIdItem(Long idItem) {
        return inventarioRepository.findByIdItem(idItem);
    }

    @Transactional
    public Inventario createInventario(Inventario inventario) {
        // Asegurarse de que no haya ID establecido
        inventario.setIdItem(null);

        if (inventarioRepository.existsByNombreItem(inventario.getNombreItem())) {
            throw new RuntimeException("Ya existe un item en inventario con este nombre");
        }

        // Asignar idProducto o idMateriaPrima dependiendo del tipoItem
        if ("Producto".equalsIgnoreCase(inventario.getTipoItem())) {
            inventario.setIdProducto(inventario.getIdProducto());
            inventario.setIdMateriaPrima(null);  // Si es un producto, la materia prima es nula
        } else if ("MateriaPrima".equalsIgnoreCase(inventario.getTipoItem())) {
            inventario.setIdMateriaPrima(inventario.getIdMateriaPrima());
            inventario.setIdProducto(null);  // Si es materia prima, el producto es nulo
        }

        return inventarioRepository.save(inventario);
    }



    @Transactional
    public Inventario updateInventario(Long id, Inventario inventario) {
        Inventario existingInventario = inventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item de inventario no encontrado"));

        // Verificar si el nuevo nombre ya existe y no pertenece al item actual
        if (!existingInventario.getNombreItem().equals(inventario.getNombreItem()) &&
                inventarioRepository.existsByNombreItem(inventario.getNombreItem())) {
            throw new RuntimeException("Ya existe un item en inventario con este nombre");
        }

        existingInventario.setIdItem(inventario.getIdItem());
        existingInventario.setNombreItem(inventario.getNombreItem());
        existingInventario.setCantidad(inventario.getCantidad());
        existingInventario.setTipoItem(inventario.getTipoItem());

        return inventarioRepository.save(existingInventario);
    }

    @Transactional
    public void deleteInventario(Long id) {
        if (!inventarioRepository.existsById(id)) {
            throw new RuntimeException("Item de inventario no encontrado");
        }
        inventarioRepository.deleteById(id);
    }
}