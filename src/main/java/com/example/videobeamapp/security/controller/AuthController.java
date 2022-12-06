package com.example.videobeamapp.security.controller;

import com.example.videobeamapp.dto.Mensaje;
import com.example.videobeamapp.security.dto.JwtDto;
import com.example.videobeamapp.security.dto.LoginUser;
import com.example.videobeamapp.security.dto.NewUser;
import com.example.videobeamapp.security.entity.Rol;
import com.example.videobeamapp.security.entity.Usuario;
import com.example.videobeamapp.security.enums.RolName;
import com.example.videobeamapp.security.jwt.JwtProvider;
import com.example.videobeamapp.security.service.RolService;
import com.example.videobeamapp.security.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin // aceeder desde cualquier URL
public class AuthController {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NewUser newUser, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("Campos mal puestos o email invalido"), HttpStatus.BAD_REQUEST);
        if (usuarioService.existsByUsername(newUser.getUsername()))
            return new ResponseEntity(new Mensaje("Ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        if (usuarioService.existsByEmail(newUser.getEmail()))
            return new ResponseEntity(new Mensaje("Ese correo ya existe"), HttpStatus.BAD_REQUEST);
        Usuario usuario = new Usuario(
                newUser.getName(),
                newUser.getUsername(),
                newUser.getEmail(),
                passwordEncoder.encode(newUser.getPassword()));
        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getByRolName(RolName.ROLE_DOCENTE).get());
        if (newUser.getRoles().contains("admin"))
            roles.add(rolService.getByRolName(RolName.ROLE_ADMIN).get());
        usuario.setRoles(roles);

        usuarioService.save(usuario);
        return new ResponseEntity(new Mensaje("Usuario Guardado"), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUser loginUser, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }
}
