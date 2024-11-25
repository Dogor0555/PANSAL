package com.desarrollo.pansal.service;

import com.desarrollo.pansal.model.Productos;
import com.desarrollo.pansal.repository.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductosService {

    @Autowired
    private ProductosRepository productosRepository;

    @Transactional(readOnly = true)
    public List<Productos> getAllProductos() {
        return productosRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Productos> getProductoById(Long id) {
        return productosRepository.findById(id);
    }

    @Transactional
    public Productos createProducto(Productos producto) {
        if (productosRepository.existsByNombreProducto(producto.getNombreProducto())) {
            throw new RuntimeException("Ya existe un producto con este nombre");
        }
        return productosRepository.save(producto);
    }

    @Transactional
    public Productos updateProducto(Long id, Productos producto) {
        Productos existingProducto = productosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Verificar si el nuevo nombre ya existe y no pertenece al producto actual
        if (!existingProducto.getNombreProducto().equals(producto.getNombreProducto()) &&
                productosRepository.existsByNombreProducto(producto.getNombreProducto())) {
            throw new RuntimeException("Ya existe un producto con este nombre");
        }

        existingProducto.setNombreProducto(producto.getNombreProducto());
        existingProducto.setDescripcion(producto.getDescripcion());
        existingProducto.setPrecioUnitario(producto.getPrecioUnitario());
        existingProducto.setCategoria(producto.getCategoria());
        existingProducto.setReceta(producto.getReceta());

        return productosRepository.save(existingProducto);
    }

    @Transactional
    public void deleteProducto(Long id) {
        if (!productosRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado");
        }
        productosRepository.deleteById(id);
    }
}
