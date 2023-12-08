package com.example.ProjectExam.controllers;

import com.example.ProjectExam.models.DTOs.BindingModel.AddWeaponDTO;
import com.example.ProjectExam.models.DTOs.View.WeaponViewDTO;
import com.example.ProjectExam.services.WeaponService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
public class WeaponController {

    public final WeaponService weaponService;

    public WeaponController(WeaponService weaponService) {
        this.weaponService = weaponService;
    }


    @GetMapping("/weapons")
    public ModelAndView weaponHome(){

        List<WeaponViewDTO> all = weaponService.getAll();

        return new ModelAndView("weapons").addObject("all", all);

    }
    @GetMapping("/weapons/add")
    public ModelAndView addWeapon(AddWeaponDTO addWeaponDTO){


        return new ModelAndView("addWeapon");

    }

    @PostMapping("/weapons/add")
    public ModelAndView addWeapon(AddWeaponDTO addWeaponDTO, Principal principal){
        String username= principal.getName();

        weaponService.addWeapon(addWeaponDTO, username,addWeaponDTO.getImgUrl());

        return new ModelAndView("redirect:/weapons");

    }

    @GetMapping("/weapons/buy/{id}")
    public ModelAndView buyWeapon(@PathVariable("id") Long id, Principal principal){
        String username= principal.getName();

        weaponService.buyWeapon(id, username);

        return new ModelAndView("redirect:/shoppingBag");
    }
}
