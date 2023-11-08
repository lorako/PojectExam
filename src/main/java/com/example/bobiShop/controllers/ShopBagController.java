package com.example.bobiShop.controllers;


import com.example.bobiShop.models.DTOs.ShopBagDTO;
import com.example.bobiShop.services.ShopBagService;
import com.example.bobiShop.session.LoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ShopBagController {

    private final ShopBagService shopBagService;


    public ShopBagController(ShopBagService shopBagService) {
        this.shopBagService = shopBagService;

    }

    @GetMapping("/shoppingBag")
    public ModelAndView shop(ShopBagDTO boughtItemsDTO){


        List<ShopBagDTO> all = shopBagService.getAll();

       return new ModelAndView("shoppingBag","all",all)
               .addObject("totalPrice", this.shopBagService.total()).addObject("discountPrice",
                       this.shopBagService.discountPrice(this.shopBagService.total()));



    }
    @GetMapping("/shoppingBag/pay")
    public ModelAndView pay(){


        ModelAndView model = new ModelAndView("payPage");
        shopBagService.pay();
        return model;

    }

}
