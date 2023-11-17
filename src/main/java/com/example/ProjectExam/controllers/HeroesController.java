package com.example.ProjectExam.controllers;

import com.example.ProjectExam.models.DTOs.AddHeroDTO;
import com.example.ProjectExam.models.DTOs.HeroViewDTO;
import com.example.ProjectExam.services.HeroService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
public class HeroesController {

    private final HeroService heroService;



    public HeroesController(HeroService heroService) {
        this.heroService = heroService;
    }


    @GetMapping("/allHeroes")
    public ModelAndView homeView(){

        List<HeroViewDTO> allH= heroService.getAllHeroes();


        return new ModelAndView("allHeroes").addObject("allH", allH);


    }
    @GetMapping("/allHeroes/like-hero/{id}")
    public String likeHero(@PathVariable Long id, Principal principal) {

        String username= principal.getName();

        heroService.likeHeroWithId(id, username);
        return "redirect:/allHeroes";
    }

   @GetMapping("/allHeroes/buy/{id}")
   public String buyHero(@PathVariable Long id, Principal principal){

        String username= principal.getName();


        heroService.buyHero(id, username);

       return ("redirect:/shoppingBag");
   }
    @GetMapping("/allHeroes/add")
    public ModelAndView addHeroes(AddHeroDTO addHeroDTO){
        return new ModelAndView("heroAdd");
    }

    @PostMapping("/allHeroes/add")
    public ModelAndView addHeroes(@ModelAttribute("addHeroDTO") @Valid AddHeroDTO addHeroDTO,
                                  BindingResult bindingResult, MultipartFile multipartFile, Principal principal){
        String username= principal.getName();

        if(bindingResult.hasErrors()){
            return new ModelAndView("heroAdd");
        }
        this.heroService.addHero(addHeroDTO,addHeroDTO.getImgUrl(),username);

        return new ModelAndView("redirect:/allHeroes");


    }
}
