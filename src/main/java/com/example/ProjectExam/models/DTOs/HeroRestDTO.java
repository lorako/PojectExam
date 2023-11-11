package com.example.ProjectExam.models.DTOs;

import com.example.ProjectExam.models.enums.PowerEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public class HeroRestDTO {

    private Long id;

    private String heroName;



    private LocalDate created;

    private String description;

    private BigDecimal price;

    private int likes;

    private PowerEnum power;

    private String creator;

    public Long getId() {
        return id;
    }



    public HeroRestDTO setId(Long id) {

        this.id = id;
        return this;
    }

    public HeroRestDTO() {
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
