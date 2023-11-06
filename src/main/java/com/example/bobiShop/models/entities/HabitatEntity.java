package com.example.bobiShop.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

@Table(name="habitats")
@Entity
public class HabitatEntity extends BaseEntity{
    @Column(nullable = false, unique = true)
    @Length(min=3, max=10)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;

    @Positive@Column(nullable = false)
    private String price;
    @Column(nullable = false)
    private String imgUrl;
    @ManyToOne
    private ArtistEntity creator;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
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
