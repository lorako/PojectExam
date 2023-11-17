package com.example.ProjectExam.services;

import com.example.ProjectExam.models.DTOs.ShopBagDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ShopBagService {

    List<ShopBagDTO> getAll(String username);

    void pay(String username);
    BigDecimal discountPrice(BigDecimal total,String username);

    BigDecimal total(String username);
}
