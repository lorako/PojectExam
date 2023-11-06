package com.example.bobiShop.models.entities;

import jakarta.persistence.*;


@Table(name="images")
@Entity
public class ImageEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String url;
    @ManyToOne
    private HeroEntity hero;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HeroEntity getHero() {
        return hero;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setHero(HeroEntity hero) {
        this.hero = hero;
    }
}
