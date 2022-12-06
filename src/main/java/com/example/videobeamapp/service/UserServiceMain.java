package com.example.videobeamapp.service;

import com.example.videobeamapp.entity.UserVb;
import com.example.videobeamapp.repository.UserVbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceMain {

    @Autowired
    UserVbRepository userVbRepository;


    public void create(UserVb userVb){
        userVbRepository.save(userVb);
    }

    public List<UserVb> getAllUsers(){
        return userVbRepository.findAll();
    }

    public boolean existsById(Long id){
        return userVbRepository.existsById(id);
    }

    public Optional<UserVb> getOneById(Long id){
        return userVbRepository.findById(id);
    }

    public void deleteById(Long id){
        userVbRepository.deleteById(id);
    }

    public boolean existsByEmail(String email){
        return userVbRepository.existsByEmail(email);
    }

}
