package com.desarrollo.pansal.service;

import com.desarrollo.pansal.model.Clientes;
import com.desarrollo.pansal.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional(readOnly = true)
    public List<Clientes> getAllClientes() {
        return clienteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Clientes> getClienteById(Long id) {
        return clienteRepository.findById(id);
    }

    @Transactional
    public Clientes createCliente(Clientes cliente) {
        if (clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new RuntimeException("Ya existe un cliente con este email");
        }
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Clientes updateCliente(Long id, Clientes cliente) {
        Clientes existingCliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        // Verificar si el nuevo email ya existe y no pertenece al cliente actual
        if (!existingCliente.getEmail().equals(cliente.getEmail()) &&
                clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new RuntimeException("Ya existe un cliente con este email");
        }

        existingCliente.setNombreCliente(cliente.getNombreCliente());
        existingCliente.setTelefonoCliente(cliente.getTelefonoCliente());
        existingCliente.setDireccion(cliente.getDireccion());
        existingCliente.setEmail(cliente.getEmail());
        existingCliente.setDiasCredito(cliente.getDiasCredito());

        return clienteRepository.save(existingCliente);
    }

    @Transactional
    public void deleteCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado");
        }
        clienteRepository.deleteById(id);
    }
}