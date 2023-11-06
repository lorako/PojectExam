package com.example.bobiShop.models.entities;

import com.example.bobiShop.models.enums.PowerEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Table(name="heroes")
@Entity
public class HeroEntity extends BaseEntity  {
    @Column(nullable = false, unique = true)
    @Length(min=3, max=15)
    private String heroName;

    @OneToMany(mappedBy = "hero", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ImageEntity> imgUrls;
    @PastOrPresent
    private LocalDate created;
    @Column(nullable = false, unique = true)
    @Length(min=3, max=15)
    private String description;
    @Column(nullable = false)
    @Positive
    private BigDecimal price;
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


    public Set<ImageEntity> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(Set<ImageEntity> imgUrls) {
        this.imgUrls = imgUrls;
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
