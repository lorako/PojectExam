package com.example.ProjectExam.models.DTOs;

import com.example.ProjectExam.models.entities.ArtistEntity;
import com.example.ProjectExam.models.entities.WeaponEntity;
import com.example.ProjectExam.models.enums.PowerEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;

public class WeaponViewDTO {
    private Long id;

    private String heroName;
    private String imgUrl;


    private BigDecimal price;

    private ArtistEntity creator;

    public WeaponViewDTO(WeaponEntity weapon) {
        this.id = weapon.getId();
        this.heroName = weapon.getWeaponName();
        this.imgUrl = weapon.getImgUrl();
        this.price = weapon.getPrice();
        this.creator = weapon.getCreator();
    }

    public WeaponViewDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
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

    public ArtistEntity getCreator() {
        return creator;
    }

    public void setCreator(ArtistEntity creator) {
        this.creator = creator;
    }
}
