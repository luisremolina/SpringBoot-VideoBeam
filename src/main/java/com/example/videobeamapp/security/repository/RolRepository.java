package com.example.videobeamapp.security.repository;

import com.example.videobeamapp.security.enums.RolName;
import com.example.videobeamapp.security.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByRolName(RolName rolName);
}
