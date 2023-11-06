package com.example.bobiShop.controllers;

import com.example.bobiShop.models.DTOs.AddHeroDTO;
import com.example.bobiShop.models.DTOs.HeroViewDTO;
import com.example.bobiShop.models.entities.HeroEntity;
import com.example.bobiShop.services.HeroService;
import com.example.bobiShop.session.LoggedUser;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HeroesController {

    private final HeroService heroService;
    private final LoggedUser loggedUser;


    public HeroesController(HeroService heroService, LoggedUser loggedUser) {
        this.heroService = heroService;
        this.loggedUser = loggedUser;
    }


    @GetMapping("/allHeroes")
    public ModelAndView homeView(){

        List<HeroViewDTO> allH= heroService.getAllHeroes();


        return new ModelAndView("allHeroes").addObject("allH", allH);


    }
    @GetMapping("/allHeroes/like-hero/{id}")
    public String likeHero(@PathVariable Long id) {
        String loggedUserName=loggedUser.getUsername();
        heroService.likeHeroWithId(id, loggedUserName);
        return "redirect:/allHeroes";
    }

   @GetMapping("/allHeroes/buy/{id}")
   public String buyHero(@PathVariable Long id){


        heroService.buyHero(id);

       return ("redirect:/shoppingBag");
   }
    @GetMapping("/allHeroes/add")
    public ModelAndView addHeroes(AddHeroDTO addHeroDTO){
        return new ModelAndView("heroAdd");
    }

    @PostMapping("/allHeroes/add")
    public ModelAndView addHeroes(@ModelAttribute("addHeroDTO") @Valid AddHeroDTO addHeroDTO,
                                  BindingResult bindingResult, MultipartFile multipartFile){

        if (!loggedUser.isLogged()) {
            return new ModelAndView("redirect:/index");
        }
        if(bindingResult.hasErrors()){
            return new ModelAndView("heroAdd");
        }
        this.heroService.addHero(addHeroDTO,addHeroDTO.getImgUrl());

        return new ModelAndView("redirect:/allHeroes");


    }
}
