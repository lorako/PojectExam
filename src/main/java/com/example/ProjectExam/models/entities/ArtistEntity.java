package com.example.ProjectExam.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="artists")
public class ArtistEntity extends BaseEntity{

    @Column(nullable = false, unique = true)
    @Length(min=3, max=15)
    private String username;
    @Column(nullable = false)
    @Email
    private String email;

    @Column
    private int totalArt;

    @Column
    private int totalLikes;

    public ArtistEntity() {


    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTotalArt() {
        return totalArt;
    }

    public void setTotalArt(int totalArt) {
        this.totalArt = totalArt;
    }

    public int getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(int totalLikes) {

        this.totalLikes = totalLikes;
    }
}
