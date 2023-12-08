package com.example.ProjectExam.controllers;

import com.example.ProjectExam.models.DTOs.View.ArtistViewDTO;
import com.example.ProjectExam.services.ArtistService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ArtistsController {
    private final ArtistService artistService;

    public ArtistsController(ArtistService artistService) {
        this.artistService = artistService;
    }


    @GetMapping("/artists")
    public ModelAndView artistPage(ArtistViewDTO artistViewDTO){

        ModelAndView model=new ModelAndView("artists");

        List<ArtistViewDTO> allA = artistService.getAll();

        model.addObject("allA", allA);

        return model;
    }
}
