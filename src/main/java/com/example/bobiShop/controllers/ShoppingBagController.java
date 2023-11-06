package com.example.bobiShop.controllers;


import com.example.bobiShop.models.entities.HeroEntity;
import com.example.bobiShop.models.entities.UserEntity;
import com.example.bobiShop.services.ShoppingService;
import com.example.bobiShop.session.LoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ShoppingBagController {

    private final ShoppingService shoppingService;
    private final LoggedUser loggedUser;

    public ShoppingBagController(ShoppingService shoppingService, LoggedUser loggedUser) {
        this.shoppingService = shoppingService;
        this.loggedUser = loggedUser;
    }

    @GetMapping("/shoppingBag")
    public ModelAndView shop(){


        ModelAndView model=new ModelAndView("shoppingBag");
        List<HeroEntity> all = (List<HeroEntity>) shoppingService.getAll();

        model.addObject("all",all).addObject("totalPrice");
        return model;



    }

}
