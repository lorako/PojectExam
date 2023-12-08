package com.example.ProjectExam.services;

import com.example.ProjectExam.models.DTOs.BindingModel.AddWeaponDTO;
import com.example.ProjectExam.models.DTOs.View.WeaponViewDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface WeaponService {
    void addWeapon(AddWeaponDTO addWeaponDTO, String username, MultipartFile multipartFile);

    List<WeaponViewDTO> getAll();

    void buyWeapon(Long id, String username);
}
