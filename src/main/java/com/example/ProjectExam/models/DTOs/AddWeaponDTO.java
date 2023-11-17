package com.example.ProjectExam.models.DTOs;

import com.example.ProjectExam.models.entities.ArtistEntity;
import com.example.ProjectExam.models.enums.PowerEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AddWeaponDTO {

    private Long id;

    @Size(min=3, max=15)
    @NotEmpty(message = "Name must be between 3 and 15 characters!")
    private String weaponName;

    private MultipartFile imgUrl;

    @Positive(message = "Price must be  a positive number")
    @NotNull
    private BigDecimal price;

    private ArtistEntity creator;

    public AddWeaponDTO() {
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

    public MultipartFile getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(MultipartFile imgUrl) {
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
