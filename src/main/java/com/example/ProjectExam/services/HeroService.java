package com.example.ProjectExam.services;

import com.example.ProjectExam.models.DTOs.AddHeroDTO;
import com.example.ProjectExam.models.DTOs.HeroRestDTO;
import com.example.ProjectExam.models.DTOs.HeroViewDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HeroService {
    void addHero(AddHeroDTO addHeroDTO, MultipartFile imageFile);




    List<HeroViewDTO> getAllHeroes();

    void likeHeroWithId(Long id, String loggedUserId);

    void buyHero(Long id);

    List<HeroRestDTO> findAllHeroesForRest();

    Object getTheMost();
}
