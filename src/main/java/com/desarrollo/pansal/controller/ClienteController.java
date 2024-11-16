package com.desarrollo.pansal.controller;

import com.desarrollo.pansal.dto.ClienteRequest;
import com.desarrollo.pansal.model.Clientes;
import com.desarrollo.pansal.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Clientes>> getAllClientes() {
        return ResponseEntity.ok(clienteService.getAllClientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClienteById(@PathVariable Long id) {
        return clienteService.getClienteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createCliente(@Valid @RequestBody ClienteRequest clienteRequest) {
        try {
            Clientes cliente = convertToEntity(clienteRequest);
            Clientes newCliente = clienteService.createCliente(cliente);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Cliente creado exitosamente");
            response.put("cliente", newCliente);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al crear el cliente: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCliente(@PathVariable Long id,
                                           @Valid @RequestBody ClienteRequest clienteRequest) {
        try {
            Clientes cliente = convertToEntity(clienteRequest);
            Clientes updatedCliente = clienteService.updateCliente(id, cliente);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Cliente actualizado exitosamente");
            response.put("cliente", updatedCliente);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al actualizar el cliente: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable Long id) {
        try {
            clienteService.deleteCliente(id);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Cliente eliminado exitosamente");

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al eliminar el cliente: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    private Clientes convertToEntity(ClienteRequest clienteRequest) {
        Clientes cliente = new Clientes();
        cliente.setNombreCliente(clienteRequest.getNombreCliente());
        cliente.setTelefonoCliente(clienteRequest.getTelefonoCliente());
        cliente.setDireccion(clienteRequest.getDireccion());
        cliente.setEmail(clienteRequest.getEmail());
        cliente.setDiasCredito(clienteRequest.getDiasCredito());
        return cliente;
    }
}