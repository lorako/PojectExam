package com.example.ProjectExam.controllers;

import com.example.ProjectExam.models.DTOs.BindingModel.AddHeroDTO;
import com.example.ProjectExam.models.DTOs.View.HeroViewDTO;
import com.example.ProjectExam.services.HeroService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class HeroesController {

    private final HeroService heroService;



    public HeroesController(HeroService heroService) {
        this.heroService = heroService;
    }


    @GetMapping("/allHeroes")
    public ModelAndView homeView(@PageableDefault(
                                 size=3,
                                 sort = "id"
                                )Pageable pageable){

        Page<HeroViewDTO> allH= heroService.getAllHeroes(pageable);
        return new ModelAndView("allHeroes").addObject("allH", allH);
    }

    @GetMapping("/allHeroes/like-hero/{id}")
    public String likeHero(@PathVariable Long id, Principal principal, HttpServletRequest request) {
        String username = principal.getName();
        heroService.likeHeroWithId(id, username);


        String referer = request.getHeader("Referer");

        return "redirect:" + referer;
    }

   @GetMapping("/allHeroes/buy/{id}")
   public String buyHero(@PathVariable Long id, Principal principal){

        String username= principal.getName();
        heroService.buyHero(id, username);
       return ("redirect:/shoppingBag");
   }
    @GetMapping("/allHeroes/add")
    public ModelAndView addHeroes(AddHeroDTO addHeroDTO){
        return new ModelAndView("addHero");}
    @PostMapping("/allHeroes/add")
    public ModelAndView addHeroes(@ModelAttribute("addHeroDTO") @Valid AddHeroDTO addHeroDTO,
                                  BindingResult bindingResult, MultipartFile multipartFile, Principal principal){
        String username= principal.getName();

        if(bindingResult.hasErrors()){
            return new ModelAndView("addHero");
        }
        this.heroService.addHero(addHeroDTO,addHeroDTO.getImgUrl(),username);
        return new ModelAndView("redirect:/allHeroes");

    }
}
