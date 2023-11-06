package com.example.bobiShop.models.DTOs;

import com.example.bobiShop.models.entities.HeroEntity;
import com.example.bobiShop.models.entities.ImageEntity;

import java.math.BigDecimal;
import java.util.Set;

public class Item extends HeroEntity {

    private Long id;


    private String name;

    private Set<ImageEntity> imgUrls;


    private BigDecimal price;

    public Item(Long id, String name, Set<ImageEntity> imgUrls, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.imgUrls = imgUrls;
        this.price = price;
    }

    public Item() {
    }



    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ImageEntity> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(Set<ImageEntity> imgUrls) {
        this.imgUrls = imgUrls;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
