package com.example.ProjectExam.services;

import com.example.ProjectExam.models.DTOs.ShopBagDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ShopBagService {






    BigDecimal total();

    List<ShopBagDTO> getAll();


    void pay();





    BigDecimal discountPrice(BigDecimal total);
}
