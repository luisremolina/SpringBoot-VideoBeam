package com.example.videobeamapp.security.service;

import com.example.videobeamapp.security.entity.Rol;
import com.example.videobeamapp.security.enums.RolName;
import com.example.videobeamapp.security.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class RolService {

    @Autowired
    RolRepository rolRepository;

    public Optional<Rol> getByRolName(RolName rolname){
        return rolRepository.findByRolName(rolname);
    }
}
