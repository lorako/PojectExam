package com.example.ProjectExam.models.DTOs.RestDTO;

import com.example.ProjectExam.models.entities.ArtistEntity;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;

public class ArtistRestDTO {

    private String username;

    private String email;

    public ArtistRestDTO() {
    }

    public ArtistRestDTO(ArtistEntity artist) {
        this.username = artist.getUsername();
        this.email = artist.getEmail();
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
}
