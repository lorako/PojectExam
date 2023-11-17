package com.example.ProjectExam.services.Impl;

import com.example.ProjectExam.models.DTOs.RestDTO.ArtistRestDTO;
import com.example.ProjectExam.models.entities.ArtistEntity;
import com.example.ProjectExam.models.entities.HeroEntity;
import com.example.ProjectExam.repositories.ArtistRepository;
import com.example.ProjectExam.services.ArtistService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistServiceImpl implements ArtistService {

    final private ArtistRepository artistRepository;

    public ArtistServiceImpl(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    public List<HeroEntity> findAllHeroesByArtist(String name) {
        return null;
    }

    @Override
    public List<ArtistRestDTO> getAllArtistsRest() {
        return artistRepository
                .findAll()
                .stream().map(ArtistRestDTO::new).toList();
    }
}
