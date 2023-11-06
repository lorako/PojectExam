package com.example.bobiShop.controllers;

import com.example.bobiShop.services.HeroService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    private final HeroService heroService;

    public HomeController(HeroService heroService) {
        this.heroService = heroService;
    }



    @GetMapping("/home")
    public ModelAndView home(){
       ModelAndView model= new ModelAndView("home");
       model.addObject("most", this.heroService.getTheMost());
       return model;
    }
    @GetMapping("/")
    public ModelAndView index(){
        return new ModelAndView("index");
    }




}
