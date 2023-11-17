package com.example.ProjectExam.services;

import com.example.ProjectExam.models.DTOs.AddHeroDTO;
import com.example.ProjectExam.models.DTOs.RestDTO.AddHeroRestDTO;
import com.example.ProjectExam.models.DTOs.RestDTO.HeroRestDTO;
import com.example.ProjectExam.models.DTOs.HeroViewDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface HeroService {
    void addHero(AddHeroDTO addHeroDTO, MultipartFile imageFile, String username);
    List<HeroViewDTO> getAllHeroes();

    void likeHeroWithId(Long id, String username);


    void buyHero(Long id, String username);

    List<HeroRestDTO> findAllHeroesForRest();

    Object getTheMost();

    Optional<HeroRestDTO> findHeroById(Long id);

    void deleteById(Long id);

    void addRestHero(AddHeroRestDTO addHeroRestDTO);
}
