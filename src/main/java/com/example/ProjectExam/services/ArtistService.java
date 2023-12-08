package com.example.ProjectExam.services;

import com.example.ProjectExam.models.DTOs.View.ArtistViewDTO;
import com.example.ProjectExam.models.DTOs.RestDTO.ArtistRestDTO;

import java.util.List;

public interface ArtistService {



    List<ArtistRestDTO> getAllArtistsRest();

    List<ArtistViewDTO> getAll();
}
