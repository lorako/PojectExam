package com.example.bobiShop.services;

import com.example.bobiShop.models.DTOs.LoginDTO;
import com.example.bobiShop.models.DTOs.RegisterDTO;

public interface UserService {
    boolean register(RegisterDTO registerDTO);

    boolean login(LoginDTO loginDTO);

    void logout();
}
