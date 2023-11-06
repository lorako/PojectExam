package com.example.bobiShop.services;

import com.example.bobiShop.models.DTOs.AddHeroDTO;
import com.example.bobiShop.models.DTOs.HeroViewDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HeroService {
    void addHero(AddHeroDTO addHeroDTO, MultipartFile imageFile);




    List<HeroViewDTO> getAllHeroes();

    void likeHeroWithId(Long id, String loggedUserId);

    void buyHero(Long id);

    Object getTheMost();
}
