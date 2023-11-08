package com.example.bobiShop.services;

import com.example.bobiShop.models.DTOs.ShopBagDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ShopBagService {






    BigDecimal total();

    List<ShopBagDTO> getAll();


    void pay();





    BigDecimal discountPrice(BigDecimal total);
}
