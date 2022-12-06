package com.example.videobeamapp.repository;

import com.example.videobeamapp.entity.UserVb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVbRepository extends JpaRepository<UserVb, Long> {
    boolean existsByEmail(String email);
}
