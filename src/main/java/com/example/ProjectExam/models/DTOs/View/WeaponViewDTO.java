package com.example.ProjectExam.models.DTOs.View;

import com.example.ProjectExam.models.entities.ArtistEntity;
import com.example.ProjectExam.models.entities.WeaponEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class WeaponViewDTO {
    private Long id;

    private String weaponName;
    private String imgUrl;


    private BigDecimal price;

    private ArtistEntity creator;

    public WeaponViewDTO(WeaponEntity weapon) {
        this.id = weapon.getId();
        this.weaponName = weapon.getWeaponName();
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


    public String getWeaponName() {
        return weaponName;
    }

    public void setWeaponName(String weaponName) {
        this.weaponName = weaponName;
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
