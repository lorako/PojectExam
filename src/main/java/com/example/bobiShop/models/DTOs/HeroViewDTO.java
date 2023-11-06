package com.example.bobiShop.models.DTOs;

import com.example.bobiShop.models.entities.ArtistEntity;
import com.example.bobiShop.models.entities.HeroEntity;
import com.example.bobiShop.models.entities.ImageEntity;
import com.example.bobiShop.models.enums.PowerEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class HeroViewDTO {

    private Long id;

    private String heroName;

    private Set<ImageEntity> imgUrls;

    private LocalDate created;

    private String description;

    private BigDecimal price;

    private int likes;

    private PowerEnum power;

    private ArtistEntity creator;
    public HeroViewDTO(HeroEntity hero) {
        this.heroName = hero.getHeroName();
        this.imgUrls = hero.getImgUrls();
        this.created = hero.getCreated();
        this.description = hero.getDescription();
        this.price = hero.getPrice();
        this.likes = hero.getLikes();
        this.power = hero.getPower();
        this.creator = hero.getCreator();
        this.id=hero.getId();
    }


    public Long getId() {
        return id;
    }

    public Set<ImageEntity> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(Set<ImageEntity> imgUrls) {
        this.imgUrls = imgUrls;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HeroViewDTO() {

    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }



    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public PowerEnum getPower() {
        return power;
    }

    public void setPower(PowerEnum power) {
        this.power = power;
    }

    public ArtistEntity getCreator() {
        return creator;
    }

    public void setCreator(ArtistEntity creator) {
        this.creator = creator;
    }
}
