package com.desarrollo.pansal.service;

import com.desarrollo.pansal.model.Rol;
import com.desarrollo.pansal.repository.RolRepository;
import com.desarrollo.pansal.model.Usuario;
import com.desarrollo.pansal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Rol> getAllRoles() {
        return rolRepository.findAll();  // Se obtiene la lista de roles desde el repositorio
    }

    public Usuario authenticate(String username, String password) {
        logger.debug("Intentando autenticar usuario: {}", username);

        Usuario usuario = usuarioRepository.findByNombreUsuario(username)
                .orElseThrow(() -> new BadCredentialsException("Usuario no encontrado"));

        logger.debug("Usuario encontrado: {}", usuario.getNombreUsuario());
        logger.debug("Hash almacenado: {}", usuario.getContrasena());

        boolean matches = passwordEncoder.matches(password, usuario.getContrasena());
        logger.debug("¿Contraseña coincide?: {}", matches);

        if (!matches) {
            throw new BadCredentialsException("Contraseña incorrecta");
        }

        return usuario;
    }

    public Usuario registerUser(Usuario usuario) {
        Optional<Usuario> existingUser = usuarioRepository.findByNombreUsuario(usuario.getNombreUsuario());
        if (existingUser.isPresent()) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }

        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        return usuarioRepository.save(usuario);
    }

    public boolean isUsernameAvailable(String username) {
        return !usuarioRepository.findByNombreUsuario(username).isPresent();
    }
}
