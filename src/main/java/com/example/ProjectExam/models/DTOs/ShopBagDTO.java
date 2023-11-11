package com.example.ProjectExam.models.DTOs;

import com.example.ProjectExam.models.entities.ShopBagEntity;

import java.math.BigDecimal;

public class ShopBagDTO {
    private String itemName;
    private String imgUrl;
    private BigDecimal price;


    public ShopBagDTO(ShopBagEntity item) {
        this.itemName = item.getItemName();
        this.imgUrl = item.getImgUrl();
        this.price = item.getPrice();
    }

    public ShopBagDTO() {
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
