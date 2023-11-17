package com.example.ProjectExam.services;

import com.example.ProjectExam.models.DTOs.RestDTO.ArtistRestDTO;
import com.example.ProjectExam.models.entities.HeroEntity;

import java.util.List;

public interface ArtistService {

    List<HeroEntity> findAllHeroesByArtist(String name);

    List<ArtistRestDTO> getAllArtistsRest();
}
