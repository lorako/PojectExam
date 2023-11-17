package com.example.ProjectExam.services;


import com.example.ProjectExam.models.DTOs.RegisterDTO;
import com.example.ProjectExam.models.entities.UserEntity;

import java.util.Optional;

public interface UserService {
    boolean register(RegisterDTO registerDTO);


    Optional<UserEntity> findByUsername(String username);
}
