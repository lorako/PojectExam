package com.example.ProjectExam.models.DTOs.View;

import com.example.ProjectExam.models.entities.HeroEntity;
import com.example.ProjectExam.models.entities.WeaponEntity;

import java.util.List;

public class ArtistViewDTO {


    private String username;

    private String country;

    private int age;
    private int totalLikes;
    private int totalArt;

    public ArtistViewDTO() {
    }

    public int getTotalArt() {
        return totalArt;
    }

    public void setTotalArt(int totalArt) {
        this.totalArt = totalArt;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(int totalLikes) {
        this.totalLikes = totalLikes;
    }
}
