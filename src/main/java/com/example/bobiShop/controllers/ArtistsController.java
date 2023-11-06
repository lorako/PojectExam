package com.example.bobiShop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ArtistsController {

    @GetMapping("/artists")
    public ModelAndView weapon(){
        return new ModelAndView("artists");
    }
}
