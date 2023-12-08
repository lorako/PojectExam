package com.example.ProjectExam.controllers.Rest;

import com.example.ProjectExam.models.DTOs.RestDTO.AddHeroRestDTO;
import com.example.ProjectExam.models.DTOs.RestDTO.HeroRestDTO;
import com.example.ProjectExam.services.HeroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;


//@CrossOrigin("*")
@RestController
@RequestMapping("/api/heroes")
@CrossOrigin(origins = "http://localhost:63342")
public class HeroesRestController {

    private final HeroService heroService;

    public HeroesRestController(HeroService heroService) {
        this.heroService = heroService;
    }

    @GetMapping
    public ResponseEntity<List<HeroRestDTO>> getAllHeroes() {

        return ResponseEntity.ok(
                this.heroService.findAllHeroesForRest());

    }

    @DeleteMapping("/delete/{id}")
    @CrossOrigin(origins = "http://localhost:63342")
    public ResponseEntity<HeroRestDTO> deleteByIdRest(@PathVariable("id") Long id) {

        heroService.deleteByIdRest(id);

        return
                ResponseEntity.noContent().build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<HeroRestDTO> findById(@PathVariable("id") Long id) {
        Optional<HeroRestDTO> heroRestDTOOptional = heroService.findHeroById(id);

        return heroRestDTOOptional
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());

    }

    @PostMapping("/add/**")
    public ResponseEntity<AddHeroRestDTO> addHeroRest(@RequestBody AddHeroRestDTO addHeroRestDTO,
                                                      UriComponentsBuilder uriComponentsBuilder) {

        long newHeroId = addHeroRestDTO.getId();
        return ResponseEntity.created(uriComponentsBuilder
                .path("/api/heroes/add/").build(newHeroId)).build();

    }

}
