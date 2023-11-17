package com.example.ProjectExam.controllers.Rest;

import com.example.ProjectExam.models.DTOs.RestDTO.AddHeroRestDTO;
import com.example.ProjectExam.models.DTOs.RestDTO.HeroRestDTO;
import com.example.ProjectExam.services.HeroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
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

    @DeleteMapping("/{id}")
    public ResponseEntity<HeroRestDTO> deleteById(@PathVariable("id") Long id){

        heroService.deleteById(id);

        return
                ResponseEntity.noContent().build();

    }
    @GetMapping("/{id}")
    public ResponseEntity<HeroRestDTO> findById(@PathVariable("id") Long id){
        Optional<HeroRestDTO> heroRestDTOOptional = heroService.findHeroById(id);

        return heroRestDTOOptional
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());

    }
    @PostMapping("/{id}")
    public void addHero(AddHeroRestDTO addHeroRestDTO){
        heroService.addRestHero(addHeroRestDTO);

    }
}
