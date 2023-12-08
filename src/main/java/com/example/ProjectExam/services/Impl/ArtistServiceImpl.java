package com.example.ProjectExam.services.Impl;

import com.example.ProjectExam.models.DTOs.View.ArtistViewDTO;
import com.example.ProjectExam.models.DTOs.RestDTO.ArtistRestDTO;
import com.example.ProjectExam.models.entities.ArtistEntity;
import com.example.ProjectExam.repositories.ArtistRepository;
import com.example.ProjectExam.services.ArtistService;
import com.example.ProjectExam.services.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArtistServiceImpl implements ArtistService {

    final private ArtistRepository artistRepository;
    final private UserService userService;

    public ArtistServiceImpl(ArtistRepository artistRepository, UserService userService) {
        this.artistRepository = artistRepository;
        this.userService = userService;
    }


    @Override
    public List<ArtistRestDTO> getAllArtistsRest() {
        return artistRepository
                .findAll()
                .stream().map(ArtistRestDTO::new).toList();
    }

    @Override
    public List<ArtistViewDTO> getAll() {
          List<ArtistEntity>listArtists= (List<ArtistEntity>) artistRepository.findAll().stream().toList();

          List<ArtistViewDTO> dtoList=new ArrayList<>();

        for (ArtistEntity artist:listArtists) {
            ArtistViewDTO artistViewDTO=new ArtistViewDTO();
            artistViewDTO.setTotalLikes(artist.getTotalLikes());
            artistViewDTO.setUsername(artist.getUsername());
            artistViewDTO.setTotalArt(artist.getTotalArt());
            userService.findByUsername(artist.getUsername()).get().getAge();
            userService.findByUsername(artist.getUsername()).get().getCountry();
            artistViewDTO.setAge(userService.findByUsername(artist.getUsername()).get().getAge());
            artistViewDTO.setCountry(userService.findByUsername(artist.getUsername()).get().getCountry());
            dtoList.add(artistViewDTO);
        }


        return dtoList;

    }
}

