package com.example.ProjectExam.controllers.Rest;

import com.example.ProjectExam.models.DTOs.HeroRestDTO;
import com.example.ProjectExam.models.DTOs.HeroViewDTO;
import com.example.ProjectExam.repositories.HeroRepository;
import com.example.ProjectExam.services.HeroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/heroes")
public class HeroesRestController {

    private final HeroService heroService;

    public HeroesRestController(HeroService heroService) {
        this.heroService = heroService;
    }
    @GetMapping
    public ResponseEntity<List<HeroRestDTO>> getAllHeroes(){

        return ResponseEntity.ok(
                this.heroService.findAllHeroesForRest());


    }
}
