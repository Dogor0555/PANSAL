package com.desarrollo.pansal.service;

import com.desarrollo.pansal.model.Usuario;
import com.desarrollo.pansal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Cargar el usuario desde la base de datos
        Usuario usuario = usuarioRepository.findByNombreUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Crear y devolver un objeto UserDetails con los detalles del usuario
        return new User(usuario.getNombreUsuario(), usuario.getContrasena(),
                Collections.singletonList(new org.springframework.security.core.authority.SimpleGrantedAuthority(usuario.getRol().getNombreRol())));
    }
}
