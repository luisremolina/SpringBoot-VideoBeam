package com.example.videobeamapp.security.service;

import com.example.videobeamapp.security.repository.UsuarioRepository;
import com.example.videobeamapp.security.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional // para mantener la coherencia en la base de datos si hay varios usuario editando al timepo evitar incoherencias
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public Optional<Usuario> getByUsername(String username){
        return usuarioRepository.findByUsername(username);
    }
    public boolean existsByUsername(String username){
        return usuarioRepository.existsByUsername(username);
    }
    public boolean existsByEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public void save(Usuario usuario){
        usuarioRepository.save(usuario);
    }
}
