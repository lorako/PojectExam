package com.example.ProjectExam.controllers;


import com.example.ProjectExam.models.DTOs.View.ShopBagDTO;
import com.example.ProjectExam.services.ShopBagService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@Controller
public class ShopBagController {

    private final ShopBagService shopBagService;


    public ShopBagController(ShopBagService shopBagService) {
        this.shopBagService = shopBagService;

    }

    @GetMapping("/shoppingBag")
    public ModelAndView shop(ShopBagDTO boughtItemsDTO, Principal principal, BigDecimal total,  BigDecimal totalPrice){

        String username=principal.getName();


        List<ShopBagDTO> all = shopBagService.getAll(username);
        BigDecimal total1 = this.shopBagService.total(username);

        return new ModelAndView("shoppingBag","all",all)
               .addObject("totalPrice", this.shopBagService.total(username))
               .addObject("discountPrice", this.shopBagService.discountPrice(total1, username));



    }
    @GetMapping("/shoppingBag/pay")
    public ModelAndView pay(Principal principal){
        String username= principal.getName();
        ModelAndView model = new ModelAndView("payPage");
        shopBagService.pay(username);
        return model;

    }

}
