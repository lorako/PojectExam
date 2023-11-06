package com.example.bobiShop.models.DTOs;

import com.example.bobiShop.models.entities.ImageEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class ShoppingBagsDTO {

    List<ItemsDTO> myShoppingBag;

    public List<ItemsDTO> getMyShoppingBag() {
        return myShoppingBag;
    }

    public void setMyShoppingBag(List<ItemsDTO> myShoppingBag) {
        this.myShoppingBag = myShoppingBag;
    }
}
