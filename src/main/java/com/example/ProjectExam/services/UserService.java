package com.example.ProjectExam.services;

import com.example.ProjectExam.models.DTOs.LoginDTO;
import com.example.ProjectExam.models.DTOs.RegisterDTO;

public interface UserService {
    boolean register(RegisterDTO registerDTO);

    boolean login(LoginDTO loginDTO);

    void logout();
}
