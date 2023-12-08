package com.example.ProjectExam.services;

import com.example.ProjectExam.models.DTOs.BindingModel.AddHeroDTO;
import com.example.ProjectExam.models.DTOs.RestDTO.AddHeroRestDTO;
import com.example.ProjectExam.models.DTOs.RestDTO.HeroRestDTO;
import com.example.ProjectExam.models.DTOs.View.HeroViewDTO;
import com.example.ProjectExam.models.entities.HeroEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface HeroService {
    void addHero(AddHeroDTO addHeroDTO, MultipartFile imageFile, String username);
    List<HeroEntity> getAllHeroes();
    Page<HeroViewDTO> getAllHeroes(Pageable pageable);
    void likeHeroWithId(Long id, String username);
    void buyHero(Long id, String username);
    List<HeroRestDTO> findAllHeroesForRest();
    Object getTheMost();
    Optional<HeroRestDTO> findHeroById(Long id);
    void deleteById(Long id);
    boolean addRestHero(AddHeroRestDTO addHeroRestDTO);
    void safe(HeroEntity hero);
    long findById(Long id);

    void deleteByIdRest(Long id);
}
