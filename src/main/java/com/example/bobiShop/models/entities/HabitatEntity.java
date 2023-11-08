package com.example.bobiShop.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

@Table(name="habitats")
@Entity
public class HabitatEntity extends BaseEntity{

    @Length(min=3, max=10)
    private String habitatName;
    @Column(columnDefinition = "TEXT")
    private String description;

    @Positive@Column(nullable = false)
    private String price;
    @Column(nullable = false)
    private String imgUrl;
    @ManyToOne
    private ArtistEntity creator;


    public String getName() {
        return habitatName;
    }

    public void setName(String habitatName) {
        this.habitatName =habitatName;
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
