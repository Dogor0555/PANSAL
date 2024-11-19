package com.desarrollo.pansal.service;

import com.desarrollo.pansal.model.Proveedores;
import com.desarrollo.pansal.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Transactional(readOnly = true)
    public List<Proveedores> getAllProveedores() {
        return proveedorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Proveedores> getProveedorById(Long id) {
        return proveedorRepository.findById(id);
    }

    @Transactional
    public Proveedores createProveedor(Proveedores proveedor) {
        if (proveedorRepository.existsByEmail(proveedor.getEmail())) {
            throw new RuntimeException("Ya existe un proveedor con este email");
        }
        return proveedorRepository.save(proveedor);
    }

    @Transactional
    public Proveedores updateProveedor(Long id, Proveedores proveedor) {
        Proveedores existingProveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        // Verificar si el nuevo email ya existe y no pertenece al proveedor actual
        if (!existingProveedor.getEmail().equals(proveedor.getEmail()) &&
                proveedorRepository.existsByEmail(proveedor.getEmail())) {
            throw new RuntimeException("Ya existe un proveedor con este email");
        }

        existingProveedor.setNombreProveedor(proveedor.getNombreProveedor());
        existingProveedor.setTelefonoProveedor(proveedor.getTelefonoProveedor());
        existingProveedor.setDireccion(proveedor.getDireccion());
        existingProveedor.setEmail(proveedor.getEmail());

        return proveedorRepository.save(existingProveedor);
    }

    @Transactional
    public void deleteProveedor(Long id) {
        if (!proveedorRepository.existsById(id)) {
            throw new RuntimeException("Proveedor no encontrado");
        }
        proveedorRepository.deleteById(id);
    }
}
