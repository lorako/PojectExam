package com.example.ProjectExam.models.entities;

import com.example.ProjectExam.models.DTOs.RestDTO.AddHeroRestDTO;
import com.example.ProjectExam.models.enums.PowerEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;


@Table(name="heroes")
@Entity
public class HeroEntity extends BaseEntity  {
    @Column(nullable = false, unique = true)
    @Length(min=3, max=15)
    private String heroName;

    @Column
    private String photoUrl;
    @Positive
    @Column(nullable = false)
    private BigDecimal price;
    @PastOrPresent
    private LocalDate created;
    @Column(nullable = false, unique = true)
    @Length(min=3, max=30)
    private String description;

   @Column
    private int likes;
   @Enumerated(EnumType.STRING)
    private PowerEnum power;
    @ManyToOne
    private ArtistEntity creator;


    public PowerEnum getPower() {

        return power;
    }

    public void setPower(PowerEnum power) {
        this.power = power;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }


    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
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



    public ArtistEntity getCreator() {
        return creator;
    }

    public void setCreator(ArtistEntity creator) {
        this.creator = creator;
    }
}
