package com.example.ProjectExam.models.DTOs;

import com.example.ProjectExam.models.entities.ArtistEntity;
import com.example.ProjectExam.models.entities.HeroEntity;
import com.example.ProjectExam.models.enums.PowerEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public class HeroViewDTO {

    private Long id;

    private String heroName;

    private String imgUrl;

    private LocalDate created;

    private String description;

    private BigDecimal price;

    private int likes;

    private PowerEnum power;

    private ArtistEntity creator;
    public HeroViewDTO(HeroEntity hero) {
        this.heroName= hero.getHeroName();
        this.imgUrl = hero.getPhotoUrl();
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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
