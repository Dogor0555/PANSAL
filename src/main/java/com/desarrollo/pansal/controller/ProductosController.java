package com.desarrollo.pansal.controller;

import com.desarrollo.pansal.dto.ProductosRequest;
import com.desarrollo.pansal.model.Categorias;
import com.desarrollo.pansal.model.Productos;
import com.desarrollo.pansal.model.Recetas;
import com.desarrollo.pansal.service.ProductosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/productos")
public class ProductosController {

    @Autowired
    private ProductosService productosService;

    @GetMapping
    public ResponseEntity<List<Productos>> getAllProductos() {
        return ResponseEntity.ok(productosService.getAllProductos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductoById(@PathVariable Long id) {
        return productosService.getProductoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createProducto(@Valid @RequestBody ProductosRequest productosRequest) {
        try {
            Productos producto = convertToEntity(productosRequest);
            Productos newProducto = productosService.createProducto(producto);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Producto creado exitosamente");
            response.put("producto", newProducto);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al crear el producto: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProducto(@PathVariable Long id,
                                            @Valid @RequestBody ProductosRequest productosRequest) {
        try {
            Productos producto = convertToEntity(productosRequest);
            Productos updatedProducto = productosService.updateProducto(id, producto);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Producto actualizado exitosamente");
            response.put("producto", updatedProducto);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al actualizar el producto: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProducto(@PathVariable Long id) {
        try {
            productosService.deleteProducto(id);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Producto eliminado exitosamente");

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al eliminar el producto: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    private Productos convertToEntity(ProductosRequest productosRequest) {
        Productos producto = new Productos();
        producto.setNombreProducto(productosRequest.getNombreProducto());
        producto.setDescripcion(productosRequest.getDescripcion());
        producto.setPrecioUnitario(productosRequest.getPrecioUnitario());

        // Simula la asignación de IDs referenciales
        Categorias categoria = new Categorias();
        categoria.setIdCategoria(productosRequest.getIdCategoria());
        producto.setCategoria(categoria);

        if (productosRequest.getIdReceta() != null) {
            Recetas receta = new Recetas();
            receta.setIdReceta(productosRequest.getIdReceta());
            producto.setReceta(receta);
        }

        return producto;
    }
}
