// AuthService.java
package com.desarrollo.pansal.service;

import com.desarrollo.pansal.model.Rol;
import com.desarrollo.pansal.repository.RolRepository;
import com.desarrollo.pansal.model.Usuario;
import com.desarrollo.pansal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        return rolRepository.findAll();
    }

    public Usuario authenticate(String username, String password) {
        logger.debug("Intentando autenticar usuario: {}", username);

        Usuario usuario = usuarioRepository.findByNombreUsuario(username)
                .orElseThrow(() -> new BadCredentialsException("Usuario no encontrado"));

        if (!passwordEncoder.matches(password, usuario.getContrasena())) {
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

    public Usuario findByUsername(String username) {
        return usuarioRepository.findByNombreUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }

    public boolean isUsernameAvailable(String username) {
        return !usuarioRepository.findByNombreUsuario(username).isPresent();
    }
}