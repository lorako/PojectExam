package com.example.ProjectExam.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Table(name="weapons")
@Entity
public class WeaponEntity extends BaseEntity{
    @Column(nullable = false, unique = true)
    @Length(min=3, max=15)
    private String weaponName;
    @Positive
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private String imgUrl;
    @ManyToOne
    private ArtistEntity creator;

    public String getWeaponName() {
        return weaponName;
    }

    public void setWeaponName(String weaponName) {
        this.weaponName = weaponName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public ArtistEntity getCreator() {
        return creator;
    }

    public void setCreator(ArtistEntity creator) {
        this.creator = creator;
    }
}
