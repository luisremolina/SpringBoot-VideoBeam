package com.example.videobeamapp.controller;

import com.example.videobeamapp.dto.Mensaje;
import com.example.videobeamapp.dto.UserDTO;
import com.example.videobeamapp.entity.UserVb;
import com.example.videobeamapp.service.UserServiceMain;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserVbController {

    @Autowired
    UserServiceMain userService;

    @GetMapping("/")
    public ResponseEntity<List<UserVb>> list(){
        List<UserVb> list = userService.getAllUsers();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<UserVb> getById(@PathVariable("id") Long id){
        if(!userService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        UserVb usuario = userService.getOneById(id).get();
        return new ResponseEntity(usuario, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody UserDTO userDTO){
        if(StringUtils.isBlank(userDTO.getName()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(userService.existsByEmail(userDTO.getEmail()))
            return new ResponseEntity(new Mensaje("El Correo ya existe"), HttpStatus.BAD_REQUEST);

        UserVb userVb = new UserVb(
                null,
                userDTO.getName(),
                userDTO.getEmail(),
                userDTO.getPassword(),
                userDTO.getRol(),
                "false"
        );
        userService.create(userVb);
        return new ResponseEntity(new Mensaje("Uusario creado correctamente"), HttpStatus.OK);
    }


}
