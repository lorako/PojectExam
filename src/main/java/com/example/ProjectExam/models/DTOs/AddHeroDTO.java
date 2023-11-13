package com.example.ProjectExam.models.DTOs;

import com.example.ProjectExam.models.entities.ArtistEntity;
import com.example.ProjectExam.models.enums.PowerEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AddHeroDTO {

    private Long id;
    @Size(min=3, max=15)
    @NotEmpty(message = "Name must be between 3 and 15 characters!")
    private String heroName;


    private MultipartFile imgUrl;

    @PastOrPresent
    @NotNull(message = "Release date cannot be in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate created;
    @Size(min=3, max=30)
    @NotNull(message = "Description must be between 3 and 30 characters!")
    private String description;

    @Positive(message = "Price must be  a positive number")
    @NotNull
    private BigDecimal price;

    private int likes;

    @NotNull(message = "You must select a power")
    @Enumerated(EnumType.STRING)
    private PowerEnum power;

    private ArtistEntity creator;

    public AddHeroDTO(Long id, String heroName, MultipartFile imgUrl, LocalDate created, String description,
                      BigDecimal price, int likes, PowerEnum power, ArtistEntity creator) {
        this.id = id;
        this.heroName = heroName;
        this.imgUrl = imgUrl;
        this.created = created;
        this.description = description;
        this.price = price;
        this.likes = likes;
        this.power = power;
        this.creator = creator;
    }

    public AddHeroDTO() {
    }

    public AddHeroDTO(MultipartFile imgUrl) {
        this.imgUrl = imgUrl;
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

    public MultipartFile getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(MultipartFile imgUrl) {
        this.imgUrl = imgUrl;
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
