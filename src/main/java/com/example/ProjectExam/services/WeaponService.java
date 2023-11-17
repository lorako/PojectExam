package com.example.ProjectExam.services;

import com.example.ProjectExam.models.DTOs.AddWeaponDTO;
import com.example.ProjectExam.models.DTOs.WeaponViewDTO;
import com.example.ProjectExam.models.entities.WeaponEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface WeaponService {
    void addWeapon(AddWeaponDTO addWeaponDTO, String username, MultipartFile multipartFile);

    List<WeaponViewDTO> getAll();

    void buyWeapon(Long id, String username);
}
