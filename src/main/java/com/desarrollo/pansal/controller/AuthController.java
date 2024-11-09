package com.desarrollo.pansal.controller;

import com.desarrollo.pansal.dto.LoginRequest;
import com.desarrollo.pansal.model.Rol;
import com.desarrollo.pansal.model.Usuario;
import com.desarrollo.pansal.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        logger.info("Intento de inicio de sesión para el usuario: {}", loginRequest.getUsername());

        try {
            Usuario user = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
            logger.info("Usuario autenticado exitosamente: {}", user.getNombreUsuario());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("username", user.getNombreUsuario());
            response.put("userId", user.getIdUsuario());
            response.put("role", user.getRol() != null ? user.getRol().getNombreRol() : "Rol no asignado");
            response.put("profilePic", user.getFotoPerfil());

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            logger.warn("Intento de inicio de sesión fallido para el usuario: {}", loginRequest.getUsername());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Credenciales inválidas");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (Exception e) {
            logger.error("Error durante el inicio de sesión para el usuario: " + loginRequest.getUsername(), e);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error en el servidor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Rol>> getRoles() {
        List<Rol> roles = authService.getAllRoles();
        logger.info("Roles fetched: " + roles);
        return ResponseEntity.ok(roles);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody Usuario usuario) {
        try {
            Usuario newUser = authService.registerUser(usuario);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Usuario registrado exitosamente");
            response.put("userId", String.valueOf(newUser.getIdUsuario()));

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al registrar el usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
